package edu.eci.ieti.greenwish.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.Role;
import edu.eci.ieti.greenwish.models.User;
import edu.eci.ieti.greenwish.models.dto.UserDto;
import edu.eci.ieti.greenwish.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private List<User> users;
    private User user;
    private final String BASE_URL = "/users/";

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(userController).build();
        user = new User("1", "Pepe", "pepe@pepe.com", "1234", Role.CUSTOMER.getName(), 0);
        User user2 = new User("2", "Juan", "juan@juan.com", "5678", Role.CUSTOMER.getName(), 0);
        users = List.of(user, user2);
    }

    @Test
    void testFindAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Pepe")))
                .andExpect(jsonPath("$[0].email", is("pepe@pepe.com")))
                .andExpect(jsonPath("$[0].points", is(0)))
                .andExpect(jsonPath("$[1].name", is("Juan")))
                .andExpect(jsonPath("$[1].email", is("juan@juan.com")))
                .andExpect(jsonPath("$[1].points", is(0)));
        verify(userService, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingUser() throws Exception {
        String id = "1";
        when(userService.findById(id)).thenReturn(user);
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pepe")))
                .andExpect(jsonPath("$.email", is("pepe@pepe.com")))
                .andExpect(jsonPath("$.points", is(0)));
        verify(userService, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotExistingBenefit() throws Exception {
        String id = "511";
        when(userService.findById(id)).thenThrow(new UserNotFoundException());
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).findById(id);
    }

    @Test
    void testSaveNewUser() throws Exception {
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234", false);
        when(userService.save(any())).thenReturn(user);
        String json = "{\"name\":\"" + userDto.getName() + "\",\"email\":\"" + userDto.getEmail()
                + "\",\"password\":\""
                + userDto.getPassword() + "\"}";
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        verify(userService, times(1)).save(any());
    }

    @Test
    void testUpdateExistingUser() throws Exception {
        String id = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234", false);
        String json = "{\"name\":\"" + userDto.getName() + "\",\"email\":\"" + userDto.getEmail()
                + "\",\"password\":\""
                + userDto.getPassword() + "\"}";
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        verify(userService, times(1)).update(any(), eq(id));
    }

    @Test
    void testUpdateNotExistingCompany() throws Exception {
        String id = "511";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234", false);
        String json = "{\"name\":\"" + userDto.getName() + "\",\"email\":\"" + userDto.getEmail()
                + "\",\"password\":\""
                + userDto.getPassword() + "\"}";
        doThrow(new UserNotFoundException()).when(userService).update(any(), eq(id));
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).update(any(), eq(id));
    }

    @Test
    void testDeleteExistingCompany() throws Exception {
        String id = "1";
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteNotExistingCompany() throws Exception {
        String id = "511";
        doThrow(new UserNotFoundException()).when(userService).deleteById(id);
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).deleteById(id);
    }
}