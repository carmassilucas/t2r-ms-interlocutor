package chat.talk_to_refugee.ms_interlocutor.resources;

import chat.talk_to_refugee.ms_interlocutor.resources.dtos.CreateInterlocutor;
import chat.talk_to_refugee.ms_interlocutor.resources.dtos.ProfileResponse;
import chat.talk_to_refugee.ms_interlocutor.resources.dtos.UpdateInterlocutor;
import chat.talk_to_refugee.ms_interlocutor.services.InterlocutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/interlocutors")
@RequiredArgsConstructor
public class InterlocutorResource {

    private final InterlocutorService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateInterlocutor dto) {
        this.service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable(name = "id") UUID id,
                                       @RequestBody @Valid UpdateInterlocutor dto) {
        this.service.update(dto, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/profile/{id}")
    public ResponseEntity<ProfileResponse> profile(@PathVariable(name = "id") UUID id) {
        var profile = this.service.profile(id);
        return ResponseEntity.ok(profile);
    }
}
