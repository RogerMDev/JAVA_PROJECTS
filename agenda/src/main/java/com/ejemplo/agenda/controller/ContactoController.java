package com.ejemplo.agenda.controller;

import com.ejemplo.agenda.model.Contacto;
import com.ejemplo.agenda.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contactos")
public class ContactoController {

    @Autowired
    private ContactoRepository repo;

    // 1. Listar todos los contactos
    @GetMapping
    public List<Contacto> listar() {
        return repo.findAll();
    }

    // 2. Obtener un contacto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> obtenerPorId(@PathVariable Long id) {
        Optional<Contacto> contacto = repo.findById(id);
        return contacto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. Crear un nuevo contacto
    @PostMapping
    public ResponseEntity<Contacto> crear(@RequestBody Contacto contacto) {
        Contacto creado = repo.save(contacto);
        return ResponseEntity.status(201).body(creado); // Devuelve 201 Created
    }

    // 4. Actualizar un contacto existente
    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(@PathVariable Long id, @RequestBody Contacto datos) {
        Optional<Contacto> contactoExistente = repo.findById(id);
        if (contactoExistente.isPresent()) {
            Contacto c = contactoExistente.get();
            c.setNombre(datos.getNombre());
            c.setTelefono(datos.getTelefono());
            c.setEmail(datos.getEmail());
            return ResponseEntity.ok(repo.save(c));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar un contacto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
