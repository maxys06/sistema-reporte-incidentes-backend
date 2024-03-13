package com.MaximoSanchez.SistemaReporteIncidentes.services.mappers;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ProblemaResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ServicioResponseMapper implements Function<Servicio, ServicioResponseDTO>
{
    private final ProblemaResponseMapper problemaResponseMapper;

    @Autowired
    public ServicioResponseMapper(ProblemaResponseMapper mapper)
    {
        this.problemaResponseMapper = mapper;
    }

    @Override
    public ServicioResponseDTO apply(Servicio servicio)
    {
        List<ProblemaResponseDTO> lista =
                servicio.getProblemas().stream()
                        .map(problemaResponseMapper)
                        .toList();

        return new ServicioResponseDTO(servicio.getIdServicio(), servicio.getNombre(), lista);
    }
}
