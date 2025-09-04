package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void testGetAllProducts() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Product p1 = new Product();
        p1.setProductId(id1);
        p1.setName("Товар 1");
        p1.setPrice(100.0);

        Product p2 = new Product();
        p2.setProductId(id2);
        p2.setName("Товар 2");
        p2.setPrice(200.0);

        when(productService.getAllProducts()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productId").value(id1.toString()))
                .andExpect(jsonPath("$[0].name").value("Товар 1"))
                .andExpect(jsonPath("$[1].productId").value(id2.toString()))
                .andExpect(jsonPath("$[1].name").value("Товар 2"));
    }

    @Test
    void testGetProductById() throws Exception {
        UUID id = UUID.randomUUID();
        Product p = new Product();
        p.setProductId(id);
        p.setName("Товар 1");
        p.setPrice(150.0);

        when(productService.getProductById(id)).thenReturn(p);

        mockMvc.perform(get("/api/products/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Товар 1"))
                .andExpect(jsonPath("$.price").value(150.0));
    }

    @Test
    void testCreateProduct() throws Exception {
        UUID id = UUID.randomUUID();
        Product p = new Product();
        p.setProductId(id);
        p.setName("Новый товар");
        p.setPrice(300.0);

        String requestJson = objectMapper.writeValueAsString(p);

        when(productService.createProduct(any(Product.class))).thenReturn(p);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Новый товар"))
                .andExpect(jsonPath("$.price").value(300.0));
    }

    @Test
    void testUpdateProduct() throws Exception {
        UUID id = UUID.randomUUID();
        Product p = new Product();
        p.setProductId(id);
        p.setName("Обновлённый товар");
        p.setPrice(350.0);

        String requestJson = objectMapper.writeValueAsString(p);

        when(productService.updateProduct(any(UUID.class), any(Product.class))).thenReturn(p);

        mockMvc.perform(put("/api/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Обновлённый товар"))
                .andExpect(jsonPath("$.price").value(350.0));
    }

    @Test
    void testDeleteProduct() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/products/" + id))
                .andExpect(status().isNoContent());
    }
}
