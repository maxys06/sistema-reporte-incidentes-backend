package com.MaximoSanchez.SistemaReporteIncidentes.services;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TecnicoResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.InvalidRequestParameterException;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.ResourceNotFoundException;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Tecnico;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.ClienteRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.ProblemaRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Cliente;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Problema;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.ServicioRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.TecnicoRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.ServicioResponseMapper;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.TecnicoResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperadorService
{
    ClienteRepository clienteRepository;
    ServicioRepository servicioRepository;
    ServicioResponseMapper servicioResponseMapper;
    TecnicoRepository tecnicoRepository;
    TecnicoResponseMapper tecnicoResponseMapper;
    ProblemaRepository problemaRepository;

    @Autowired
    public OperadorService(ClienteRepository clienteRepository, ServicioRepository servicioRepository, ServicioResponseMapper servicioResponseMapper, TecnicoRepository tecnicoRepository, TecnicoResponseMapper tecnicoResponseMapper, ProblemaRepository problemaRepository)
    {
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.servicioResponseMapper = servicioResponseMapper;
        this.tecnicoRepository = tecnicoRepository;
        this.tecnicoResponseMapper = tecnicoResponseMapper;
        this.problemaRepository = problemaRepository;
    }

    public List<ServicioResponseDTO> getServiciosByIdentificacionCliente(String identificacion)
    {
        Cliente cliente = clienteRepository.findByIdentificacion(identificacion)
                .orElseThrow(()-> new ResourceNotFoundException("CLIENTE IDENTIFICACION: " + identificacion));

        return cliente.getServicios().stream()
                .map(servicioResponseMapper)
                .toList();
    }

    public List<TecnicoResponseDTO> buscarTecnico(List<Long> idProblemas)
    {
        if (idProblemas == null || idProblemas.isEmpty())
            throw new InvalidRequestParameterException("Se necesita al menos una ID.");

        List<Problema> problemas = new ArrayList<>();
        idProblemas.forEach(
                id-> problemas.add( problemaRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("PROBLEMA ID: " + id)))
        );

        return tecnicoRepository.findAll().stream()
                .filter( t-> t.puedeResolverProblemas(problemas) )
                .map(tecnicoResponseMapper)
                .toList();
    }

    // Hace lo mismo que el método de arriba, pero con Specifications
    public List<TecnicoResponseDTO> buscarTecnicoConSpecs(List<Long> idProblemas)
    {
        Specification<Tecnico> spec = Specification.where(null);
        if ( idProblemas != null && !idProblemas.isEmpty() )
            spec = spec.and(TecnicoRepository.Specs.puedeResolverProblemas(idProblemas));
        else throw new InvalidRequestParameterException("Se necesita al menos una ID.");

        return tecnicoRepository.findAll(spec).stream()
                .map(tecnicoResponseMapper)
                .toList();
    }
}
