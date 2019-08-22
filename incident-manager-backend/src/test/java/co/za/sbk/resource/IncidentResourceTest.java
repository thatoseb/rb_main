package co.za.sbk.resource;

import co.za.sbk.TestUtil;
import co.za.sbk.domain.*;
import co.za.sbk.domain.enumeration.Gender;
import co.za.sbk.domain.enumeration.IncidentStatus;
import co.za.sbk.repository.IncidentTypesRepository;
import co.za.sbk.repository.UserRepository;
import co.za.sbk.service.IncidentService;
import co.za.sbk.service.dto.IncidentDTO;
import co.za.sbk.service.mapper.IncidentTypesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static co.za.sbk.TestUtil.sameInstant;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(IncidentResource.class)
class IncidentResourceTest {
    
    private static final String BASE_API = "/api/incidents";
    
    @Autowired
    MockMvc mvc;
    
    @MockBean
    IncidentService service;
    
    @MockBean
    IncidentTypesRepository incidentTypesRepository;  
    
    @MockBean
    UserRepository userRepository;
    
    @MockBean
    IncidentTypesMapper mapper;
    
    IncidentResource resource;

    Incident incident;
    Officer officer;
    Suspect suspect;
    User user;
    IncidentTypes incidentTypes;
    IncidentDTO incidentDTO;
    IncidentDTO savedDTO; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        resource = new IncidentResource(service);

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
        incidentDTO.setLocation("JohannesBurg Maboneng");
        incidentDTO.setDescription("3423");
        incidentDTO.setIncidentTypesId(incidentTypes.getId());
        incidentDTO.setIncidentTypesType(incidentTypes.getType());
        incidentDTO.setUserId(user.getId());
        incidentDTO.setStartDate(ZonedDateTime.now());
        incidentDTO.setIncidentStatus(IncidentStatus.OPEN);

        savedDTO = new IncidentDTO();
        savedDTO.setId(1L);
        savedDTO.setLocation("JohannesBurg");
        savedDTO.setDescription("3423");
        savedDTO.setIncidentTypesId(incidentTypes.getId());
        savedDTO.setIncidentTypesType(incidentTypes.getType());
        savedDTO.setUserId(user.getId());
        savedDTO.setStartDate(ZonedDateTime.now());
        savedDTO.setIncidentStatus(IncidentStatus.OPEN);

        incident = new Incident();
//        incident.setId(1L);
        incident.setLocation("JohannesBurg");
        incident.setDescription("3423");
        incident.setIncidentTypes(incidentTypes);
        incident.user(user);
        incident.setStartDate(ZonedDateTime.now());
        incident.setIncidentStatus(IncidentStatus.OPEN);

    }

    @Test
    void testCreateIncident() throws Exception{
        
        given(service.save(any())).willReturn(savedDTO);
        
        mvc.perform(post(BASE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isCreated());
                
    }

    @Test
    void testUpdateIncident() throws Exception {

        mvc.perform(put("/api/incidents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(savedDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllIncidents() throws Exception{

        List<IncidentDTO> list = new ArrayList<>();
        list.add(savedDTO);
        Page<IncidentDTO> pageObj = new PageImpl<>(list);

        given(service.findAll(ArgumentMatchers.isA(Pageable.class))).willReturn(pageObj);

        mvc.perform(get("/api/incidents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(savedDTO.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(savedDTO.getStartDate()))))
                .andExpect(jsonPath("$.[*].incidentStatus").value(hasItem(IncidentStatus.OPEN.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(savedDTO.getLocation())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(savedDTO.getDescription())));
    }

    @Test
    void testGetIncident() throws Exception {
        
        given(service.findOne(any())).willReturn(Optional.of(savedDTO));
        
        mvc.perform(get(BASE_API + "/{id}", savedDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(savedDTO.getId().intValue()))
                .andExpect(jsonPath("$.startDate").value(sameInstant(savedDTO.getStartDate())))
                .andExpect(jsonPath("$.incidentStatus").value(IncidentStatus.OPEN.toString()))
                .andExpect(jsonPath("$.location").value(savedDTO.getLocation()))
                .andExpect(jsonPath("$.description").value(savedDTO.getDescription()));
    }

    @Test
    void testDeleteIncident() throws Exception {
        mvc.perform(delete("/api/incidents/{id}", savedDTO.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

}