package com.info.controller;

import com.info.execption.BadRequestException;
import com.info.execption.NotFoundException;
import com.info.model.ClienteDto;
import com.info.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ClienteController.class)
@ExtendWith(SpringExtension.class)
public class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void testFindByDocument_ReturnsOk() throws Exception {
        // Configurar el comportamiento del servicio mock
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setPrimerNombre("Daniela");
        clienteDto.setSegundoNombre("Patricia");
        clienteDto.setPrimerApellido("Ramirez");
        clienteDto.setSegundoApellido("Gomez");
        clienteDto.setTelefono("23445322");
        clienteDto.setDireccion("Calle Principal 123");
        clienteDto.setCiudadResidencia("Ciudad de Residencia");
        Mockito.when(clienteService.findClientByDocumento(Mockito.any())).thenReturn(clienteDto);

        // Solicitud POST y verificar respuesta
        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tipoDocumento\": \"C\", \"documento\": \"23445322\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.primerNombre").value("Daniela"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.primerApellido").value("Ramirez"));
    }

    @Test
    public void testFindByDocument_ReturnsBadRequest() throws Exception {
        // Excepción BadRequestException
        Mockito.when(clienteService.findClientByDocumento(Mockito.any())).thenThrow(new BadRequestException("Tipo de documento no válido"));

        // Solicitud POST y verificar respuesta
        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tipoDocumento\": \"X\", \"documento\": \"123456789\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Tipo de documento no válido\"}"));
    }


    @Test
    public void testFindByDocument_ReturnsNotFound() throws Exception {
        // Excepción NotFoundException
        Mockito.when(clienteService.findClientByDocumento(Mockito.any())).thenThrow(new NotFoundException("Cliente no encontrado"));

        // Solicitud POST y verificar de respuesta
        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tipoDocumento\": \"C\", \"documento\": \"123456789\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Cliente no encontrado\"}"));
    }
}

