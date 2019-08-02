package co.za.sbk.repository;

import co.za.sbk.domain.IncidentTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IncidentTypesService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentTypesRepository extends JpaRepository<IncidentTypes, Long> {

}
