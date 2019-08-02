package co.za.sbk.service.mapper;

import co.za.sbk.domain.*;
import co.za.sbk.service.dto.SuspectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Suspect} and its DTO {@link SuspectDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SuspectMapper extends EntityMapper<SuspectDTO, Suspect> {


//    @Mapping(target = "linkedIncidents", ignore = true)
//    @Mapping(target = "removeLinkedIncidents", ignore = true)
    Suspect toEntity(SuspectDTO suspectDTO);

    default Suspect fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suspect suspect = new Suspect();
        suspect.setId(id);
        return suspect;
    }
}
