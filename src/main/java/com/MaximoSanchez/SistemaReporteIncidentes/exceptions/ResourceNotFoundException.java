package com.MaximoSanchez.SistemaReporteIncidentes.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
