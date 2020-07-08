package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.model.AuthorDTO;
import com.example.demo.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "Duong", "Ha Noi", "duong@gmail.com"));
        authors.add(new Author(2, "Anh", "Ha Nam", "anh@gmail.com"));
        authors.add(new Author(3, "Binh", "Ha Tay", "binh@gmail.com"));
        authors.add(new Author(4, "Cong", "Ha Tinh", "cong@gmail.com"));
//
//        Mockito.when(authorService.findAll()).thenReturn(authors);
//        Mockito.when(authorService.findById(1)).thenReturn(java.util.Optional.ofNullable(authors.get(0)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllAuthor() {

    }

    @Test
    void getAuthor() {
    }

    @Test
    void updateAuthor() {
    }

    @Test
    void createAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO("Nguyễn Văn Dương", "Ha Noi", "duong@gmail.com");
        Author author = new Author(1, "Nguyễn Văn Dương", "Ha Noi", "duong@gmail.com");

        Mockito.when(authorService.createAuthor(any(AuthorDTO.class))).thenReturn(author);

        mockMvc.perform(post("/v1/authors")
                .content(asJsonString(authorDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$data.id", Matchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteAuthor() {
    }

    @Test
    void search() {
    }
}