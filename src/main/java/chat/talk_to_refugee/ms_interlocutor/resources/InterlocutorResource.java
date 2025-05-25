package chat.talk_to_refugee.ms_interlocutor.resources;

import chat.talk_to_refugee.ms_interlocutor.resources.dtos.CreateInterlocutor;
import chat.talk_to_refugee.ms_interlocutor.services.InterlocutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/interlocutors")
public class InterlocutorResource {


    private final InterlocutorService service;

    public InterlocutorResource(InterlocutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid CreateInterlocutor dto) {
        try {
            this.service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }

    }
}
