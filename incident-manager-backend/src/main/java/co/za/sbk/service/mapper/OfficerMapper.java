package co.za.sbk.service.mapper;

import co.za.sbk.domain.Officer;
import co.za.sbk.domain.*;
import co.za.sbk.service.dto.OfficerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Officer} and its DTO {@link OfficerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OfficerMapper extends EntityMapper<OfficerDTO, Officer> {


//    @Mapping(target = "assignedIncidents", ignore = true)
//    @Mapping(target = "removeAssignedIncidents", ignore = true)
    Officer toEntity(OfficerDTO officerDTO);

    default Officer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Officer officer = new Officer();
        officer.setId(id);
        return officer;
    }
}
