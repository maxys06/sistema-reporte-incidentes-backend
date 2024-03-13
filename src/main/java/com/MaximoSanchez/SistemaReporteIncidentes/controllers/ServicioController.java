package com.MaximoSanchez.SistemaReporteIncidentes.controllers;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ServicioUpdateRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.InvalidRequestParameterException;
import com.MaximoSanchez.SistemaReporteIncidentes.services.ServicioService;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.ServicioRequestDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioPageResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ServicioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController
{
    private final ServicioService service;

    @Autowired
    public ServicioController(ServicioService service)
    {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<ServicioResponseDTO>> getAllServicios()
    {
        return new ResponseEntity<List<ServicioResponseDTO>>(service.getAllServicios(), HttpStatus.OK);
    }

    @GetMapping("/filtros")
    public ResponseEntity<ServicioPageResponseDTO> getAllServiciosFiltered(
            @RequestParam(name="nombre", required = false) String nombreServicio,
            @RequestParam(name="page", required = false, defaultValue = "1") Integer page)
    {

        if(page < 1) {
            throw new InvalidRequestParameterException("The page number cannot be less than one");
        }

        return new ResponseEntity<ServicioPageResponseDTO>(service.getAllServiciosFiltered(nombreServicio, page-1), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> getServicioById(@PathVariable Long id)
    {
        return new ResponseEntity<ServicioResponseDTO>(service.getServicioById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ServicioResponseDTO> addServicio(@RequestBody ServicioRequestDTO dto)
    {
        return new ResponseEntity<ServicioResponseDTO>(service.addServicio(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO>
        updateServicio(@PathVariable Long id, @RequestBody ServicioUpdateRequestDTO dto)
    {
        return new ResponseEntity<ServicioResponseDTO>(service.updateServicio(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> deleteServicio(@PathVariable Long id)
    {
        return new ResponseEntity<ServicioResponseDTO>(service.deleteServicio(id), HttpStatus.OK);
    }
}
