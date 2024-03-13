package com.MaximoSanchez.SistemaReporteIncidentes.exceptions;

public class InvalidRequestParameterException extends RuntimeException
{
    public InvalidRequestParameterException(){super();}
    public InvalidRequestParameterException(String msg){super(msg);}
}
