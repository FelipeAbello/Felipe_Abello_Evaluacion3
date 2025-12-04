package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.repository.MuebleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MuebleCrudTest {
    
    @Mock
    private MuebleRepository muebleRepository;
    
    @InjectMocks
    private MuebleService muebleService;
    
    private Mueble mueble;
    
    @BeforeEach
    void setUp() {
        mueble = new Mueble();
        mueble.setIdMueble(1L);
        mueble.setNombreMueble("Sillón Ejecutivo");
        mueble.setTipo("SILLON");
        mueble.setPrecioBase(new BigDecimal("250000.00"));
        mueble.setStock(8);
        mueble.setEstado("ACTIVO");
        mueble.setTamanio("GRANDE");
        mueble.setMaterial("Cuero");
    }
    
    @Test
    @DisplayName("CREATE: Crear un nuevo mueble")
    void testCrearMueble() {
        
        Mueble nuevoMueble = new Mueble();
        nuevoMueble.setNombreMueble("Mesa Moderna");
        nuevoMueble.setTipo("MESA");
        nuevoMueble.setPrecioBase(new BigDecimal("150000.00"));
        nuevoMueble.setStock(5);
        
        when(muebleRepository.save(any(Mueble.class))).thenReturn(nuevoMueble);
        
       
        Mueble resultado = muebleService.crearMueble(nuevoMueble);
        
       
        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
        assertEquals("Mesa Moderna", resultado.getNombreMueble());
        verify(muebleRepository, times(1)).save(any(Mueble.class));
        System.out.println("✓ Mueble creado exitosamente: " + resultado.getNombreMueble());
    }
    
    @Test
    @DisplayName("READ: Listar todos los muebles")
    void testListarTodosMuebles() {
       
        Mueble mueble2 = new Mueble();
        mueble2.setIdMueble(2L);
        mueble2.setNombreMueble("Estante Modular");
        
        List<Mueble> listaMuebles = Arrays.asList(mueble, mueble2);
        when(muebleRepository.findAll()).thenReturn(listaMuebles);
        
      
        List<Mueble> resultado = muebleService.listarTodosMuebles();
        
        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(muebleRepository, times(1)).findAll();
        System.out.println("✓ Total de muebles encontrados: " + resultado.size());
    }
    
    @Test
    @DisplayName("READ: Obtener mueble por ID")
    void testObtenerMueblePorId() {
     
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(mueble));
        
      
        Optional<Mueble> resultado = muebleService.obtenerMueblePorId(1L);
        
      
        assertTrue(resultado.isPresent());
        assertEquals("Sillón Ejecutivo", resultado.get().getNombreMueble());
        verify(muebleRepository, times(1)).findById(1L);
        System.out.println("✓ Mueble encontrado: " + resultado.get().getNombreMueble());
    }
    
    @Test
    @DisplayName("READ: Mueble no encontrado por ID")
    void testObtenerMuebleNoEncontrado() {
    
        when(muebleRepository.findById(999L)).thenReturn(Optional.empty());
        
      
        Optional<Mueble> resultado = muebleService.obtenerMueblePorId(999L);
        
      
        assertFalse(resultado.isPresent());
        System.out.println("✓ Mueble no encontrado (esperado)");
    }
    
    @Test
    @DisplayName("UPDATE: Actualizar un mueble existente")
    void testActualizarMueble() {
      
        Mueble muebleActualizado = new Mueble();
        muebleActualizado.setNombreMueble("Sillón Ejecutivo Premium");
        muebleActualizado.setTipo("SILLON");
        muebleActualizado.setPrecioBase(new BigDecimal("300000.00"));
        muebleActualizado.setStock(12);
        muebleActualizado.setTamanio("GRANDE");
        muebleActualizado.setMaterial("Cuero Premium");
        
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(mueble));
        when(muebleRepository.save(any(Mueble.class))).thenReturn(mueble);
        
    
        Mueble resultado = muebleService.actualizarMueble(1L, muebleActualizado);
        
      
        assertNotNull(resultado);
        assertEquals("Sillón Ejecutivo Premium", resultado.getNombreMueble());
        assertEquals(new BigDecimal("300000.00"), resultado.getPrecioBase());
        verify(muebleRepository, times(1)).save(any(Mueble.class));
        System.out.println("✓ Mueble actualizado: " + resultado.getNombreMueble());
    }
    
    @Test
    @DisplayName("UPDATE: Error al actualizar mueble inexistente")
    void testActualizarMuebleInexistente() {
    
        Mueble muebleActualizado = new Mueble();
        when(muebleRepository.findById(999L)).thenReturn(Optional.empty());
        
      
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            muebleService.actualizarMueble(999L, muebleActualizado);
        });
        
        assertTrue(exception.getMessage().contains("Mueble no encontrado"));
        System.out.println("✓ Excepción al actualizar mueble inexistente: " + exception.getMessage());
    }
    
    @Test
    @DisplayName("DELETE: Desactivar un mueble (soft delete)")
    void testDesactivarMueble() {
 
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(mueble));
        when(muebleRepository.save(any(Mueble.class))).thenReturn(mueble);
        
    
        Mueble resultado = muebleService.desactivarMueble(1L);
        
       
        assertNotNull(resultado);
        assertEquals("INACTIVO", resultado.getEstado());
        verify(muebleRepository, times(1)).save(any(Mueble.class));
        System.out.println("✓ Mueble desactivado exitosamente");
    }
    
    @Test
    @DisplayName("DELETE: Error al desactivar mueble inexistente")
    void testDesactivarMuebleInexistente() {
     
        when(muebleRepository.findById(999L)).thenReturn(Optional.empty());
        
      
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            muebleService.desactivarMueble(999L);
        });
        
        assertTrue(exception.getMessage().contains("Mueble no encontrado"));
        System.out.println("✓ Excepción al desactivar mueble inexistente: " + exception.getMessage());
    }
    
    @Test
    @DisplayName("READ: Buscar muebles por tipo")
    void testBuscarMueblesPorTipo() {
     
        List<Mueble> sillones = Arrays.asList(mueble);
        when(muebleRepository.findByTipo("SILLON")).thenReturn(sillones);
        
     
        List<Mueble> resultado = muebleService.buscarPorTipo("SILLON");
        
    
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("SILLON", resultado.get(0).getTipo());
        System.out.println("✓ Muebles encontrados por tipo: " + resultado.size());
    }
    
    @Test
    @DisplayName("READ: Listar muebles activos")
    void testListarMueblesActivos() {
   
        List<Mueble> mueblesActivos = Arrays.asList(mueble);
        when(muebleRepository.findByEstado("ACTIVO")).thenReturn(mueblesActivos);
        
       
        List<Mueble> resultado = muebleService.listarMueblesActivos();
        
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ACTIVO", resultado.get(0).getEstado());
        System.out.println("✓ Muebles activos encontrados: " + resultado.size());
    }
}