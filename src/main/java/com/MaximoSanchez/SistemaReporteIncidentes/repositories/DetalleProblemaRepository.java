package com.MaximoSanchez.SistemaReporteIncidentes.repositories;

import com.MaximoSanchez.SistemaReporteIncidentes.models.DetalleProblema;
import com.MaximoSanchez.SistemaReporteIncidentes.util.DetalleProblemaEmbeddedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleProblemaRepository extends JpaRepository<DetalleProblema, DetalleProblemaEmbeddedId>
{
}
