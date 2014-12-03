package com.denispalchuk.epam.task.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Created by denis on 11/26/14.
 */
public class CustomDataDeserializer extends JsonDeserializer<DateTime> {
    private static DateTimeFormatter formatter =  DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");;

    @Override
    public DateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        return formatter.parseDateTime(parser.getText());
    }
}