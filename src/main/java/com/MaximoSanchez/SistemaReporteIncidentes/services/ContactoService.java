package com.MaximoSanchez.SistemaReporteIncidentes.services;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TipoContactoResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.ResourceNotFoundException;
import com.MaximoSanchez.SistemaReporteIncidentes.models.TipoContacto;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.TipoContactoRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.TipoContactoResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService
{
    TipoContactoRepository tipoContactoRepository;

    TipoContactoResponseMapper tipoContactoResponseMapper;

    @Autowired
    public ContactoService(TipoContactoRepository tipoContactoRepository, TipoContactoResponseMapper tipoContactoResponseMapper)
    {
        this.tipoContactoRepository = tipoContactoRepository;
        this.tipoContactoResponseMapper = tipoContactoResponseMapper;
    }


    public List<TipoContactoResponseDTO> getAllTipoContactos()
    {


        return tipoContactoRepository.findAll().stream()
                .map(tipoContactoResponseMapper)
                .toList();
    }

    public TipoContactoResponseDTO getTipoContactoById(Long id)
    {
        TipoContacto tipoContacto = tipoContactoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("TIPO CONTACTO ID: " + id));

        return tipoContactoResponseMapper.apply(tipoContacto);
    }

    public TipoContactoResponseDTO getTipoContactoByTipo(String tipo)
    {
        TipoContacto tipoContacto = tipoContactoRepository.findByTipo(tipo)
                .orElseThrow(()-> new ResourceNotFoundException("TIPO CONTACTO: " + tipo));

        return tipoContactoResponseMapper.apply(tipoContacto);
    }
}
