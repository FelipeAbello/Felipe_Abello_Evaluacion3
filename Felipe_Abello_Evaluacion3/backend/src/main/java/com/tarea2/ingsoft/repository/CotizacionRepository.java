package com.tarea2.ingsoft.repository;

import com.tarea2.ingsoft.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    
    // Métodos de consulta personalizados
    
    // Buscar cotizaciones por estado
    List<Cotizacion> findByEstado(String estado);
    
    // Buscar cotizaciones confirmadas
    List<Cotizacion> findByConfirmada(Boolean confirmada);
    
    // Buscar cotizaciones por estado y confirmación
    List<Cotizacion> findByEstadoAndConfirmada(String estado, Boolean confirmada);
    
    // Buscar cotizaciones entre fechas
    List<Cotizacion> findByFechaCotizacionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar cotizaciones pendientes
    List<Cotizacion> findByEstadoOrderByFechaCotizacionDesc(String estado);
    
    // Contar cotizaciones por estado
    Long countByEstado(String estado);
}