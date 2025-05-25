package chat.talk_to_refugee.ms_interlocutor.resources.dtos;

import chat.talk_to_refugee.ms_interlocutor.entities.Interlocutor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CreateInterlocutor(
        @NotBlank String fullName,
        String aboutMe,
        @NotNull LocalDate birthDate,
        @NotBlank @Email String email,
        @NotBlank @Length(min = 8, max = 64) String password,
        String state,
        String city
) {
    public Interlocutor toInterlocutor() {
        return new Interlocutor(fullName, aboutMe, birthDate, email, state, city);
    }
}
