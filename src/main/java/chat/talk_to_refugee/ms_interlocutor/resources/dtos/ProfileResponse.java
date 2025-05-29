package chat.talk_to_refugee.ms_interlocutor.resources.dtos;

import chat.talk_to_refugee.ms_interlocutor.entities.Interlocutor;
import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;

import java.time.LocalDate;

public record ProfileResponse(String fullName,
                              String aboutMe,
                              LocalDate birthDate,
                              String email,
                              String state,
                              String city,
                              InterlocutorType type
) {

    public static ProfileResponse fromInterlocutor(Interlocutor interlocutor) {
        return new ProfileResponse(
                interlocutor.getFullName(),
                interlocutor.getAboutMe(),
                interlocutor.getBirthDate(),
                interlocutor.getEmail(),
                interlocutor.getState(),
                interlocutor.getCity(),
                interlocutor.getType()
        );
    }
}
