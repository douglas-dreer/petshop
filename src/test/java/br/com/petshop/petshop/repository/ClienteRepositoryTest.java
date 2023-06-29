package br.com.petshop.petshop.repository;

import br.com.petshop.petshop.entity.ClienteEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.yml")
class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;
    private final List<ClienteEntity> clienteEntityList = Arrays.asList(
            new ClienteEntity(), new ClienteEntity(),
            new ClienteEntity(), new ClienteEntity()
    );

   @Test
    void mustReturnSucces_WhenFindAllByAtivos(){
       clienteRepository.saveAll(clienteEntityList);
       List<ClienteEntity> resultados = clienteRepository.findAllByIsAtivo(true);

       assertFalse(resultados.isEmpty());
   }
}
