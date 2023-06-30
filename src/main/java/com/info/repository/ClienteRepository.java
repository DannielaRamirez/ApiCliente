package com.info.repository;

import com.info.model.Cliente;
import com.info.model.ClienteDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    @Query("SELECT new com.info.model.ClienteDto" +
            "(c.primerNombre, c.segundoNombre, c.primerApellido, c.segundoApellido, c.telefono, c.direccion, c.ciudadResidencia) " +
            "FROM Cliente c WHERE c.documento = :documento")
    ClienteDto findClientByDocumento(@Param("documento") String documento);
}
