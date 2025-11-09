package de.paladinsinn.rollenspielcons.persistence.events;


import de.paladinsinn.rollenspielcons.persistence.time.EmbeddableTimeSpec;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@Repository
public interface JpaEventRepository extends PagingAndSortingRepository<JpaEvent, Long>,
                                            ListCrudRepository<JpaEvent, Long> {
  @RestResource(path = "/events")
  @Query("SELECT e FROM Event e WHERE e.temporalData.startDay >= :start ORDER BY e.temporalData.startDay ASC")
  Page<JpaEvent> findByStartDay(@Param("start") @NotNull LocalDateTime start, Pageable p);
  
  @RestResource(path = "/events/by-date-range")
  @Query("SELECT e FROM Event e WHERE e.temporalData.startDay >= :start AND e.temporalData.duration <= :duration ORDER BY e.temporalData.startDay ASC")
  Page<JpaEvent> findByStartDayAndEndDay(
      @Param("start") @NotNull LocalDateTime start,
      @Param("duration") @NotNull Duration duration,
      Pageable p
  );
  
  @RestResource(path = "/events/by-owner/{owner}")
  @Query("SELECT e FROM  Event e WHERE e.owner.owner = :owner ORDER BY e.temporalData.startDay ASC")
  Page<JpaEvent> findByOwner(@Param("owner") @NotNull String owner, Pageable p);
  
  List<JpaEvent> findAllByTemporalDataIsAfter(EmbeddableTimeSpec temporalDataAfter);
}
