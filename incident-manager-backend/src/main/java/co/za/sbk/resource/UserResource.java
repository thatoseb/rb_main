package co.za.sbk.resource;

import co.za.sbk.resource.errors.BadRequestAlertException;
import co.za.sbk.resource.utils.HeaderUtil;
import co.za.sbk.service.UserService;
import co.za.sbk.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);
    
    private static final String ENTITY_NAME = "User";

    @Value("${app.name}")
    private String applicationName;
    
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save user: {}", userDTO);
        if (userDTO.getId() != null){
            throw new BadRequestAlertException("A new user cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        UserDTO result = userService.save(userDTO);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
}
