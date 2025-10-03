package de.paladinsinn.rollenspielcons.domain.persistence.events;


import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@Repository
public interface PhysicalEventRepository extends PagingAndSortingRepository<JpaPhysicalEvent, Long> {
  @Query("SELECT e FROM PhysicalEvent e WHERE e.temporalData.startDay >= :start ORDER BY e.temporalData.startDay ASC")
  List<JpaPhysicalEvent> findByStartDay(@Param("start") @NotNull LocalDateTime start);
  
  @Query("SELECT e FROM PhysicalEvent e WHERE e.owner.owner = :owner ORDER BY e.temporalData.startDay ASC")
  List<JpaPhysicalEvent> findByOwner(@Param("owner") String owner);
}
