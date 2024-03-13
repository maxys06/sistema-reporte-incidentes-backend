package com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TecnicoPageResponseDTO {

    private Integer totalPages;
    private Long totalElements;
    private Integer selectedPage;

    private List<TecnicoResponseDTO> tecnicos;

}
