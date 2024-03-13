package com.MaximoSanchez.SistemaReporteIncidentes.repositories;

import com.MaximoSanchez.SistemaReporteIncidentes.models.Servicio;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio, Long>, JpaSpecificationExecutor<Servicio>
{
    Optional<Servicio> findByNombre(String nombre);

    interface Specs {
        static Specification<Servicio> nombreLike(String nombre) {
            return (servicio, cq, cb) -> cb.like(servicio.get("nombre"), "%" + nombre.toUpperCase() + "%");
        }
}

}
