package com.tarea2.ingsoft.controller;

import com.tarea2.ingsoft.model.Variante;
import com.tarea2.ingsoft.service.VarianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/variantes")
@CrossOrigin(origins = "*")
public class VarianteController {
    
    @Autowired
    private VarianteService varianteService;
    

    @PostMapping
    public ResponseEntity<Variante> crearVariante(@RequestBody Variante variante) {
        try {
            Variante nuevaVariante = varianteService.crearVariante(variante);
            return new ResponseEntity<>(nuevaVariante, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping
    public ResponseEntity<List<Variante>> listarTodasVariantes() {
        try {
            List<Variante> variantes = varianteService.listarTodasVariantes();
            if (variantes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(variantes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Variante> obtenerVariantePorId(@PathVariable("id") Long id) {
        Optional<Variante> variante = varianteService.obtenerVariantePorId(id);
        
        if (variante.isPresent()) {
            return new ResponseEntity<>(variante.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Variante>> buscarPorTipo(@PathVariable("tipo") String tipo) {
        try {
            List<Variante> variantes = varianteService.buscarPorTipo(tipo);
            return new ResponseEntity<>(variantes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variante> actualizarVariante(@PathVariable("id") Long id, 
                                                         @RequestBody Variante variante) {
        try {
            Variante varianteActualizada = varianteService.actualizarVariante(id, variante);
            return new ResponseEntity<>(varianteActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarVariante(@PathVariable("id") Long id) {
        try {
            varianteService.eliminarVariante(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}