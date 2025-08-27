package eu.openanalytics.phaedra.plateservice.config;

import graphql.schema.DataFetcher;
import java.util.List;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQlSourceBuilderCustomizer graphQlSourceBuilderCustomizer(
        DataFetcher<String> serviceDataFetcher,
        DataFetcher<List<Object>> entitiesDataFetcher) {

        return builder -> builder.configureRuntimeWiring(runtimeWiringBuilder ->
            runtimeWiringBuilder
                .type("Query", typeWiring -> typeWiring
                    .dataFetcher("_service", serviceDataFetcher)
                    .dataFetcher("_entities", entitiesDataFetcher)
                )
        );
    }
}
