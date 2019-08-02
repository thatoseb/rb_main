package co.za.sbk.bootstrap;

import co.za.sbk.domain.IncidentTypes;
import co.za.sbk.repository.IncidentTypesRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final IncidentTypesRepository incidentTypesRepository;

    public DevBootstrap(IncidentTypesRepository incidentTypesRepository) {
        this.incidentTypesRepository = incidentTypesRepository;
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
    }
}
