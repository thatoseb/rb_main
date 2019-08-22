package co.za.sbk.service.impl;

import co.za.sbk.domain.*;
import co.za.sbk.domain.enumeration.Gender;
import co.za.sbk.domain.enumeration.IncidentStatus;
import co.za.sbk.repository.IncidentRepository;
import co.za.sbk.repository.IncidentTypesRepository;
import co.za.sbk.service.IncidentService;
import co.za.sbk.service.dto.IncidentDTO;
import co.za.sbk.service.mapper.IncidentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.mockito.BDDMockito.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IncidentServiceImplTest {

    @Mock
    IncidentRepository incidentRepository;

    @Mock
    IncidentMapper incidentMapper;

    @Mock
    IncidentTypesRepository incidentTypesRepository;
    

    IncidentService service;
    
    Incident incident;
    Officer officer;
    Suspect suspect;
    User user;
    IncidentTypes incidentTypes;
    IncidentDTO incidentDTO;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        service = new IncidentServiceImpl(incidentRepository, incidentMapper, incidentTypesRepository);

        incidentTypes = new IncidentTypes();
        incidentTypes.setId(1L);
        incidentTypes.setType("CRIME");
        
        user = new User();
        user.setId(1L);
        user.setIdnumber("ME");
        user.setGender(Gender.MALE);
        user.setFirstName("Thato");
        user.setLastName("Seboko");
        
        incidentDTO = new IncidentDTO();
        incidentDTO.setId(1L);
        incidentDTO.setLocation("JohannesBurg");
        incidentDTO.setDescription("3423");
        incidentDTO.setIncidentTypesId(incidentTypes.getId());
        incidentDTO.setIncidentTypesType(incidentTypes.getType());
        incidentDTO.setUserId(user.getId());
        incidentDTO.setStartDate(ZonedDateTime.now());
        incidentDTO.setIncidentStatus(IncidentStatus.OPEN);
        
        incident = new Incident();
        incident.setId(1L);
        incident.setLocation("JohannesBurg");
        incident.setDescription("3423");
        incident.setIncidentTypes(incidentTypes);
        incident.user(user);
        incident.setStartDate(ZonedDateTime.now());
        incident.setIncidentStatus(IncidentStatus.OPEN);
    }

    @Test
    void testSave() {
        given(incidentMapper.toEntity(incidentDTO)).willReturn(incident);
        given(incidentTypesRepository.findById(anyLong())).willReturn(Optional.of(incidentTypes));
        given(incidentMapper.toDto(incident)).willReturn(incidentDTO);
        given(incidentRepository.save(incident)).willReturn(incident);
        
        IncidentDTO savedDto = service.save(incidentDTO);
        
        then(incidentRepository).should().save(incident);
                
        assertEquals("JohannesBurg", savedDto.getLocation());
        
    }

    @Test
    void testFindAll() {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        List<Incident> list = new ArrayList<>();
        list.add(incident);
        Page<Incident> pageObj = new PageImpl<>(list);
        given(incidentMapper.toDto(incident)).willReturn(incidentDTO);
        given(incidentRepository.findAll(pageable)).willReturn(pageObj);
        
        Page<IncidentDTO> incidentDTOS = service.findAll(pageable);
        IncidentDTO incidentDTO = incidentDTOS.getContent().get(0);
        
        assertEquals("JohannesBurg", incidentDTO.getLocation());
    }

    @Test
    void testFindOne() {
        given(incidentRepository.findOneWithEagerRelationships(1L)).willReturn(Optional.of(incident));
        given(incidentMapper.toDto(incident)).willReturn(incidentDTO);
        
        Optional<IncidentDTO> incidentDTO = service.findOne(1L);
        
        assertNotNull(incidentDTO);
        
        then(incidentRepository).should(atLeast(1)).findOneWithEagerRelationships(anyLong());
    }

    @Test
    void testDelete() {        
        service.delete(1L);
        
        then(incidentRepository).should().deleteById(1L);
        then(incidentRepository).shouldHaveNoMoreInteractions();
    }
}