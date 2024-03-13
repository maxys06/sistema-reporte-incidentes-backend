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
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_cliente")
    private TipoCliente tipoCliente;

    @Column
    private String nombre;
    @Column
    private String identificacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Set<Contacto> contactos = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "servicio_x_cliente",
            joinColumns = @JoinColumn(name = "id_cliente"),
            inverseJoinColumns = @JoinColumn(name = "id_servicio")
    )
    private List<Servicio> servicios = new ArrayList<>();

    public Cliente(TipoCliente tipoCliente, String nombre, String identificacion, Set<Contacto> contactos, List<Servicio> servicios)
    {
        this.tipoCliente = tipoCliente;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.contactos = contactos;
        this.servicios = servicios;
    }

    public void update(TipoCliente tipoCliente, String nombre, String identificacion, Set<Contacto> contactos, List<Servicio> servicios)
    {
        setTipoCliente(tipoCliente);
        this.nombre = nombre;
        setContactos(contactos);
        this.identificacion = identificacion;
        setServicios(servicios);
    }

    public void setContactos(Set<Contacto> contactos) {
        this.contactos.clear();
        this.contactos.addAll(contactos);
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios.clear();
        this.servicios.addAll(servicios);
    }

    public boolean addContacto(Contacto contacto)
    {
        return getContactos().add(contacto);
    }

    public boolean esTuServicio(Servicio servicio) {
        return getServicios().contains(servicio);
    }
}
