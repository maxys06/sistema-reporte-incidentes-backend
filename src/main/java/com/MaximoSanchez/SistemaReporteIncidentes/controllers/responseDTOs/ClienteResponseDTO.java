package com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO
{
    long id;
    String tipoCliente;
    String nombre;
    String identificacion;
    List<ContactoResponseDTO> contactos;
    long[] idServicios;
}
