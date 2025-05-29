package chat.talk_to_refugee.ms_interlocutor.repositories;

import chat.talk_to_refugee.ms_interlocutor.entities.Interlocutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InterlocutorRepository extends JpaRepository<Interlocutor, UUID> {
    Optional<Interlocutor> findByEmail(String email);

    @Query(
            value = "select * from tb_interlocutor ti " +
                    "where (:fullName is null or ti.full_name ilike concat('%', :fullName, '%')) " +
                    "and (:state is null or ti.state ilike concat('%', :state, '%')) " +
                    "and (:city is null or ti.city ilike concat('%', :city, '%')) " +
                    "and ti.type_id in :types " +
                    "and ti.id <> :id " +
                    "order by created_at",
            nativeQuery = true
    )
    List<Interlocutor> search(
            @Param("fullName") String fullName,
            @Param("state") String state,
            @Param("city") String city,
            @Param("types") List<Long> types,
            @Param("id") UUID id
    );
}
