package com.MaximoSanchez.SistemaReporteIncidentes.services.mappers;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ClienteResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Servicio;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClienteResponseMapper implements Function<Cliente, ClienteResponseDTO>
{
    private ContactoResponseMapper contactoResponseMapper;

    @Autowired
    public ClienteResponseMapper(ContactoResponseMapper contactoResponseMapper)
    {
        this.contactoResponseMapper = contactoResponseMapper;
    }

    @Override
    public ClienteResponseDTO apply(Cliente cliente)
    {
        return new ClienteResponseDTO(cliente.getIdCliente(),
                cliente.getTipoCliente().getTipo(),
                cliente.getNombre(),
                cliente.getIdentificacion(),
                cliente.getContactos().stream().map(contactoResponseMapper).toList(),
                cliente.getServicios().stream().mapToLong(Servicio::getIdServicio).toArray());
    }
}
