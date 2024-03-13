package com.MaximoSanchez.SistemaReporteIncidentes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tecnicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tecnico implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Long idTecnico;

    @Column
    private String nombre;
    @Column
    private String apellido;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_tecnico")
    private Set<Contacto> contactos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tecnico_x_especialidad",
            joinColumns = @JoinColumn(name = "id_tecnico"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidad")
    )
    private List<Especialidad> especialidades = new ArrayList<>();

    public Tecnico(String nombre, String apellido, Set<Contacto> contactos, List<Especialidad> especialidades)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contactos = contactos;
        this.especialidades = especialidades;
    }

    public void update(String nombre, String apellido, Set<Contacto> contactos, List<Especialidad> especialidades)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        setContactos(contactos);
        setEspecialidades(especialidades);
    }

    public void setContactos(Set<Contacto> contactos) {
        this.contactos.clear();
        this.contactos.addAll(contactos);
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades.clear();
        this.especialidades.addAll(especialidades);
    }

    public boolean addContacto(Contacto contacto)
    {
        return getContactos().add(contacto);
    }


    //Verifica si puede resolver un problema particular
    public boolean puedeResolverProblema(Problema p)
    {
        return getEspecialidades().stream().anyMatch((e) -> e.esTuProblema(p));
    }

    // Verifica si puede resolver un conjunto de problemas
    public boolean puedeResolverProblemas(List<Problema> problemas)
    {
        return problemas.stream().allMatch(this::puedeResolverProblema);
    }
}
