package chat.talk_to_refugee.ms_interlocutor.resources.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LocalitiesResponse(@JsonProperty(value = "id") Long id, @JsonProperty(value = "nome") String name) {

}
