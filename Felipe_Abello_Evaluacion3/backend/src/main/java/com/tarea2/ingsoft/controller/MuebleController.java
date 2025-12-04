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
    

    @PostMapping
    public ResponseEntity<Mueble> crearMueble(@RequestBody Mueble mueble) {
        try {
            Mueble nuevoMueble = muebleService.crearMueble(mueble);
            return new ResponseEntity<>(nuevoMueble, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

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
    

    @GetMapping("/{id}")
    public ResponseEntity<Mueble> obtenerMueblePorId(@PathVariable("id") Long id) {
        Optional<Mueble> mueble = muebleService.obtenerMueblePorId(id);
        
        if (mueble.isPresent()) {
            return new ResponseEntity<>(mueble.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @GetMapping("/activos")
    public ResponseEntity<List<Mueble>> listarMueblesActivos() {
        try {
            List<Mueble> muebles = muebleService.listarMueblesActivos();
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Mueble>> buscarPorTipo(@PathVariable("tipo") String tipo) {
        try {
            List<Mueble> muebles = muebleService.buscarPorTipo(tipo);
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/material/{material}")
    public ResponseEntity<List<Mueble>> buscarPorMaterial(@PathVariable("material") String material) {
        try {
            List<Mueble> muebles = muebleService.buscarPorMaterial(material);
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/buscar")
    public ResponseEntity<List<Mueble>> buscarPorNombre(@RequestParam("nombre") String nombre) {
        try {
            List<Mueble> muebles = muebleService.buscarPorNombre(nombre);
            return new ResponseEntity<>(muebles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

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