package com.tarea2.ingsoft.controller;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.service.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/muebles")
@CrossOrigin(origins = "*")
public class MuebleController {
    
    @Autowired
    private MuebleService muebleService;
    
    /**
     * CREATE - Crear un nuevo mueble
     * POST http://localhost:8080/api/muebles
     */
    @PostMapping
    public ResponseEntity<Mueble> crearMueble(@RequestBody Mueble mueble) {
        try {
            Mueble nuevoMueble = muebleService.crearMueble(mueble);
            return new ResponseEntity<>(nuevoMueble, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * READ - Listar todos los muebles
     * GET http://localhost:8080/api/muebles
     */
    @GetMapping
    public ResponseEntity<List<Mueble>> listarTodosMuebles() {
        try {
            List<Mueble> muebles = muebleService.listarTodosMuebles();
            if (muebles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * READ - Obtener mueble por ID
     * GET http://localhost:8080/api/muebles/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mueble> obtenerMueblePorId(@PathVariable("id") Long id) {
        Optional<Mueble> mueble = muebleService.obtenerMueblePorId(id);
        
        if (mueble.isPresent()) {
            return new ResponseEntity<>(mueble.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * READ - Listar muebles activos
     * GET http://localhost:8080/api/muebles/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Mueble>> listarMueblesActivos() {
        try {
            List<Mueble> muebles = muebleService.listarMueblesActivos();
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * READ - Buscar muebles por tipo
     * GET http://localhost:8080/api/muebles/tipo/SILLA
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Mueble>> buscarPorTipo(@PathVariable("tipo") String tipo) {
        try {
            List<Mueble> muebles = muebleService.buscarPorTipo(tipo);
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * READ - Buscar muebles por material
     * GET http://localhost:8080/api/muebles/material/Madera
     */
    @GetMapping("/material/{material}")
    public ResponseEntity<List<Mueble>> buscarPorMaterial(@PathVariable("material") String material) {
        try {
            List<Mueble> muebles = muebleService.buscarPorMaterial(material);
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * READ - Buscar muebles por nombre
     * GET http://localhost:8080/api/muebles/buscar?nombre=silla
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Mueble>> buscarPorNombre(@RequestParam("nombre") String nombre) {
        try {
            List<Mueble> muebles = muebleService.buscarPorNombre(nombre);
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * UPDATE - Actualizar un mueble
     * PUT http://localhost:8080/api/muebles/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mueble> actualizarMueble(@PathVariable("id") Long id, 
                                                     @RequestBody Mueble mueble) {
        try {
            Mueble muebleActualizado = muebleService.actualizarMueble(id, mueble);
            return new ResponseEntity<>(muebleActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * DELETE - Desactivar un mueble (soft delete)
     * DELETE http://localhost:8080/api/muebles/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> desactivarMueble(@PathVariable("id") Long id) {
        try {
            muebleService.desactivarMueble(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * PUT - Activar un mueble
     * PUT http://localhost:8080/api/muebles/1/activar
     */
    @PutMapping("/{id}/activar")
    public ResponseEntity<Mueble> activarMueble(@PathVariable("id") Long id) {
        try {
            Mueble muebleActivado = muebleService.activarMueble(id);
            return new ResponseEntity<>(muebleActivado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}