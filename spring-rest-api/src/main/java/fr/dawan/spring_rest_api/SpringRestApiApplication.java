package fr.dawan.spring_rest_api;

import fr.dawan.spring_rest_api.entities.Product;

import fr.dawan.spring_rest_api.entities.Salarie;
import fr.dawan.spring_rest_api.interceptors.MyInterceptor;
import fr.dawan.spring_rest_api.interceptors.TokenInterceptor;
import fr.dawan.spring_rest_api.services.AsyncService;
import fr.dawan.spring_rest_api.services.CacheSalarieService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Activer la saisie du Token dans Swagger UI
 */
@SecurityScheme(name = "BearerToken", type = SecuritySchemeType.HTTP, scheme = "Bearer", description = "Enter your Token here:")
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
//@EnableScheduling
public class SpringRestApiApplication implements CommandLineRunner {

	@Value("${java.home}")
	private String javaHome;

	@Autowired
	private AsyncService asyncService;


	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Bean
	public PasswordEncoder getEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*
	Accéder à l'environnement de Spring
	 */
	@Autowired
	private Environment environment;

	@Autowired
	private MyInterceptor myInterceptor;

	@Autowired
	private TokenInterceptor tokenInterceptor;

	@Autowired
	private CacheSalarieService salarieService;


	//Maj de la configuration du module MVC: ajout d'un intercepteur
	@Bean
	public WebMvcConfigurer getConfig(){

		return new WebMvcConfigurer() {

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(myInterceptor);
				registry.addInterceptor(tokenInterceptor);
			}

			/*
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET","POST","DELETE","PUT");

			}*/
		};

	}
/*
CORS: CROSS ORIGIN RESOURCES SHARING
 */
	public static void main(String[] args) {
		SpringApplication.run(SpringRestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(javaHome);
		System.out.println(environment.getProperty("java.home"));

		//Ecriture dans un fichier.properties
		Properties p = new Properties();
		FileOutputStream fos = new FileOutputStream("login.properties");
		p.put("user", "Admin");
		p.put("password", "@@dsdqsd");
		p.store(fos,"Params de connexion");
		fos.close();

		//Lecture d'un fichier.properties
		FileInputStream fis = new FileInputStream("login.properties");
		p.load(fis);
		System.out.println(p.getProperty("user"));
		System.out.println(p.getProperty("password"));
		fis.close();

		Product prod = new Product();
		prod.setDescription("pc");
		System.out.println(prod.getDescription());

		Contact c = new Contact();
		c.setFirstName("Fname");
		c.setLastName("Lname");
		ModelMapper mapper = new ModelMapper();

		mapper.typeMap(Contact.class, ContactDto.class)
				.addMappings(m -> {
			m.map(src -> src.getFirstName(),ContactDto::setNom);
			m.map(src -> src.getLastName(),ContactDto::setPrenom);

		});


		ContactDto dto = mapper.map(c, ContactDto.class);
		System.out.println(dto);

		System.out.println(">>>>> Appelle des méthodes asynchrones:");
		asyncService.methodVoid();
		CompletableFuture<String> resul = asyncService.methodWithReturn();
		System.out.println(resul.get());

		if(salarieService.getAll().size() == 0) {

			System.out.println(">>> insertion des salariés");

			Salarie s1 = new Salarie();
			s1.setNom("DUPONT");
			s1.setPrenom("Jean");
			salarieService.save(s1);

			Salarie s2 = new Salarie();
			s2.setNom("DAWAN");
			s2.setPrenom("Paris");
			salarieService.save(s2);
		}

	}
}

@Getter
@Setter
class Contact{
	private String firstName;
	private String lastName;
}


@Getter
@Setter
@ToString
class ContactDto{
	private String nom;
	private String prenom;
}