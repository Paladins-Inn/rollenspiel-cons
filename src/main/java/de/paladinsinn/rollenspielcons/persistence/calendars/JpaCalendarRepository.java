package de.paladinsinn.rollenspielcons.persistence.calendars;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Repository
public interface JpaCalendarRepository extends CrudRepository<AbstractJpaCalendar, Long> {
  List<AbstractJpaCalendar> findByOwner_Owner(@NotNull String owner);
  List<AbstractJpaCalendar> findBySynctypevalue(@NotBlank String syncType);
}
