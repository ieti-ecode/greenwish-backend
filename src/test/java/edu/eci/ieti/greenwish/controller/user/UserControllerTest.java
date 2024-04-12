package edu.eci.ieti.greenwish.controller.user;

import edu.eci.ieti.greenwish.exception.UserNotFoundException;
import edu.eci.ieti.greenwish.repository.document.User;
import edu.eci.ieti.greenwish.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private final String BASE_URL = "/v1/users/";

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(userController).build();
    }


    @Test
    void testFindAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Pepe", "pepe@pepe.com", "1234"));
        users.add(new User("Juan", "juan@juan.com", "5678"));
        when(userService.all()).thenReturn(users);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Pepe")))
                .andExpect(jsonPath("$[0].email", is("pepe@pepe.com")))
                .andExpect(jsonPath("$[0].points", is(0)))
                .andExpect(jsonPath("$[1].name", is("Juan")))
                .andExpect(jsonPath("$[1].email", is("juan@juan.com")))
                .andExpect(jsonPath("$[1].points", is(0)));
        verify(userService, times(1)).all();
    }

    @Test
    void testFindByIdExistingUser() throws Exception {
        String id = "1";
        User user = new User("Pepe", "pepe@pepe.com", "1234");
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
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        User user = new User(userDto);
        when(userService.save(any())).thenReturn(user);
        String json = "{\"name\":\"" + userDto.getName() + "\",\"email\":\"" + userDto.getEmail() + "\",\"password\":\"" + userDto.getPassword() + "\"}";
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        verify(userService, times(1)).save(any());
    }

    @Test
    void testUpdateExistingUser() throws Exception {
        String id = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        String json = "{\"name\":\"" + userDto.getName() + "\",\"email\":\"" + userDto.getEmail() + "\",\"password\":\"" + userDto.getPassword() + "\"}";
        mockMvc.perform(put(BASE_URL + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(userService, times(1)).update(any(), eq(id));
    }

    @Test
    void testUpdateNotExistingCompany() throws Exception {
        String id = "511";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        String json = "{\"name\":\"" + userDto.getName() + "\",\"email\":\"" + userDto.getEmail() + "\",\"password\":\"" + userDto.getPassword() + "\"}";
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