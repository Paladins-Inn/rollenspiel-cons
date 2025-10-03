package de.paladinsinn.rollenspielcons.domain.persistence.events;


import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
public interface EventRepository extends PagingAndSortingRepository<JpaEvent, Long> {
  @RestResource(path = "/events")
  @Query("SELECT e FROM Event e WHERE e.temporalData.startDay >= :start ORDER BY e.temporalData.startDay ASC")
  Page<JpaEvent> findByStartDay(@Param("start") @NotNull LocalDateTime start, Pageable p);
  
  @RestResource(path = "/events/by-owner/{owner}")
  @Query("SELECT e FROM  Event e WHERE e.owner.owner = :owner ORDER BY e.temporalData.startDay ASC")
  Page<JpaEvent> findByOwner(@Param("owner") @NotNull String owner, Pageable p);

}
