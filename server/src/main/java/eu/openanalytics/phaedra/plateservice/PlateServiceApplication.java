package eu.openanalytics.phaedra.plateservice;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import eu.openanalytics.phaedra.measurementservice.client.config.MeasurementServiceClientAutoConfiguration;
import eu.openanalytics.phaedra.util.PhaedraRestTemplate;
import eu.openanalytics.phaedra.util.auth.AuthorizationServiceFactory;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import eu.openanalytics.phaedra.util.jdbc.JDBCUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@EnableDiscoveryClient
@EnableScheduling
@EnableCaching
@EnableWebSecurity
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@Import({MeasurementServiceClientAutoConfiguration.class})
public class PlateServiceApplication {
	private final ServletContext servletContext;
	private final Environment environment;

	public PlateServiceApplication(ServletContext servletContext, Environment environment) {
		this.servletContext = servletContext;
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PlateServiceApplication.class);
		app.run(args);
	}

	@Bean
	@LoadBalanced
	public PhaedraRestTemplate restTemplate() {
		return new PhaedraRestTemplate();
	}

	@Bean
	public DataSource dataSource() {
		String url = environment.getProperty("DB_URL");
		String username = environment.getProperty("DB_USER");
		String password = environment.getProperty("DB_PASSWORD");

		if (StringUtils.isEmpty(url)) {
			throw new RuntimeException("No database URL configured: " + url);
		}
		String driverClassName = JDBCUtils.getDriverClassName(url);
		if (driverClassName == null) {
			throw new RuntimeException("Unsupported database type: " + url);
		}

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public OpenAPI customOpenAPI() {
		Server server = new Server().url(servletContext.getContextPath()).description("Default Server URL");
		return new OpenAPI().addServersItem(server);
	}
	
	@Bean
	public IAuthorizationService authService() {
		return AuthorizationServiceFactory.create();
	}
	
	@Bean
	public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
				.csrf().disable()
				.oauth2ResourceServer().jwt();
		return http.build();
	}
}
