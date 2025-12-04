package com.tarea2.ingsoft.repository;

import com.tarea2.ingsoft.model.DetalleCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Long> {
    
    // Métodos de consulta personalizados
    
    // Buscar detalles por ID de cotización
    List<DetalleCotizacion> findByCotizacionIdCotizacion(Long idCotizacion);
    
    // Buscar detalles por ID de mueble
    List<DetalleCotizacion> findByMuebleIdMueble(Long idMueble);
    
    // Buscar detalles por ID de variante
    List<DetalleCotizacion> findByVarianteIdVariante(Long idVariante);
    
    // Consulta personalizada: obtener los muebles más vendidos
    @Query("SELECT d.mueble.idMueble, SUM(d.cantidad) as total " +
           "FROM DetalleCotizacion d " +
           "WHERE d.cotizacion.confirmada = true " +
           "GROUP BY d.mueble.idMueble " +
           "ORDER BY total DESC")
    List<Object[]> findMueblesMasVendidos();
    
    // Consulta personalizada: calcular total vendido por mueble
    @Query("SELECT SUM(d.cantidad) FROM DetalleCotizacion d " +
           "WHERE d.mueble.idMueble = :idMueble AND d.cotizacion.confirmada = true")
    Integer calcularCantidadVendidaPorMueble(@Param("idMueble") Long idMueble);
}