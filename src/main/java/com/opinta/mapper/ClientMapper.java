package com.opinta.mapper;

import com.opinta.dto.ClientDto;
import com.opinta.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ClientMapper extends BaseMapper<ClientDto, Client> {
    
    @Override
    @Mappings({
            @Mapping(source = "address.id", target = "addressId"),
            @Mapping(source = "counterparty.id", target = "counterpartyId")})
    ClientDto toDto(Client client);
    
    @Override
    @Mappings({
            @Mapping(source = "addressId", target = "address.id"),
            @Mapping(source = "counterpartyId", target = "counterparty.id")})
    Client toEntity(ClientDto clientDto);
}
