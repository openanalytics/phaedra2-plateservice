package eu.openanalytics.phaedra.plateservice.config;

import graphql.schema.DataFetcher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

@Configuration
public class GraphQLFederationConfig {

    private final PlateServiceEntityResolver plateServiceEntityResolver;

    public GraphQLFederationConfig(PlateServiceEntityResolver plateServiceEntityResolver) {
        this.plateServiceEntityResolver = plateServiceEntityResolver;
    }

    @Bean
    public DataFetcher<List<Object>> entitiesDataFetcher() {
        return env -> {
            List<Map<String, Object>> representations = env.getArgument("representations");
            return plateServiceEntityResolver.resolveEntities(representations);
        };
    }

    @Bean
    public DataFetcher<String> serviceDataFetcher() {
        return env -> loadSchemaFromClasspath();
    }

    private String loadSchemaFromClasspath() {
        try {
            ClassPathResource resource = new ClassPathResource("graphql/schema.graphqls");
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load GraphQL schema", e);
        }
    }
}
