package com.tarea2.ingsoft.controller;

import com.tarea2.ingsoft.model.Cotizacion;
import com.tarea2.ingsoft.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "*")
public class CotizacionController {
    
    @Autowired
    private CotizacionService cotizacionService;
    

    @PostMapping
    public ResponseEntity<Cotizacion> crearCotizacionVacia() {
        try {
            Cotizacion cotizacion = cotizacionService.crearCotizacionVacia();
            return new ResponseEntity<>(cotizacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PostMapping("/{idCotizacion}/detalles")
    public ResponseEntity<?> agregarDetalle(
            @PathVariable("idCotizacion") Long idCotizacion,
            @RequestBody Map<String, Object> detalle) {
        try {
            Long idMueble = Long.valueOf(detalle.get("idMueble").toString());
            Long idVariante = detalle.get("idVariante") != null ? 
                              Long.valueOf(detalle.get("idVariante").toString()) : null;
            Integer cantidad = Integer.valueOf(detalle.get("cantidad").toString());
            
            Cotizacion cotizacion = cotizacionService.agregarDetalleCotizacion(
                idCotizacion, idMueble, idVariante, cantidad
            );
            
            return new ResponseEntity<>(cotizacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/{idCotizacion}/confirmar")
    public ResponseEntity<?> confirmarVenta(@PathVariable("idCotizacion") Long idCotizacion) {
        try {
            Cotizacion cotizacion = cotizacionService.confirmarVenta(idCotizacion);
            return new ResponseEntity<>(cotizacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Captura específicamente el error de stock insuficiente
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping
    public ResponseEntity<List<Cotizacion>> listarTodasCotizaciones() {
        try {
            List<Cotizacion> cotizaciones = cotizacionService.listarTodasCotizaciones();
            if (cotizaciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cotizaciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Cotizacion> obtenerCotizacionPorId(@PathVariable("id") Long id) {
        Optional<Cotizacion> cotizacion = cotizacionService.obtenerCotizacionPorId(id);
        
        if (cotizacion.isPresent()) {
            return new ResponseEntity<>(cotizacion.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @GetMapping("/pendientes")
    public ResponseEntity<List<Cotizacion>> listarCotizacionesPendientes() {
        try {
            List<Cotizacion> cotizaciones = cotizacionService.listarCotizacionesPendientes();
            return new ResponseEntity<>(cotizaciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/ventas")
    public ResponseEntity<List<Cotizacion>> listarVentas() {
        try {
            List<Cotizacion> ventas = cotizacionService.listarVentas();
            return new ResponseEntity<>(ventas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}