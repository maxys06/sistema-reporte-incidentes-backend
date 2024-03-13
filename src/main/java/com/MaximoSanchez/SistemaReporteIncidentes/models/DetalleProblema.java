package com.MaximoSanchez.SistemaReporteIncidentes.models;

import com.MaximoSanchez.SistemaReporteIncidentes.util.DetalleProblemaEmbeddedId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "detalle_problema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleProblema implements Serializable
{
    @EmbeddedId
    private DetalleProblemaEmbeddedId detalleProblemaEmbeddedId = new DetalleProblemaEmbeddedId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idIncidente")
    @JoinColumn(name = "id_incidente", nullable = false)
    private Incidente incidente;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProblema")
    @JoinColumn(name = "id_problema", nullable = false)
    private Problema problema;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_incidente", referencedColumnName = "id_incidente", nullable = false),
            @JoinColumn(name = "id_problema", referencedColumnName = "id_problema", nullable = false)
    })
    private List<TiempoEstimadoResolucion> estimaciones = new ArrayList<>();


    public DetalleProblema(Incidente incidente, Problema problema, List<TiempoEstimadoResolucion> estimaciones) {
        this.incidente = incidente;
        this.problema = problema;
        this.estimaciones = estimaciones;
        this.detalleProblemaEmbeddedId = new DetalleProblemaEmbeddedId(incidente.getIdIncidente(), problema.getIdProblema());

    }
}
