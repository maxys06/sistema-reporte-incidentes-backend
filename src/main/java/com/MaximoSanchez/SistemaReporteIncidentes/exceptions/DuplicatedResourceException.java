package com.MaximoSanchez.SistemaReporteIncidentes.exceptions;

public class DuplicatedResourceException extends RuntimeException
{
    public DuplicatedResourceException() {
        super();
    }

    public DuplicatedResourceException(String msg) {
        super(msg);
    }
}
