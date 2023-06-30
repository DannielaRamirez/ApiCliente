package com.info.controller;

import com.info.execption.BadRequestException;
import com.info.execption.ExceptionHandlerUtil;
import com.info.execption.NotFoundException;
import com.info.model.ClientRequest;
import com.info.model.ClienteDto;
import com.info.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final HttpServletRequest request;

    @Autowired
    public ClienteController(ClienteService clienteService, HttpServletRequest request) {
        this.clienteService = clienteService;
        this.request = request;
    }

    @PostMapping
    public ResponseEntity<?> findByDocument(@RequestBody ClientRequest clientRequest) {
        try {
            ClienteDto clienteDto = clienteService.findClientByDocumento(clientRequest);
            return ResponseEntity.ok(clienteDto);
        } catch (BadRequestException e) {
            return ExceptionHandlerUtil.handleException(HttpStatus.BAD_REQUEST, e, request);
        } catch (NotFoundException e) {
            return ExceptionHandlerUtil.handleException(HttpStatus.NOT_FOUND, e, request);
        } catch (Exception e) {
            return ExceptionHandlerUtil.handleException(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
        }
    }
}