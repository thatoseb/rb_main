package co.za.sbk.service.impl;

import co.za.sbk.domain.IncidentTypes;
import co.za.sbk.repository.IncidentTypesRepository;
import co.za.sbk.service.IncidentTypesService;
import co.za.sbk.service.dto.IncidentTypesDTO;
import co.za.sbk.service.mapper.IncidentTypesMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentTypesServiceImplTest {
    
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_INCIDENT_TYPE_DESC = "Theft";
    
    @Mock
    IncidentTypesRepository repository;
    
    @Mock
    IncidentTypesMapper mapper;
    
    IncidentTypesService service;
    
    IncidentTypesDTO incidentTypesDTO;
    
    IncidentTypes incidentTypes;
    
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        service = new IncidentTypesServiceImpl(repository, mapper);
        
        incidentTypesDTO = new IncidentTypesDTO();
        incidentTypesDTO.setId(DEFAULT_ID);
        incidentTypesDTO.setType(DEFAULT_INCIDENT_TYPE_DESC);
        
        incidentTypes = new IncidentTypes();
        incidentTypes.setId(DEFAULT_ID);
        incidentTypes.setType(DEFAULT_INCIDENT_TYPE_DESC);
    }

    @Test
    void testSave() {
        given(mapper.toEntity(incidentTypesDTO)).willReturn(incidentTypes);
        given(repository.save(incidentTypes)).willReturn(incidentTypes);
        given(mapper.toDto(incidentTypes)).willReturn(incidentTypesDTO);
        
        IncidentTypesDTO savedDto = service.save(incidentTypesDTO);

        assertEquals(DEFAULT_ID, savedDto.getId());
        assertEquals(DEFAULT_INCIDENT_TYPE_DESC, savedDto.getType());
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
        List<IncidentTypes> incidentTypesList = new ArrayList<>();
        incidentTypesList.add(incidentTypes);
        Page<IncidentTypes> typesPage = new PageImpl<IncidentTypes>(incidentTypesList);
        
        given(repository.findAll(pageable)).willReturn(typesPage);
        given(mapper.toDto(incidentTypes)).willReturn(incidentTypesDTO);
        
        Page<IncidentTypesDTO> incidentTypesDTOS = service.findAll(pageable);
        incidentTypesDTO = incidentTypesDTOS.getContent().get(0);

        assertEquals(DEFAULT_ID, incidentTypesDTO.getId());
        assertEquals(DEFAULT_INCIDENT_TYPE_DESC, incidentTypesDTO.getType());
        
    }

    @Test
    void testFindOne() {
        given(repository.findById(anyLong())).willReturn(Optional.of(incidentTypes));
        given(mapper.toDto(incidentTypes)).willReturn(incidentTypesDTO);
        
        Optional<IncidentTypesDTO> typesDTO = service.findOne(anyLong());

        assertNotNull(typesDTO);
        assertEquals(Long.valueOf("1"), typesDTO.get().getId());
        
        then(repository).should(times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        service.delete(1L);

        then(repository).should().deleteById(1L);
        then(repository).shouldHaveNoMoreInteractions();
    }

}