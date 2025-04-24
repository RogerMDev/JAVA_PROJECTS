package com.ejemplo.agenda.controller;

import com.ejemplo.agenda.repository.ContactoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactoRepository repo;

    @Test
    public void getContactos_devuelve200YLista() throws Exception {
        mockMvc.perform(get("/contactos"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray()); // Respuesta es un array JSON
    }

    @Test
    public void crearContacto_devuelve201YContactoCreado() throws Exception {
        String jsonContacto = """
        {
            "nombre": "Carlos",
            "telefono": "654987321",
            "email": "carlos@email.com"
        }
    """;

        mockMvc.perform(
                        post("/contactos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonContacto)
                )
                .andExpect(status().isCreated()) // 201 Created
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.telefono").value("654987321"))
                .andExpect(jsonPath("$.email").value("carlos@email.com"));
    }
}