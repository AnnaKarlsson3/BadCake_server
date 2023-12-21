package com.dkserver.danielServer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DanielServerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DanielServerApplication.class, args);

	}

/*	@Bean
	public ServletWebServerFactory servletContainer(){
		//Enable SSL Traffic
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
			@Override
			protected void postProcessContext(Context context){
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		//Add HTTP to HTTPS redirect
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectionConnector());
		return tomcat;
	}

	private Connector httpToHttpsRedirectionConnector(){
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}*/
}
