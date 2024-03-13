package com.MaximoSanchez.SistemaReporteIncidentes.controllers;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TecnicoPageResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.TecnicoResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.services.TecnicoService;
import com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs.TecnicoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController
{
    TecnicoService service;
    @Autowired
    public TecnicoController(TecnicoService service)
    {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<TecnicoResponseDTO>> getAllTecnicos()
    {
        return new ResponseEntity<>(service.getAllTecnicos(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<TecnicoResponseDTO> getTecnicoById(@PathVariable Long id)
    {
        return new ResponseEntity<>(service.getTecnicoById(id), HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<TecnicoResponseDTO> addTecnico(@RequestBody TecnicoRequestDTO dto)
    {
        return new ResponseEntity<>(service.addTecnico(dto), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<TecnicoResponseDTO> updateTecnico(@PathVariable Long id, @RequestBody TecnicoRequestDTO dto)
    {
        return new ResponseEntity<>(service.updateTecnico(id, dto), HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<TecnicoResponseDTO> deleteTecnico(@PathVariable Long id)
    {
        return new ResponseEntity<>(service.deleteTecnico(id), HttpStatus.OK);
    }

    @GetMapping("/filtros")
    public ResponseEntity<TecnicoPageResponseDTO>
        getTecnicosFiltrado(@RequestParam(name = "nombre", required = false) String nombre,
                            @RequestParam(name = "apellido", required = false) String apellido,
                            @RequestParam(name = "especialidades", required = false) List<Long> idEspecialidades,
                            @RequestParam(name = "page", required = false) Integer page)
    {
        Integer updatedPage;
        if (page == null || page < 1) {
            updatedPage = 0;
        }
        else {
            updatedPage = page - 1;
        }

        return new ResponseEntity<TecnicoPageResponseDTO>(service.getTecnicosFiltrado(nombre, apellido, idEspecialidades, updatedPage), HttpStatus.OK);
    }
}
