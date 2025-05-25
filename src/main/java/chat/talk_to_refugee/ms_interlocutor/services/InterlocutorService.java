package chat.talk_to_refugee.ms_interlocutor.services;

import chat.talk_to_refugee.ms_interlocutor.repositories.InterlocutorRepository;
import chat.talk_to_refugee.ms_interlocutor.resources.dtos.CreateInterlocutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InterlocutorService {

    private final InterlocutorRepository repository;
    private final PasswordEncoder encoder;


    public InterlocutorService(InterlocutorRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void create(CreateInterlocutor dto) {
        if (LocalDate.now().minusYears(18).isBefore(dto.birthDate())) {
            throw new RuntimeException("minors are not allowed to register");
        }

        if (this.repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("email is already in use");
        }

        //todo: validar se cidade e estado existem

        var interlocutor = dto.toInterlocutor();
        interlocutor.setPassword(encoder.encode(dto.password()));

        this.repository.save(interlocutor);
    }
}
