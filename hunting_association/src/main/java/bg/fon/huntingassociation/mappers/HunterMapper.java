package bg.fon.huntingassociation.mappers;

import bg.fon.huntingassociation.domain.Hunter;
import bg.fon.huntingassociation.domain.dtos.HunterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring", uses = {TeamMapper.class})
public interface HunterMapper {
    HunterMapper mapper = Mappers.getMapper(HunterMapper.class);

    HunterDto entityToDto(Hunter hunter);

    Hunter dtoToEntity(HunterDto hunterDto);
}
