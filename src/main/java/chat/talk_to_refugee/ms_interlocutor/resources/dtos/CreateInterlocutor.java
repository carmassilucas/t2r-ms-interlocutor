package chat.talk_to_refugee.ms_interlocutor.resources.dtos;

import chat.talk_to_refugee.ms_interlocutor.entities.Interlocutor;
import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CreateInterlocutor(@NotBlank String fullName,
                                 String aboutMe,
                                 @NotNull @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String birthDate,
                                 @Email @NotBlank String email,
                                 @NotBlank @Length(min = 8, max = 64) String password,
                                 String state,
                                 String city,
                                 @NotNull InterlocutorType.Values type
) {

    public Interlocutor toInterlocutor() {
        return Interlocutor.builder()
                .fullName(fullName)
                .aboutMe(aboutMe)
                .birthDate(LocalDate.parse(birthDate))
                .email(email)
                .state(state)
                .city(city)
                .type(type.get())
                .build();
    }
}
