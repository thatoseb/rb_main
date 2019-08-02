package co.za.sbk.service.mapper;

import co.za.sbk.domain.*;
import co.za.sbk.service.dto.IncidentTypesDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link IncidentTypes} and its DTO {@link IncidentTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncidentTypesMapper extends EntityMapper<IncidentTypesDTO, IncidentTypes> {



    default IncidentTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setId(id);
        return incidentTypes;
    }
}
