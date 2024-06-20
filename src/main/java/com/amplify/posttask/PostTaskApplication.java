package com.amplify.posttask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostTaskApplication {

	@Value("${http.proxy.server.endpoint}")
	public static String proxyEndpoint;
	
	public static void main(String[] args) {
		SpringApplication.run(PostTaskApplication.class, args);
	}

	// @Bean
 //  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
 //    return args -> {

 //      System.out.println("Let's inspect the beans provided by Spring Boot:");

 //      String[] beanNames = ctx.getBeanDefinitionNames();
 //      Arrays.sort(beanNames);
 //      for (String beanName : beanNames) {
 //        System.out.println(beanName);
 //      }
 //    };
 //  }

}
