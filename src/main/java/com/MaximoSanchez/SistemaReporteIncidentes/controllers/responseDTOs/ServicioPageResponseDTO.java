package com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ServicioPageResponseDTO {

    private Integer totalPages;
    private Long totalElements;
    private Integer selectedPage;
    private List<ServicioResponseDTO> servicios;


}
