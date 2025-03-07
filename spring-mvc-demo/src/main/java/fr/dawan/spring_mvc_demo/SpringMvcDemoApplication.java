package fr.dawan.spring_mvc_demo;

import fr.dawan.spring_mvc_demo.formbeans.PlayerForm;
import fr.dawan.spring_mvc_demo.validators.PhoneNumberConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMvcDemoApplication {

	@Bean(name = "playerBean")
	public PlayerForm getPlayer(){
		PlayerForm p = new PlayerForm();
		p.setName("nom player");
		return p;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcDemoApplication.class, args);
//
 //
	}

}
