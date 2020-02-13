package com.gfi.LDVELHWebService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gfi.LDVELHWebService.bll.EpubToDB;


@SpringBootApplication
public class LdvelhWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LdvelhWebServiceApplication.class, args);
//		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//		EpubToDB bll = (EpubToDB) context.getBean("EpubToDBµImpl");
//		Livre1 livre = new Livre1();
//		livre.setId(1);
//		livre.setChapitre(
//				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		bll.loadParagraphe(livre);
	}
	
}