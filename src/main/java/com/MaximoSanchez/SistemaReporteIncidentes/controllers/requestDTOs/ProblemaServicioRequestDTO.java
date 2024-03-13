package com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemaServicioRequestDTO
{
    @NotEmpty
    private String tipo;

    private String descripcion;

    @NotEmpty
    private Long tiempoMaximoResolucion;

    @NotEmpty
    private boolean complejo;
}
