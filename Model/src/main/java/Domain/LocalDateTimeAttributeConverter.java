package Domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convertToDatabaseColumn(LocalDateTime locDateTime) {
        return locDateTime != null ? locDateTime.format(formatter) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String sqlTimestamp) {
        return sqlTimestamp != null ? LocalDateTime.parse(sqlTimestamp, formatter) : null;
    }
}
