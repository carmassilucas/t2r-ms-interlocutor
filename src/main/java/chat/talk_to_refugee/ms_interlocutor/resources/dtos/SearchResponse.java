package chat.talk_to_refugee.ms_interlocutor.resources.dtos;

import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;

public record SearchResponse(String fullName, String email, String state, String city, InterlocutorType type) {
}
