package com.MaximoSanchez.SistemaReporteIncidentes.services;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ProblemaServicioRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ServicioRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ServicioUpdateRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioPageResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.DuplicatedResourceException;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.ResourceNotFoundException;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Servicio;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.ServicioRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.ServicioPageResponseMapper;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.ServicioResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService
{
    private final ServicioRepository repository;
    private final ServicioResponseMapper mapper;

    private final ServicioPageResponseMapper pageMapper;

    @Autowired
    public ServicioService(ServicioRepository repository, ServicioResponseMapper mapper, ServicioPageResponseMapper pageMapper)
    {
        this.repository = repository;
        this.mapper = mapper;
        this.pageMapper = pageMapper;
    }

    public List<ServicioResponseDTO> getAllServicios()
    {
        return repository.findAll().stream()
                .map(mapper)
                .toList();
    }

    public ServicioPageResponseDTO getAllServiciosFiltered(String nombreServicio, Integer page) {

        Pageable pr = PageRequest.of(page, 2);

        Specification<Servicio> spec = Specification.where(null);

        if (nombreServicio != null && !nombreServicio.isEmpty()) {
            spec = spec.and(ServicioRepository.Specs.nombreLike(nombreServicio));
        }

        Page<Servicio> servicios = repository.findAll(spec, pr);
        return pageMapper.apply(servicios);

    }

    public ServicioResponseDTO getServicioById(Long id) throws ResourceNotFoundException
    {
        Servicio servicio = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio ID: " + id));
        return mapper.apply(servicio);
    }

    public ServicioResponseDTO addServicio(ServicioRequestDTO servicioRequestDTO)
    {
        String nombre = servicioRequestDTO.getNombre().toUpperCase();
        if (repository.findByNombre(nombre).isPresent())
            throw new DuplicatedResourceException("SERVICIO " + nombre );

        Servicio servicio = new Servicio(nombre);
        for (ProblemaServicioRequestDTO dto : servicioRequestDTO.getProblemas())
        {
            servicio.crearProblema(dto.getTipo(),
                    dto.getDescripcion(),
                    dto.getTiempoMaximoResolucion(),
                    dto.isComplejo());
        }

        repository.save(servicio);
        return mapper.apply(servicio);
    }

    public ServicioResponseDTO updateServicio(Long id, ServicioUpdateRequestDTO dto)
    {
        Servicio servicio = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio ID: " + id));

        String nombre = dto.getNombre().toUpperCase();
        /*
         Si ya se encuentra un servicio con el nombre provisto por el dto,
         y el nombre provisto por el dto NO COINCIDE con el nombre del servicio a actualizar...
        */
        if (repository.findByNombre(nombre).isPresent() && ! servicio.getNombre().equals(nombre))
            throw new DuplicatedResourceException("SERVICIO \"" + nombre + "\"");

        servicio.update(nombre);
        repository.save(servicio);
        return mapper.apply(servicio);
    }

    public ServicioResponseDTO deleteServicio(Long id)
    {
        Servicio servicio = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio ID: " + id));

        repository.delete(servicio);
        return mapper.apply(servicio);
    }
}
