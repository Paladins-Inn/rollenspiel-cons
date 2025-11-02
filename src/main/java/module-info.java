/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
module de.paladinsinn.rollenspielcons {
  requires static lombok;
  
  requires java.desktop;
  requires jakarta.validation;
  requires jakarta.persistence;
  requires jakarta.annotation;
  requires jakarta.inject;
  requires spring.core;
  requires spring.web;
  requires spring.webflux;
  requires spring.security.core;
  requires spring.security.config;
  requires spring.security.oauth2.client;
  requires spring.security.oauth2.core;
  requires spring.security.oauth2.jose;
  requires spring.security.oauth2.resource.server;
  requires spring.security.web;
  requires spring.security.crypto;
  requires spring.boot.actuator;
  requires spring.boot.actuator.autoconfigure;
  requires reactor.core;
  requires org.reactivestreams;
  requires micrometer.core;
  requires micrometer.tracing;
  requires micrometer.jakarta9;
  requires micrometer.commons;
  requires org.eclipse.microprofile.openapi;
  requires brave.http;
  requires spring.context;
  requires spring.context.support;
  requires spring.aop;
  requires spring.aspects;
  requires spring.beans;
  requires spring.boot;
  requires spring.hateoas;
  requires com.github.benmanes.caffeine;
  requires spring.boot.autoconfigure;
  requires spring.data.commons;
  requires spring.data.jpa;
  requires spring.data.rest.core;
  requires spring.tx;
  requires com.fasterxml.jackson.databind; // jackson-databind
  requires liquibase.core;
  requires java.sql;
  requires info.picocli;
  requires w3w.java.wrapper;
  requires snowflake.id.generator;
  requires org.mapstruct;
  requires ical4j.core;
  requires org.slf4j;
  requires flexmark;
  requires org.apache.tomcat.embed.core;
  requires owasp.java.html.sanitizer;
  requires gg.jte.runtime;
  
  // Export top-level packages (note: subpackages must be exported individually if compile-time export is required)
  exports de.paladinsinn.rollenspielcons.domain.api;
  exports de.paladinsinn.rollenspielcons.domain.model;

  // Öffnen für Jackson / Spring Reflection (JSON-Deserialisierung, Proxies)
  opens de.paladinsinn.rollenspielcons.domain.api
        to com.fasterxml.jackson.databind;
  opens de.paladinsinn.rollenspielcons.domain.model
      to com.fasterxml.jackson.databind;
}