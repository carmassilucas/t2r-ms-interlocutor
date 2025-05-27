package chat.talk_to_refugee.ms_interlocutor.resources.dtos;

import chat.talk_to_refugee.ms_interlocutor.exceptions.CommonException;
import jakarta.validation.constraints.Pattern;

import java.lang.reflect.Field;

public record UpdateInterlocutor(String fullName, String aboutMe, String state, String city,
                                 @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String birthDate) {

    public Boolean isEmpty() {
        for (Field field : UpdateInterlocutor.class.getDeclaredFields()) {
            try {
                var value = field.get(this);
                if (value != null && !value.toString().isBlank()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new CommonException();
            }
        }
        return true;
    }
}
