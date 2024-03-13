package com.MaximoSanchez.SistemaReporteIncidentes.models;

import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.InvalidRequestParameterException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incidentes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incidente implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidente")
    private Long idIncidente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", nullable = false)
    private Tecnico tecnico;

    // LISTA DE "DetalleProblema" !!!
    @OneToMany(mappedBy = "incidente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleProblema> problemas = new ArrayList<>();

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    @Column
    private boolean resuelto;
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    @Column
    private String mensaje;

    public Incidente(Cliente cliente, Servicio servicio, Tecnico tecnico, LocalDateTime fechaInicio)
    {
        this.cliente = cliente;
        this.servicio = servicio;
        this.tecnico = tecnico;
        this.fechaInicio = fechaInicio;
        this.resuelto = false;
        this.problemas = new ArrayList<>();
    }

    public void setProblemas(List<DetalleProblema> problemas)
    {
        getProblemas().clear();
        this.problemas.addAll(problemas);
    }

    public void crearDetalleProblema(Problema p, List<TiempoEstimadoResolucion> estimaciones)
    {
        getProblemas().add(new DetalleProblema(this, p, estimaciones));
    }

    public void resolver(String mensaje)
    {
        if (resuelto)
            throw new InvalidRequestParameterException("El incidente con ID: " + idIncidente
                + " ya ha sido resuelto en fecha: " + fechaResolucion);

        resuelto = true;
        fechaResolucion = LocalDateTime.now();
        this.mensaje = mensaje;
    }
}
