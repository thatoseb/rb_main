package co.za.sbk.resource;

import co.za.sbk.TestUtil;
import co.za.sbk.domain.IncidentTypes;
import co.za.sbk.domain.enumeration.IncidentStatus;
import co.za.sbk.repository.IncidentTypesRepository;
import co.za.sbk.repository.UserRepository;
import co.za.sbk.service.IncidentTypesService;
import co.za.sbk.service.dto.IncidentDTO;
import co.za.sbk.service.dto.IncidentTypesDTO;
import co.za.sbk.service.impl.IncidentServiceImpl;
import co.za.sbk.service.mapper.IncidentTypesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static co.za.sbk.TestUtil.sameInstant;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(IncidentTypesResource.class)
class IncidentTypesResourceTest {

    private static final String BASE_API = "/api/incident-types";
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_INCIDENT_TYPE = "Theft";

    @Autowired
    MockMvc mvc;
    
    @MockBean
    IncidentTypesService service;

    @MockBean
    IncidentTypesRepository incidentTypesRepository;

    @MockBean
    UserRepository userRepository;
    
    @MockBean
    IncidentServiceImpl incidentService;

    @Autowired
    IncidentTypesMapper mapper;        
    
    IncidentTypesResource resource;
    
    IncidentTypesDTO dto;

    IncidentTypesDTO incidentTypesDTO;
    
    IncidentTypes incidentTypes;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        resource = new IncidentTypesResource(service);
        
        incidentTypes = new IncidentTypes();
        incidentTypes.setId(DEFAULT_ID);
        incidentTypes.setType(DEFAULT_INCIDENT_TYPE);
        
        dto = new IncidentTypesDTO();
        dto.setType(DEFAULT_INCIDENT_TYPE);

        incidentTypesDTO = mapper.toDto(incidentTypes);
    }

    @Test
    void testCreateIncidentTypes() throws Exception{
        IncidentTypesDTO incidentTypesDTO = mapper.toDto(incidentTypes);
        
        given(service.save(any())).willReturn(incidentTypesDTO);

        mvc.perform(post(BASE_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateIncidentTypes() throws Exception {
        
        given(service.save(incidentTypesDTO)).willReturn(incidentTypesDTO);
        
        mvc.perform(put(BASE_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(incidentTypesDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(incidentTypesDTO.getId().intValue()));
    }

    @Test
    void testGetAllIncidentTypes() throws Exception{

        List<IncidentTypesDTO> incidentTypesDTOS = new ArrayList<>();
        incidentTypesDTOS.add(incidentTypesDTO);
        Page<IncidentTypesDTO> typesPage = new PageImpl<IncidentTypesDTO>(incidentTypesDTOS);
        given(service.findAll(isA(Pageable.class))).willReturn(typesPage);

        mvc.perform(get(BASE_API + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(incidentTypes.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_INCIDENT_TYPE)));
    }

    @Test
    void testGetIncidentTypes() throws Exception {
        given(service.findOne(any())).willReturn(Optional.of(incidentTypesDTO));

        mvc.perform(get(BASE_API + "/{id}", incidentTypesDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.type").value(DEFAULT_INCIDENT_TYPE));
    }

    @Test
    void testDeleteIncidentTypes() throws Exception{
        mvc.perform(delete(BASE_API + "/{id}", incidentTypesDTO.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

}