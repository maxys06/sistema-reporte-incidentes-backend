package com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleProblemaResponseDTO
{
    private ProblemaResponseDTO problema;
    private long[] estimaciones;
}
