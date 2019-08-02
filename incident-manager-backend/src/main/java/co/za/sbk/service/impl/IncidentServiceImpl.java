package co.za.sbk.service.impl;

import co.za.sbk.domain.Incident;
import co.za.sbk.repository.IncidentRepository;
import co.za.sbk.repository.IncidentTypesRepository;
import co.za.sbk.service.IncidentService;
import co.za.sbk.service.dto.IncidentDTO;
import co.za.sbk.service.mapper.IncidentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final Logger log = LoggerFactory.getLogger(IncidentServiceImpl.class);

    private final IncidentRepository incidentRepository;

    private final IncidentMapper incidentMapper;

    private final IncidentTypesRepository incidentTypesRepository;

    public IncidentServiceImpl(IncidentRepository incidentRepository, IncidentMapper incidentMapper, IncidentTypesRepository incidentTypesRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
        this.incidentTypesRepository = incidentTypesRepository;
    }

    @Override
    public IncidentDTO save(IncidentDTO incidentDTO) {
        log.debug("Request to save Incident : {}", incidentDTO);
        Incident incident = incidentMapper.toEntity(incidentDTO);
        Long incidentTypesId = incidentDTO.getIncidentTypesId();
        incidentTypesRepository.findById(incidentTypesId).ifPresent(incident::incidentTypes);
        incident = incidentRepository.save(incident);
        return incidentMapper.toDto(incident);
    }

    /**
     * Get all the incidents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IncidentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll(pageable)
                .map(incidentMapper::toDto);
    }

    /**
     * Get all the incidents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IncidentDTO> findAllWithEagerRelationships(Pageable pageable) {
        log.debug("Request to get all Incidents with Eager");
        return incidentRepository.findAllWithEagerRelationships(pageable)
                .map(incidentMapper::toDto);
    }


    /**
     * Get one incident by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IncidentDTO> findOne(Long id) {
        log.debug("Request to get Incident : {}", id);
        return incidentRepository.findOneWithEagerRelationships(id)
                .map(incidentMapper::toDto);
    }

    /**
     * Delete the incident by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.deleteById(id);
    }
}
