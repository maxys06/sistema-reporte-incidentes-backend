package com.MaximoSanchez.SistemaReporteIncidentes.repositories;

import com.MaximoSanchez.SistemaReporteIncidentes.models.TipoContacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoContactoRepository extends JpaRepository<TipoContacto, Long>
{
    Optional<TipoContacto> findByTipo(String tipo);
}
