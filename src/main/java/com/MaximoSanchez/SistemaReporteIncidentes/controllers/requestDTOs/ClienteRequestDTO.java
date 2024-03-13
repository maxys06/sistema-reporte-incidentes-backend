package com.MaximoSanchez.SistemaReporteIncidentes.controllers.requestDTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO
{
    @NotEmpty
    String tipoCliente;
    @NotEmpty
    String nombre;
    @NotEmpty
    String identificacion;
    @NotEmpty
    List<ContactoRequestDTO> contactos;

    List<Long> idServicios;
}
