package ua.ithillel.javapro.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.ithillel.javapro.domain.dto.RoleDTO;
import ua.ithillel.javapro.domain.model.Role;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleMapper {
    RoleDTO userToUserDTO(Role role);
    Role userDTOtoUser(RoleDTO roleDTO);
}
