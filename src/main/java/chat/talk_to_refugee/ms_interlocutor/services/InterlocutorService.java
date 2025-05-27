package chat.talk_to_refugee.ms_interlocutor.services;

import chat.talk_to_refugee.ms_interlocutor.exceptions.*;
import chat.talk_to_refugee.ms_interlocutor.repositories.InterlocutorRepository;
import chat.talk_to_refugee.ms_interlocutor.resources.dtos.CreateInterlocutor;
import chat.talk_to_refugee.ms_interlocutor.resources.dtos.UpdateInterlocutor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterlocutorService {

    private final InterlocutorRepository repository;
    private final PasswordEncoder encoder;

    public void create(CreateInterlocutor dto) {
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

    @Transactional
    public void update(UpdateInterlocutor dto, UUID id) {
        if (dto.isEmpty()) {
            throw new EmptyBodyException();
        }

        var interlocutor = this.repository.findById(id).orElseThrow(NotFoundException::new);

        copyNonNullProperties(dto, interlocutor);

        this.repository.save(interlocutor);
    }

    private static void copyNonNullProperties(Object source, Object target) {
        for (Field sourceField : source.getClass().getDeclaredFields()) {
            sourceField.setAccessible(true);
            try {
                var value = sourceField.get(source);

                if (value != null && !value.toString().isBlank()) {
                    var targetField = target.getClass().getDeclaredField(sourceField.getName());
                    targetField.setAccessible(true);

                    if (targetField.getType().equals(LocalDate.class) && value instanceof String) {
                        targetField.set(target, LocalDate.parse((String) value));
                        continue;
                    }

                    targetField.set(target, value);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new CommonException();
            }
        }
    }
}
