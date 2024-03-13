package com.MaximoSanchez.SistemaReporteIncidentes.services;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ContactoRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TecnicoPageResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TecnicoResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.InvalidRequestParameterException;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.ResourceNotFoundException;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Contacto;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Especialidad;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Tecnico;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.EspecialidadRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.TecnicoRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.repositories.TipoContactoRepository;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.TecnicoRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.models.TipoContacto;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.TecnicoPageResponseMapper;
import com.MaximoSanchez.SistemaReporteIncidentes.services.mappers.TecnicoResponseMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TecnicoService
{
    TecnicoRepository tecnicoRepository;
    EspecialidadRepository especialidadRepository;
    TecnicoResponseMapper tecnicoResponseMapper;
    TecnicoPageResponseMapper tecnicoPageResponseMapper;
    TipoContactoRepository tipoContactoRepository;

    @Autowired
    public TecnicoService(TecnicoRepository tecnicoRepository, EspecialidadRepository especialidadRepository, TecnicoResponseMapper tecnicoResponseMapper, TipoContactoRepository tipoContactoRepository, TecnicoPageResponseMapper tecnicoPageResponseMapper)
    {
        this.tecnicoRepository = tecnicoRepository;
        this.especialidadRepository = especialidadRepository;
        this.tipoContactoRepository = tipoContactoRepository;

        this.tecnicoResponseMapper = tecnicoResponseMapper;
        this.tecnicoPageResponseMapper = tecnicoPageResponseMapper;
    }

    public List<TecnicoResponseDTO> getAllTecnicos()
    {

        return tecnicoRepository.findAll().stream()
                .map(tecnicoResponseMapper)
                .toList();
    }

    public TecnicoResponseDTO getTecnicoById(Long id)
    {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("TECNICO ID: " + id));
        return tecnicoResponseMapper.apply(tecnico);
    }

    @Transactional
    public TecnicoResponseDTO addTecnico(TecnicoRequestDTO dto)
    {

        List<Especialidad> especialidades = dto.getIdEspecialidades().stream()
                .map(id -> especialidadRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("ESPECIALIDAD ID: " + id)))
                .toList();

        Set<Contacto> contactos = dto.getContactos().stream()
                .map(this::crearContacto)
                .collect(Collectors.toSet());

        Tecnico tecnico = new Tecnico(dto.getNombre().toUpperCase(),
                dto.getApellido().toUpperCase(),
                contactos,
                especialidades);

        tecnicoRepository.save(tecnico);
        return tecnicoResponseMapper.apply(tecnico);
    }

    @Transactional
    public TecnicoResponseDTO updateTecnico(Long id_tecnico, TecnicoRequestDTO dto)
    {
        Tecnico tecnico = tecnicoRepository.findById(id_tecnico)
                .orElseThrow(()-> new ResourceNotFoundException("TECNICO ID: " + id_tecnico));

        List<Especialidad> especialidades = dto.getIdEspecialidades().stream()
                .map(id -> especialidadRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("ESPECIALIDAD ID: " + id)))
                .toList();

        Set<Contacto> contactos = dto.getContactos().stream()
                .map(this::crearContacto)
                .collect(Collectors.toSet());

        tecnico.update(dto.getNombre().toUpperCase(),
                dto.getApellido().toUpperCase(),
                contactos,
                especialidades);

        System.out.println(tecnico);

        tecnicoRepository.save(tecnico);
        return tecnicoResponseMapper.apply(tecnico);
    }

    @Transactional
    public TecnicoResponseDTO deleteTecnico(Long id)
    {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("TECNICO ID: " + id));

        tecnicoRepository.delete(tecnico);
        return tecnicoResponseMapper.apply(tecnico);
    }

    public TecnicoPageResponseDTO getTecnicosFiltrado(String nombre, String apellido, List<Long> idEspecialidades, Integer page)
    {
        Pageable pageRequest = PageRequest.of(page, 10);

        Specification<Tecnico> spec = Specification.where(null);
        if (nombre != null && !nombre.isEmpty())
            spec = spec.and(TecnicoRepository.Specs.nombreLike(nombre));
        if (apellido != null && !apellido.isEmpty())
            spec = spec.and(TecnicoRepository.Specs.apellidoLike(apellido));
        if (idEspecialidades != null && !idEspecialidades.isEmpty())
        {
            idEspecialidades = idEspecialidades.stream().filter(Objects::nonNull).toList();
            spec = spec.and(TecnicoRepository.Specs.hasEspecialidades(idEspecialidades));
        }
        Page<Tecnico> tecnicos = tecnicoRepository.findAll(spec, pageRequest);
        return tecnicoPageResponseMapper.apply(tecnicos);
    }

    private Contacto crearContacto(ContactoRequestDTO dto)
    {
        TipoContacto tipoContacto = tipoContactoRepository.findByTipo(dto.getTipoContacto().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("TIPO CONTACTO: " + dto.getTipoContacto()));

        // Si el contacto no cumple con el regex del TipoContacto, salta exception!
        if ( ! tipoContacto.verificarContacto(dto.getContacto()))
            throw new InvalidRequestParameterException(tipoContacto.getMensajeError());

        return new Contacto(tipoContacto, dto.getContacto());
    }
}
