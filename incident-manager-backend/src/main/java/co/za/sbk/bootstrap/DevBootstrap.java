package co.za.sbk.bootstrap;

import co.za.sbk.domain.IncidentTypes;
import co.za.sbk.domain.User;
import co.za.sbk.domain.enumeration.Gender;
import co.za.sbk.repository.IncidentTypesRepository;
import co.za.sbk.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final IncidentTypesRepository incidentTypesRepository;
    private final UserRepository userRepository;

    public DevBootstrap(IncidentTypesRepository incidentTypesRepository, UserRepository userRepository) {
        this.incidentTypesRepository = incidentTypesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }
    
    private void initData() {
        IncidentTypes incidentTypes = new IncidentTypes("Theft");
        IncidentTypes incidentTypes2 = new IncidentTypes("Assault");
        incidentTypesRepository.save(incidentTypes);
        incidentTypesRepository.save(incidentTypes2);
        
        User user = new User("1234", "Thato", "Seboko", Gender.MALE);
        userRepository.save(user);
    }
}
