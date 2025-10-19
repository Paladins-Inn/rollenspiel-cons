package de.paladinsinn.rollenspielcons.persistence.mapper;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import org.springframework.stereotype.Component;

import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Converter
@Component
public class TemporalConverter implements AttributeConverter<Temporal, Instant> {
  @Override
  public Instant convertToDatabaseColumn(final Temporal data) {
    long e;
    int nanos;
    
    if (data instanceof LocalDate) {
      return Instant.from(((LocalDate) data).atStartOfDay().atZone(ZoneId.systemDefault()));
    }
    
    if (data.isSupported(INSTANT_SECONDS)) {
      e = data.getLong(INSTANT_SECONDS);
      nanos = data.isSupported(NANO_OF_SECOND) ? data.get(NANO_OF_SECOND) : 0;
    } else {
      throw new IllegalArgumentException("Temporal type not supported: " + data.getClass().getName());
    }
    
    return Instant.ofEpochSecond(e, nanos);
  }
  
  @Override
  public Temporal convertToEntityAttribute(final Instant data) {
    return data;
  }
}
