package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Author testAuthor = new Author("Автор", "Биография");

    @Test
    void testGetAllBooksSummaryView_HidesAuthors() throws Exception {
        Book book = new Book("Название", "Жанр", 2023, testAuthor);
        Page<Book> page = new PageImpl<>(List.of(book));

        Mockito.when(bookService.getAllBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/books?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].title").value("Название"))
                .andExpect(jsonPath("$.content[0].genre").value("Жанр"))
                .andExpect(jsonPath("$.content[0].year").value("2023"))
                .andExpect(jsonPath("$.content[0].author").doesNotExist());
    }

    @Test
    void testGetBookById() throws Exception {
        UUID id = UUID.randomUUID();
        Book book = new Book("Название", "Жанр", 2023, testAuthor);
        book.setId(id);

        Mockito.when(bookService.getBookById(id)).thenReturn(book);

        mockMvc.perform(get("/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Название"))
                .andExpect(jsonPath("$.genre").value("Жанр"))
                .andExpect(jsonPath("$.year").value("2023"))
                .andExpect(jsonPath("$.author.name").value("Автор"))
                .andExpect(jsonPath("$.author.biography").value("Биография"));
    }

    @Test
    void testCreateBook() throws Exception {
        Book book = new Book("Новая книга", "Роман", 2024, testAuthor);

        Mockito.when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Новая книга"));
    }

    @Test
    void testUpdateBook() throws Exception {
        UUID id = UUID.randomUUID();
        Book updatedBook = new Book("Обновлённая книга", "Фантастика", 2025, testAuthor);
        updatedBook.setId(id);

        Mockito.when(bookService.updateBook(eq(id), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Обновлённая книга"));
    }

    @Test
    void testDeleteBook() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(bookService).deleteBook(id);

        mockMvc.perform(delete("/books/" + id))
                .andExpect(status().isNoContent());
    }
}