package com.timeyang.amanda.core.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@Converter
public class InstantConverter implements AttributeConverter<Instant, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(Instant instant) {
        return instant == null ? null : new Timestamp(instant.toEpochMilli());
    }

    @Override
    public Instant convertToEntityAttribute(Timestamp timestamp) {
        return timestamp == null ? null : Instant.ofEpochMilli(timestamp.getTime());
    }
}
