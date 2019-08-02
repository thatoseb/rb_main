package co.za.sbk.repository;

import co.za.sbk.domain.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long>{
    
    @Query("select incident from Incident incident where incident.user.id = :userId")
    List<Incident> findByUserIsCurrentUser(@Param("userId") Long userId);

    @Query(value = "select distinct incident from Incident incident left join fetch incident.officers left join fetch incident.suspects",
            countQuery = "select count(distinct incident) from Incident incident")
    Page<Incident> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct incident from Incident incident left join fetch incident.officers left join fetch incident.suspects")
    List<Incident> findAllWithEagerRelationships();

    @Query("select incident from Incident incident left join fetch incident.officers left join fetch incident.suspects where incident.id =:id")
    Optional<Incident> findOneWithEagerRelationships(@Param("id") Long id);
}
