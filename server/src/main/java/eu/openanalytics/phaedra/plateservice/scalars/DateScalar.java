/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateScalar implements Coercing<Date, String> {

    @Override
    public String serialize(final Object dataFetcherResult) {
        if (dataFetcherResult instanceof Date) {
            return DateFormatUtils.format((Date) dataFetcherResult, "yyyy-MM-ddTHH:mm:ss.SS");
        } else {
            throw new CoercingSerializeException("Expected a LocalDate object.");
        }
    }

    @Override
    public Date parseValue(final Object input) {
        try {
            if (input instanceof String) {
                return DateUtils.parseDate((String) input, "yyyy-MM-ddTHH:mm:ss.SS");
            } else {
                throw new CoercingParseValueException("Expected a String");
            }
        } catch (ParseException e) {
            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e);
        }
    }

    @Override
    public Date parseLiteral(final Object input) {
        if (input instanceof StringValue) {
            try {
                return DateUtils.parseDate(((StringValue) input).getValue(), "yyyy-MM-ddTHH:mm:ss.SS");
            } catch (ParseException e) {
                throw new CoercingParseLiteralException(e);
            }
        } else {
            throw new CoercingParseLiteralException("Expected a StringValue.");
        }
    }
}
