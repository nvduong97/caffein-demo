package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AuthorServiceTest {

    @MockBean
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "Duong", "Ha Noi", "duong@gmail.com"));
        authors.add(new Author(2, "Anh", "Ha Nam", "anh@gmail.com"));
        authors.add(new Author(3, "Binh", "Ha Tay", "binh@gmail.com"));
        authors.add(new Author(4, "Cong", "Ha Tinh", "cong@gmail.com"));

        Mockito.when(authorRepository.findAll()).thenReturn(authors);
        Mockito.when(authorRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(authors.get(0)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllAuthor() {
        Assert.assertEquals(4, authorRepository.findAll().size());
    }

    @Test
    void createAuthor() {

    }

    @Test
    void getAuthor() {
        Assert.assertEquals("duong@gmail.com", authorRepository.findById(1).get().getEmail());
    }

    @Test
    void updateAuthor() {
    }

    @Test
    void deleteAuthor() {
    }

    @Test
    void filter() {
    }
}