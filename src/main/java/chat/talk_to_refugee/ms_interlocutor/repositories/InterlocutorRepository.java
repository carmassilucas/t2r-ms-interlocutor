package chat.talk_to_refugee.ms_interlocutor.repositories;

import chat.talk_to_refugee.ms_interlocutor.entities.Interlocutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InterlocutorRepository extends JpaRepository<Interlocutor, UUID> {
    Optional<Interlocutor> findByEmail(String email);
}
