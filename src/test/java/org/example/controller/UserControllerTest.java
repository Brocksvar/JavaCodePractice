package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Order;
import org.example.entity.User;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void testGetAllUsersSummaryView() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Alex");
        user.setEmail("alex@test.com");

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("Alex"))
                .andExpect(jsonPath("$[0].email").value("alex@test.com"))
                .andExpect(jsonPath("$[0].orders").doesNotExist());
    }

    @Test
    void testGetUserDetailsView() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Alex");
        user.setEmail("alex@test.com");
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setProducts(List.of("Book", "Pen"));
        order.setCost(50.0);
        order.setStatus("PAID");
        user.setOrders(List.of(order));

        Mockito.when(userService.getUser(eq(user.getId()))).thenReturn(user);

        mockMvc.perform(get("/user/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@test.com"))
                .andExpect(jsonPath("$.orders[0].products[0]").value("Book"))
                .andExpect(jsonPath("$.orders[0].status").value("PAID"));
    }

    @Test
    void testCreateUserValid() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Alex");
        user.setEmail("alex@test.com");

        Mockito.doNothing().when(userService).createUser(any(User.class));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@test.com"));
    }

    @Test
    void testCreateUserInvalidEmail() throws Exception {
        User user = new User();
        user.setName("Alex");
        user.setEmail("wrong_format");

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Ошибка валидации входных данных"));
    }

    @Test
    void testUpdateUserValid() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Updated Name");
        user.setEmail("updated@test.com");

        Mockito.doNothing().when(userService).updateUser(any(User.class));

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.doNothing().when(userService).deleteUser(eq(id));

        mockMvc.perform(delete("/delete/" + id))
                .andExpect(status().isOk());
    }
}