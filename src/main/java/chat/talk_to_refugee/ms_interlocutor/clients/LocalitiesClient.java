package chat.talk_to_refugee.ms_interlocutor.clients;

import chat.talk_to_refugee.ms_interlocutor.resources.dtos.LocalitiesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "localities-api", url = "https://servicodados.ibge.gov.br", path = "/api/v1/localidades/estados")
public interface LocalitiesClient {

    @GetMapping(value = "/{UF}/municipios")
    List<LocalitiesResponse> findByUF(@PathVariable(name = "UF") String uf);
}
