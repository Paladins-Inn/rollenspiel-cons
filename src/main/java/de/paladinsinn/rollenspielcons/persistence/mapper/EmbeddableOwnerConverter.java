package de.paladinsinn.rollenspielcons.persistence.mapper;


import de.paladinsinn.rollenspielcons.persistence.EmbeddableOwner;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-11-02
 */
@Converter(autoApply = true)
@Slf4j
public class EmbeddableOwnerConverter implements AttributeConverter<EmbeddableOwner, String> {

  @Override
  public String convertToDatabaseColumn(EmbeddableOwner attribute) {
    log.trace("enter - {}", attribute);
    
    String result = attribute == null ? null : attribute.getOwner();
    
    log.trace("exit - {}", result);
    return result;
  }

  @Override
  public EmbeddableOwner convertToEntityAttribute(String dbData) {
    log.trace("enter - {}", dbData);
    
    EmbeddableOwner result = dbData == null ? null : EmbeddableOwner.builder().owner(dbData).build();
    
    log.trace("exit - {}", result);
    return result;
  }
}