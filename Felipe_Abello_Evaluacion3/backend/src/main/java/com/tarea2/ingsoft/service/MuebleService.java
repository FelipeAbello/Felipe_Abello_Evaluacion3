package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.repository.MuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuebleService {
    
    @Autowired
    private MuebleRepository muebleRepository;
    
    // CREATE - Crear un nuevo mueble
    public Mueble crearMueble(Mueble mueble) {
        mueble.setEstado("ACTIVO");
        return muebleRepository.save(mueble);
    }
    
    // READ - Obtener todos los muebles
    public List<Mueble> listarTodosMuebles() {
        return muebleRepository.findAll();
    }
    
    // READ - Obtener muebles activos
    public List<Mueble> listarMueblesActivos() {
        return muebleRepository.findByEstado("ACTIVO");
    }
    
    // READ - Obtener un mueble por ID
    public Optional<Mueble> obtenerMueblePorId(Long id) {
        return muebleRepository.findById(id);
    }
    
    // READ - Buscar muebles por tipo
    public List<Mueble> buscarPorTipo(String tipo) {
        return muebleRepository.findByTipo(tipo);
    }
    
    // READ - Buscar muebles por material
    public List<Mueble> buscarPorMaterial(String material) {
        return muebleRepository.findByMaterial(material);
    }
    
    // READ - Buscar muebles por nombre
    public List<Mueble> buscarPorNombre(String nombre) {
        return muebleRepository.findByNombreMuebleContainingIgnoreCase(nombre);
    }
    
    // UPDATE - Actualizar un mueble existente
    public Mueble actualizarMueble(Long id, Mueble muebleActualizado) {
        Optional<Mueble> muebleExistente = muebleRepository.findById(id);
        
        if (muebleExistente.isPresent()) {
            Mueble mueble = muebleExistente.get();
            mueble.setNombreMueble(muebleActualizado.getNombreMueble());
            mueble.setTipo(muebleActualizado.getTipo());
            mueble.setPrecioBase(muebleActualizado.getPrecioBase());
            mueble.setStock(muebleActualizado.getStock());
            mueble.setTamanio(muebleActualizado.getTamanio());
            mueble.setMaterial(muebleActualizado.getMaterial());
            
            return muebleRepository.save(mueble);
        }
        
        throw new RuntimeException("Mueble no encontrado con ID: " + id);
    }
    
    // DELETE - Desactivar un mueble (no se elimina físicamente)
    public Mueble desactivarMueble(Long id) {
        Optional<Mueble> muebleExistente = muebleRepository.findById(id);
        
        if (muebleExistente.isPresent()) {
            Mueble mueble = muebleExistente.get();
            mueble.setEstado("INACTIVO");
            return muebleRepository.save(mueble);
        }
        
        throw new RuntimeException("Mueble no encontrado con ID: " + id);
    }
    
    // Activar un mueble
    public Mueble activarMueble(Long id) {
        Optional<Mueble> muebleExistente = muebleRepository.findById(id);
        
        if (muebleExistente.isPresent()) {
            Mueble mueble = muebleExistente.get();
            mueble.setEstado("ACTIVO");
            return muebleRepository.save(mueble);
        }
        
        throw new RuntimeException("Mueble no encontrado con ID: " + id);
    }
    
    // Verificar si hay stock suficiente
    public boolean verificarStock(Long idMueble, Integer cantidad) {
        Optional<Mueble> mueble = muebleRepository.findById(idMueble);
        return mueble.isPresent() && mueble.get().getStock() >= cantidad;
    }
    
    // Decrementar stock
    public void decrementarStock(Long idMueble, Integer cantidad) {
        Optional<Mueble> muebleOpt = muebleRepository.findById(idMueble);
        
        if (muebleOpt.isPresent()) {
            Mueble mueble = muebleOpt.get();
            
            if (mueble.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente para el mueble: " + mueble.getNombreMueble());
            }
            
            mueble.setStock(mueble.getStock() - cantidad);
            muebleRepository.save(mueble);
        } else {
            throw new RuntimeException("Mueble no encontrado con ID: " + idMueble);
        }
    }
}