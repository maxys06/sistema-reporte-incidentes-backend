package com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadRequestDTO
{
    @NotEmpty
    private String nombre;
    @NotEmpty
    private List<Long> idProblemas;
}
