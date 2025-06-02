package chat.talk_to_refugee.ms_interlocutor.utils;

import chat.talk_to_refugee.ms_interlocutor.entities.Interlocutor;
import chat.talk_to_refugee.ms_interlocutor.exceptions.CommonException;
import chat.talk_to_refugee.ms_interlocutor.resources.dtos.UpdateRequest;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class CustomBeanUtils {

    public static void copyNonNullProperties(UpdateRequest dto, Interlocutor interlocutor) {
        for (Field dtoField : dto.getClass().getDeclaredFields()) {
            dtoField.setAccessible(true);
            try {
                var value = dtoField.get(dto);

                if (value != null && !value.toString().isBlank()) {
                    var interlocutorField = interlocutor.getClass().getDeclaredField(dtoField.getName());
                    interlocutorField.setAccessible(true);

                    if (interlocutorField.getType().equals(LocalDate.class) && value instanceof String) {
                        interlocutorField.set(interlocutor, LocalDate.parse((String) value));
                        continue;
                    }

                    interlocutorField.set(interlocutor, value);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new CommonException();
            }
        }
    }
}
