package com.tarea2.ingsoft.repository;

import com.tarea2.ingsoft.model.Mueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuebleRepository extends JpaRepository<Mueble, Long> {
    
    // Métodos de consulta personalizados
    
    // Buscar muebles por tipo
    List<Mueble> findByTipo(String tipo);
    
    // Buscar muebles por estado
    List<Mueble> findByEstado(String estado);
    
    // Buscar muebles activos
    List<Mueble> findByEstadoOrderByNombreMuebleAsc(String estado);
    
    // Buscar muebles por material
    List<Mueble> findByMaterial(String material);
    
    // Buscar muebles por tamaño
    List<Mueble> findByTamanio(String tamanio);
    
    // Buscar muebles con stock mayor a cierto valor
    List<Mueble> findByStockGreaterThan(Integer stock);
    
    // Buscar por nombre (búsqueda parcial, case insensitive)
    List<Mueble> findByNombreMuebleContainingIgnoreCase(String nombre);
}