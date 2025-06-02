package chat.talk_to_refugee.ms_interlocutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsInterlocutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInterlocutorApplication.class, args);
	}

}
