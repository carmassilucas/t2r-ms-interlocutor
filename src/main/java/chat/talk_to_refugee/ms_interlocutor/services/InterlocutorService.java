package chat.talk_to_refugee.ms_interlocutor.services;

import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;
import chat.talk_to_refugee.ms_interlocutor.exceptions.EmailInUseException;
import chat.talk_to_refugee.ms_interlocutor.exceptions.EmptyBodyException;
import chat.talk_to_refugee.ms_interlocutor.exceptions.NotFoundException;
import chat.talk_to_refugee.ms_interlocutor.exceptions.UnderageException;
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

    public void create(CreateRequest dto) {
        var birthDate = LocalDate.parse(dto.birthDate());
        if (LocalDate.now().minusYears(18).isBefore(birthDate)) {
            throw new UnderageException();
        }

        if (this.repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailInUseException();
        }

        //todo: validar se cidade e estado existem

        var interlocutor = dto.toInterlocutor();
        interlocutor.setPassword(encoder.encode(dto.password()));

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
}
