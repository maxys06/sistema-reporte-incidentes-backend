package com.MaximoSanchez.SistemaReporteIncidentes.services.mappers;

import com.MaximoSanchez.SistemaReporteIncidentes.controllers.responseDTOs.ProblemaResponseDTO;
import com.MaximoSanchez.SistemaReporteIncidentes.models.Problema;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProblemaResponseMapper implements Function<Problema, ProblemaResponseDTO>
{
    @Override
    public ProblemaResponseDTO apply(Problema problema)
    {
        return new ProblemaResponseDTO(
                problema.getIdProblema(),
                problema.getTipo(),
                problema.getDescripcion(),
                //devuelve los minutos a tiempo en formato HH:MM:SS
                formatTime(problema.getTiempoMaximoResolucion()),
                problema.isComplejo());
    }

    private String formatTime(long minutos)
    {
        long horas = minutos / 60;
        long restoMinutos = minutos % 60;

        return String.format("%02d:%02d:00", horas, restoMinutos);
    }
}
