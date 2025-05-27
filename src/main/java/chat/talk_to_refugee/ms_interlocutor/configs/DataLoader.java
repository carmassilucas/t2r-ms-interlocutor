package chat.talk_to_refugee.ms_interlocutor.configs;

import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;
import chat.talk_to_refugee.ms_interlocutor.repositories.InterlocutorTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final InterlocutorTypeRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (this.repository.count() > 0) return;

        Arrays.stream(InterlocutorType.Values.values())
                .forEach(type -> this.repository.save(type.get()));
    }
}
