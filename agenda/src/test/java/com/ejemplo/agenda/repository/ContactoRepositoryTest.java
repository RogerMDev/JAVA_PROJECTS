package com.ejemplo.agenda.repository;

import com.ejemplo.agenda.model.Contacto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 🧪 Spring Boot configura solo la BBDD para testing
public class ContactoRepositoryTest {

    @Autowired
    private ContactoRepository repo;

    @Test
    public void guardarYBuscarContacto() {
        // Crear contacto de prueba
        Contacto contacto = new Contacto("Ana", "123456789", "ana@email.com");

        // Guardarlo
        Contacto guardado = repo.save(contacto);

        // Buscarlo por ID
        Optional<Contacto> encontrado = repo.findById(guardado.getId());

        // Comprobar que existe
        assertThat(encontrado).isPresent();

        // Verificar que los datos son correctos
        assertThat(encontrado.get().getNombre()).isEqualTo("Ana");
        assertThat(encontrado.get().getTelefono()).isEqualTo("123456789");
    }
    @Test
    public void buscarContactoInexistente_devuelveVacio() {
        // Intentamos buscar un ID que no existe (ej: 999L)
        Optional<Contacto> resultado = repo.findById(999L);

        // Comprobamos que NO está presente
        assertThat(resultado).isNotPresent();
    }
}