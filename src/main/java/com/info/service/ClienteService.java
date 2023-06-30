package com.info.service;

import com.info.execption.BadRequestException;
import com.info.execption.NotFoundException;
import com.info.model.ClientRequest;
import com.info.model.ClienteDto;
import com.info.repository.ClienteRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository repository;
    @Autowired
    public ClienteService(ClienteRepository repository){
        this.repository= repository;
    }

    public ClienteDto findClientByDocumento(ClientRequest clientRequest) {
        String tipoDocumento = clientRequest.getTipoDocumento();
        String documento = clientRequest.getDocumento();
        validateInput(tipoDocumento, documento);
        ClienteDto clienteDto = repository.findClientByDocumento(documento);
        if (clienteDto == null) {
            log.error("No se encontró un cliente con documento: {}", documento);
            throw new NotFoundException("Cliente no encontrado");
        }
        return clienteDto;
    }

    private void validateInput(String tipoDocumento, String documento) {
        if (tipoDocumento == null || documento == null) {
            throw new BadRequestException("Tipo de documento y número de documento son obligatorios");
        }
        if (!tipoDocumento.equals("C") && !tipoDocumento.equals("P")) {
            throw new BadRequestException("Tipo de documento no válido");
        }
    }
}
