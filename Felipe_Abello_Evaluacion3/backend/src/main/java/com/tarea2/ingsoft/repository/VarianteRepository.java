package com.tarea2.ingsoft.repository;

import com.tarea2.ingsoft.model.Variante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VarianteRepository extends JpaRepository<Variante, Long> {
    
    // Métodos de consulta personalizados
    
    // Buscar variantes por tipo
    List<Variante> findByTipoVariante(String tipoVariante);
    
    // Buscar variante por nombre exacto
    Optional<Variante> findByNombreVariante(String nombreVariante);
    
    // Buscar variantes ordenadas por precio
    List<Variante> findAllByOrderByPrecioAdicionalAsc();
    
    // Buscar variantes que contengan cierto texto en el nombre
    List<Variante> findByNombreVarianteContainingIgnoreCase(String nombre);
}