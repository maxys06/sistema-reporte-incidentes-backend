package com.MaximoSanchez.SistemaReporteIncidentes.repositories;

import com.MaximoSanchez.SistemaReporteIncidentes.models.Problema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemaRepository extends JpaRepository<Problema, Long>
{
    /*@Query(value = "SELECT s.idServicio FROM Servicio s JOIN s.problemas p WHERE p.idProblema = :idProblema")
    long findIdServicioByIdProblema(@Param("idProblema") Long idProblema);*/
}
