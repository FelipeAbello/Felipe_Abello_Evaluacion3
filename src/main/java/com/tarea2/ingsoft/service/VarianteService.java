package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Variante;
import com.tarea2.ingsoft.repository.VarianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VarianteService {
    
    @Autowired
    private VarianteRepository varianteRepository;
    
    // CREATE - Crear una nueva variante
    public Variante crearVariante(Variante variante) {
        return varianteRepository.save(variante);
    }
    
    // READ - Listar todas las variantes
    public List<Variante> listarTodasVariantes() {
        return varianteRepository.findAll();
    }
    
    // READ - Obtener variante por ID
    public Optional<Variante> obtenerVariantePorId(Long id) {
        return varianteRepository.findById(id);
    }
    
    // READ - Buscar variantes por tipo
    public List<Variante> buscarPorTipo(String tipo) {
        return varianteRepository.findByTipoVariante(tipo);
    }
    
    // READ - Buscar variante por nombre
    public Optional<Variante> buscarPorNombre(String nombre) {
        return varianteRepository.findByNombreVariante(nombre);
    }
    
    // UPDATE - Actualizar una variante
    public Variante actualizarVariante(Long id, Variante varianteActualizada) {
        Optional<Variante> varianteExistente = varianteRepository.findById(id);
        
        if (varianteExistente.isPresent()) {
            Variante variante = varianteExistente.get();
            variante.setNombreVariante(varianteActualizada.getNombreVariante());
            variante.setDescripcion(varianteActualizada.getDescripcion());
            variante.setPrecioAdicional(varianteActualizada.getPrecioAdicional());
            variante.setTipoVariante(varianteActualizada.getTipoVariante());
            
            return varianteRepository.save(variante);
        }
        
        throw new RuntimeException("Variante no encontrada con ID: " + id);
    }
    
    // DELETE - Eliminar una variante
    public void eliminarVariante(Long id) {
        varianteRepository.deleteById(id);
    }
}