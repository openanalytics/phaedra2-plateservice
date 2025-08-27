/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.config;

import eu.openanalytics.phaedra.util.scalars.Scalars;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLScalarConfiguration {
    @Bean
    public GraphQLScalarType dateScalar() {
        return Scalars.dateType();
    }

    @Bean
    public GraphQLScalarType floatNaNScalar() {
        return Scalars.floatNaNType();
    }

    @Bean
    public GraphQLScalarType anyScalar() {
        return GraphQLScalarType.newScalar()
            .name("_Any")
            .description("Federation _Any scalar")
            .coercing(new Coercing<Object, Object>() {
                @Override
                public Object serialize(Object dataFetcherResult) {
                    return dataFetcherResult;
                }

                @Override
                public Object parseValue(Object input) {
                    return input;
                }

                @Override
                public Object parseLiteral(Object input) {
                    return input;
                }
            })
            .build();
    }

    @Bean
    public GraphQLScalarType fieldSetScalar() {
        return GraphQLScalarType.newScalar()
            .name("_FieldSet")
            .description("Federation _FieldSet scalar")
            .coercing(new Coercing<String, String>() {
                @Override
                public String serialize(Object dataFetcherResult) {
                    return dataFetcherResult != null ? dataFetcherResult.toString() : null;
                }

                @Override
                public String parseValue(Object input) {
                    return input != null ? input.toString() : null;
                }

                @Override
                public String parseLiteral(Object input) {
                    return input != null ? input.toString() : null;
                }
            })
            .build();
    }

}
