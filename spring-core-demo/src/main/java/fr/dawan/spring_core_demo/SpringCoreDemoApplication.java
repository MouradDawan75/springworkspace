package fr.dawan.spring_core_demo;

import fr.dawan.spring_core_demo.entities.Voiture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;




@SpringBootApplication
//@ComponentScan()
public class SpringCoreDemoApplication implements CommandLineRunner {

	/*
	Bean: désigne un objet crée par Spring
	 */

	@Value("${mon.param}")
	private String param;

	static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(SpringCoreDemoApplication.class, args);
		test();
		allBeans();
	}

	private static void allBeans() {
		String[] beans = context.getBeanDefinitionNames();
		System.out.println(">>>>>>>>>>> All beans:");
		for(String b : beans){
			System.out.println(b);
		}
	}

	private static void test() {
		Voiture v = context.getBean(Voiture.class);
		v.demarrer();
	}

	/*
	Méthode exécutée après l'initialisation du context
	 */
	@Override
	public void run(String... args) throws Exception {
		System.out.println(">>>> méthode run de CommandLineRunner <<<<<<");
		System.out.println(param);
	}
}
