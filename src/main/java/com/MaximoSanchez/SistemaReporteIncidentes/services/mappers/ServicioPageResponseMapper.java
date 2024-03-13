package com.MaximoSanchez.SistemaReporteIncidentes.services.mappers;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioPageResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Servicio;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ServicioPageResponseMapper implements Function<Page<Servicio>, ServicioPageResponseDTO> {

    @Autowired
    private ServicioResponseMapper mapper;

    @Override
    public ServicioPageResponseDTO apply(Page<Servicio> servicios) {

        List<ServicioResponseDTO> serviciosDTO = servicios.getContent().stream().map(mapper).toList();


        return new ServicioPageResponseDTO(servicios.getTotalPages(), servicios.getTotalElements(), servicios.getNumber(), serviciosDTO);
    }
}
