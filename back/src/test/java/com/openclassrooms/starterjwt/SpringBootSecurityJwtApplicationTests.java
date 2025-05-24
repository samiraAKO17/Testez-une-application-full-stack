package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootSecurityJwtApplicationTests {


	@Test
	public void contextLoads() {
		// Vérifier que l'application se lance sans erreurs
		Assertions.assertTrue(true);
	}

	@Test
	void testMainRunsSpringApplication() {
		try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
			SpringBootSecurityJwtApplication.main(new String[]{});
			mockedSpringApplication.verify(() ->
					SpringApplication.run(SpringBootSecurityJwtApplication.class, new String[]{}));
		}
	}

}
