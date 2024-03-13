package com.MaximoSanchez.SistemaReporteIncidentes.services;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ProblemaResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.ResourceNotFoundException;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Problema;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Servicio;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.ProblemaRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.ProblemaResponseMapper;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ProblemaRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ProblemaUpdateRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemaService
{
    private ProblemaRepository problemaRepository;
    private ServicioRepository servicioRepository;
    private ProblemaResponseMapper mapper;

    @Autowired
    public ProblemaService(ProblemaRepository problemaRepository, ServicioRepository servicioRepository, ProblemaResponseMapper mapper)
    {
        this.problemaRepository = problemaRepository;
        this.servicioRepository = servicioRepository;
        this.mapper = mapper;
    }

    public List<ProblemaResponseDTO> getAllProblemas()
    {
        return problemaRepository.findAll().stream()
                .map(mapper)
                .toList();
    }

    public ProblemaResponseDTO getProblemaById(Long id)
    {
        Problema p = problemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PROBLEMA ID: " + id));

        return mapper.apply(p);
    }


    public ProblemaResponseDTO addProblema(ProblemaRequestDTO dto)
    {
        Servicio s = servicioRepository.findById(dto.getIdServicio())
                .orElseThrow(() -> new ResourceNotFoundException("SERVICIO ID: " + dto.getIdServicio()));

        Problema p = new Problema();
        p.setTipo(dto.getTipo().toUpperCase());
        p.setDescripcion(dto.getDescripcion().toUpperCase());
        p.setTiempoMaximoResolucion(dto.getTiempoMaximoResolucion());
        p.setComplejo(dto.isComplejo());

        s.getProblemas().add(p);
        servicioRepository.save(s);
        return mapper.apply(p);
    }

    public ProblemaResponseDTO updateProblema(Long id_problema, ProblemaUpdateRequestDTO dto)
    {
        Problema p = problemaRepository.findById(id_problema)
                .orElseThrow(() -> new ResourceNotFoundException("PROBLEMA ID: " + id_problema));

        p.setTipo(dto.getTipo().toUpperCase());
        p.setDescripcion(dto.getDescripcion().toUpperCase());
        p.setTiempoMaximoResolucion(dto.getTiempoMaximoResolucion());
        p.setComplejo(dto.isComplejo());

        problemaRepository.save(p);
        return mapper.apply(p);
    }

    public ProblemaResponseDTO deleteProblema(Long id)
    {
        Problema p = problemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PROBLEMA ID: " + id));

        problemaRepository.delete(p);
        return mapper.apply(p);
    }
}
