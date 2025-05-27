package chat.talk_to_refugee.ms_interlocutor.repositories;

import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterlocutorTypeRepository extends JpaRepository<InterlocutorType, Long> {
}
