package eu.openanalytics.phaedra.plateservice;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.StringUtils;

import eu.openanalytics.phaedra.util.jdbc.JDBCUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class PlateService {

	private static final String PROP_DB_URL = "plateService.db.url";
	private static final String PROP_DB_USERNAME = "plateService.db.username";
	private static final String PROP_DB_PASSWORD = "plateService.db.password";
		  	
	@Autowired
	private Environment environment;
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PlateService.class);
		app.run(args);
	}

	@Bean
	public DataSource plateDataSource() {
		String url = environment.getProperty(PROP_DB_URL);
		if (StringUtils.isEmpty(url)) {
			throw new RuntimeException("No database URL configured: " + PROP_DB_URL);
		}
		String driverClassName = JDBCUtils.getDriverClassName(url);
		if (driverClassName == null) {
			throw new RuntimeException("Unsupported database type: " + url);
		}
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(environment.getProperty(PROP_DB_USERNAME));
		dataSource.setPassword(environment.getProperty(PROP_DB_PASSWORD));
		return dataSource;
	}
}
