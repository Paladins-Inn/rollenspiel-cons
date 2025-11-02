package de.paladinsinn.rollenspielcons.domain.api.events;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * The port for the model to persist events.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
public interface EventPersistencePort {
  /**
   * @param event to create
   * @return The created event with id and version set
   */
  Event create(@NotNull final Event event) throws EventPersistenceException;
  
  
  /**
   * @param id of the event to retrieve
   * @return The event with the given id or an empty Optional
   */
  Optional<Event> retrieveById(@NotNull final Long id);
  /**
   * @param owner of the event to retrieve
   * @return The event with the given owner or an empty Optional
   */
  List<Event> retrieveByOwner(@NotBlank final String owner);
  /**
   * @param label of the event to retrieve
   * @return The event with the given label or an empty Optional
   */
  List<Event> retrieveByLabel(@NotBlank final String label);
  /**
   * @param date of the event to retrieve
   * @return The event with the given calendar or an empty Optional
   */
  List<Event> retrieveByDate(@NotNull final LocalDate date);
  /**
   * @param start of the event to retrieve
   * @param end of the event to retrieve
   * @return The event between the given dates
   */
  List<Event> retrieveByDate(@NotNull final LocalDate start, @NotNull final LocalDate end);
  
  
  /**
   * @param event to update
   * @return The updated event
   */
  Event update(@NotNull final Event event) throws EventPersistenceException;
  
  /**
   * @param event to delete
   */
  void delete(@NotNull final Event event) throws EventPersistenceException;
}
