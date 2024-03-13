package com.MaximoSanchez.SistemaReporteIncidentes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio implements Serializable
{
    @Id
    @Column(name = "id_servicio", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private List<Problema> problemas = new ArrayList<>();

    public Servicio(String nombre)
    {
        this.nombre = nombre;
        this.problemas = new ArrayList<>();
    }

    public void update(String nombre)
    {
        this.nombre = nombre;
    }

    public boolean esTuProblema(Problema p)
    {
        return getProblemas().contains(p);
    }

    public void crearProblema(String tipo, String descripcion, long tiempoMaximoResolucion, boolean complejo)
    {
        getProblemas().add(new Problema(tipo, descripcion, tiempoMaximoResolucion, complejo));
    }
}
