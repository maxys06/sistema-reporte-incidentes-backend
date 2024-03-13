package com.MaximoSanchez.SistemaReporteIncidentes.services.mappers;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TecnicoPageResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TecnicoResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Tecnico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class TecnicoPageResponseMapper implements Function<Page<Tecnico>, TecnicoPageResponseDTO> {

    @Autowired
    private TecnicoResponseMapper tecnicoMapper;


    @Override
    public TecnicoPageResponseDTO apply(Page<Tecnico> tecnicos) {
        List<TecnicoResponseDTO> tecnicosDTO = tecnicos.getContent().stream().map(tecnicoMapper).toList();
        return new TecnicoPageResponseDTO(tecnicos.getTotalPages(), tecnicos.getTotalElements(), tecnicos.getNumber(), tecnicosDTO);
    }
}
