/**
 * This is a configuration class
 * 
 */

package com.jorge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jorge.service.UserService;
import com.jorge.service.UserServiceImpl;

/**
 * Using the Java RMI, HTTP Invoker, Hessian, and REST
 * 
 * 		HTTP Invoker to interact with another Spring application
 * 		Java RMI to interact with another Java application not using Spring
 * 		Hessian to interact with another Java application not using Spring when you need to go over	proxies and firewalls
 * 		SOAP if you have to
 * 		REST for all other cases. REST is currently the most popular option; it's simple, flexible, and cross-platform
 *
 */
@Configuration // This declares it as a Spring configuration class
@EnableWebMvc // This enables Spring's ability to receive and process web requests. Necessary for interceptors too.
@ComponentScan(basePackages = { "com.jorge.controller" }) // This scans the com.jorge.controller package for Spring components

// @Import({ DatabaseConfig.class, SecurityConfig.class }) => //If you are using a Spring application without a 'ServletInitializer' class,
														      // you can include other configuration classes from your primary configuration class

public class AppConfig{

	 @Bean
	 // Necessary for Java RMI service. This returns an instance of UserServiceImpl
	 // SERVER SIDE
	 public UserService userService() {
		 return new UserServiceImpl();
	 }
	
	 
	 /***************************************************************************************************
	  * Creating a Java RMI service
	  * 
	  * The Java RMI is a Java remote method invocation technology; a client executes a method ocated on a
	  * server, the Java RMI service.
	  * In this recipe, we will set up a Java RMI service that will expose the methods of a normal Java class.
	  * The service will be part of an existing Spring web application but will use its own port
	  * 
	  * RmiServiceExporter is a Spring class generating an RMI service from a Java interface
	  * ( UserService ). For each method defined in UserService , the corresponding method from
	  * userService() , in UserServiceImpl , will be executed. The RMI service is made available
	  * default on the 1099 port.
	  * 
	  * The Java RMI service is now available at rmi://localhost:1099/userService
	  * 
	  */
	 @Bean
	 // This defines the Java RMI service name, the interface exposed by the service, and the object implementing it
	 // RMI SERVER SIDE
	 public RmiServiceExporter rmiServiceExporter() {
		 RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
		 
		 rmiServiceExporter.setServiceName("userService"); // Service name
		 rmiServiceExporter.setServiceInterface(UserService.class); // Refers to UserService interface
		 rmiServiceExporter.setService(userService()); // Refers to 'public UserService userService()' method above
		 
		 return rmiServiceExporter;
	 }
	 
}