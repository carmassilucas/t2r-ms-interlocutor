package chat.talk_to_refugee.ms_interlocutor.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_interlocutor_type")
@NoArgsConstructor
@AllArgsConstructor
public @Data class InterlocutorType {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "description", unique = true, nullable = false, updatable = false)
    private String name;

    public enum Values {
        ADMIN(1L, "admin"),
        COLLABORATOR(2L, "collaborator"),
        IMMIGRANT(3L, "immigrant"),
        REFUGEE(4L, "refugee");

        private final Long id;
        private final String name;

        Values(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public InterlocutorType get() {
            return new InterlocutorType(id, name);
        }
    }
}
