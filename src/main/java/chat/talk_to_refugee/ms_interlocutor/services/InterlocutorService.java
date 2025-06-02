package chat.talk_to_refugee.ms_interlocutor.services;

import chat.talk_to_refugee.ms_interlocutor.clients.LocalitiesClient;
import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;
import chat.talk_to_refugee.ms_interlocutor.exceptions.*;
import chat.talk_to_refugee.ms_interlocutor.repositories.InterlocutorRepository;
import chat.talk_to_refugee.ms_interlocutor.resources.dtos.*;
import chat.talk_to_refugee.ms_interlocutor.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterlocutorService {

    private final InterlocutorRepository repository;
    private final PasswordEncoder encoder;
    private final LocalitiesClient localitiesApi;

    public void create(CreateRequest dto) {
        validateAge(dto.getBirthDate());
        validateEmail(dto.getEmail());

        handleStateAndCity(dto);

        var interlocutor = dto.toInterlocutor();
        interlocutor.setPassword(encoder.encode(dto.getPassword()));

        this.repository.save(interlocutor);
    }

    public void update(UpdateRequest dto, UUID id) {
        if (dto.isEmpty()) {
            throw new EmptyBodyException();
        }

        var interlocutor = this.repository.findById(id).orElseThrow(NotFoundException::new);

        CustomBeanUtils.copyNonNullProperties(dto, interlocutor);

        this.repository.save(interlocutor);
    }

    public ProfileResponse profile(UUID id) {
        var interlocutor = this.repository.findById(id).orElseThrow(NotFoundException::new);

        return ProfileResponse.fromInterlocutor(interlocutor);
    }

    public List<SearchResponse> search(SearchRequest dto, UUID id) {
        List<Long> types;
        if (dto.types() != null) {
            types = dto.types().stream().map(type -> type.get().getId()).toList();
        } else {
            types = Arrays.stream(InterlocutorType.Values.values()).map(type -> type.get().getId()).toList();
        }

        var search = this.repository.search(dto.fullName(), dto.state(), dto.city(), types, id);

        return search.stream().map(interlocutor ->
                new SearchResponse(
                        interlocutor.getFullName(),
                        interlocutor.getEmail(),
                        interlocutor.getState(),
                        interlocutor.getCity(),
                        interlocutor.getType()
                )
        ).toList();
    }

    private void validateAge(String birthDateStr) {
        var birthDate = LocalDate.parse(birthDateStr);
        if (LocalDate.now().minusYears(18).isBefore(birthDate)) {
            throw new UnderageException();
        }
    }

    private void validateEmail(String email) {
        if (this.repository.findByEmail(email).isPresent()) {
            throw new EmailInUseException();
        }
    }

    private void handleStateAndCity(CreateRequest dto) {
        boolean filledState = dto.getState() != null && !dto.getState().isBlank();
        boolean filledCity = dto.getCity() != null && !dto.getCity().isBlank();

        if (!filledState && filledCity) {
            throw new StateEmptyException();
        }

        if (!filledState) {
            dto.setState(null);
            dto.setCity(null);
            return;
        }

        var cities = this.localitiesApi.findByUF(dto.getState());

        if (cities.isEmpty()) {
            throw new StateNotFoundException();
        }

        if (filledCity) {
            var city = cities.stream()
                    .filter(localitie -> localitie.name().equalsIgnoreCase(dto.getCity()))
                    .findFirst()
                    .orElseThrow(CityNotFoundException::new);

            dto.setCity(city.name());
        } else {
            dto.setCity(null);
        }
    }

}
