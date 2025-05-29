package chat.talk_to_refugee.ms_interlocutor.resources.dtos;

import chat.talk_to_refugee.ms_interlocutor.entities.InterlocutorType;

import java.util.List;

public record SearchRequest(String fullName, String state, String city, List<InterlocutorType.Values> types) {
}
