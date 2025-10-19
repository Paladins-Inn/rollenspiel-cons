package de.paladinsinn.rollenspielcons.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Configuration
public class HateoasConfig {

  @Bean
  public AnnotationLinkRelationProvider annotationLinkRelationProvider() {
    return new AnnotationLinkRelationProvider();
  }
}