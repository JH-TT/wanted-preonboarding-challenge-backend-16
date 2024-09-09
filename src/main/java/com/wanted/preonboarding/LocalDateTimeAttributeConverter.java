package com.wanted.preonboarding;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Converter(autoApply = true)
@Slf4j
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return (localDateTime == null ? null : Timestamp.valueOf(localDateTime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return (timestamp == null ? null : timestamp.toLocalDateTime());
    }
}
