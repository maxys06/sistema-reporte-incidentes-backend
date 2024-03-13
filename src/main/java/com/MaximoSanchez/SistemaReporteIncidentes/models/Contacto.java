package com.MaximoSanchez.SistemaReporteIncidentes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "contactos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contacto implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    @EqualsAndHashCode.Exclude
    protected Long idContacto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_contacto")
    private TipoContacto tipoContacto;

    @Column
    private String contacto;

    public Contacto(TipoContacto tipoContacto, String contacto)
    {
        this.tipoContacto = tipoContacto;
        this.contacto = contacto;
    }
}