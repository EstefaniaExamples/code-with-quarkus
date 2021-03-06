package org.springboot.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication(proxyBeanMethods = false)
public class Application {
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(Application.class, args);
		displayAllBeans();
	}

	public static void displayAllBeans() {
		String[] allBeanNames = applicationContext.getBeanDefinitionNames();
		AtomicInteger counter = new AtomicInteger();
		String SPACE = " ";

		Arrays.stream(allBeanNames)
				.map(beanName -> new StringBuilder()
						.append(counter.getAndIncrement())
						.append(SPACE)
						.append(beanName)
						.toString())
				.forEach(System.out::println);
	}
}
