package com.exemplo.banco.controlador;

import com.exemplo.banco.dto.ContaDTO;
import com.exemplo.banco.modelo.Conta;
import com.exemplo.banco.modelo.Cliente;
import com.exemplo.banco.modelo.TipoConta;
import com.exemplo.banco.repositorio.ContaRepositorio;
import com.exemplo.banco.repositorio.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContaControladorTeste {

    @Mock
    private ContaRepositorio contaRepo;

    @Mock
    private ClienteRepositorio clienteRepo;

    @InjectMocks
    private ContaControlador contaControlador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListar() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setNumeroConta("12345");
        conta.setSaldo(1000.0);
        conta.setTipoConta(TipoConta.CONTA_CORRENTE);
        conta.setCliente(cliente);
        
        when(contaRepo.findAll()).thenReturn(Collections.singletonList(conta));

        List<ContaDTO> result = contaControlador.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).getNumeroConta());
        assertEquals("João Silva", result.get(0).getClienteNome());
        verify(contaRepo, times(1)).findAll();
    }

    @Test
    void testCriar() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");

        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setNumeroConta("12345");
        conta.setSaldo(1000.0);
        conta.setTipoConta(TipoConta.CONTA_CORRENTE);
        conta.setCliente(cliente);
        when(contaRepo.save(any(Conta.class))).thenReturn(conta);

        Map<String, Object> payload = new HashMap<>();
        payload.put("numeroConta", "12345");
        payload.put("saldo", "1000.0");
        payload.put("tipoConta", "CONTA_CORRENTE");
        payload.put("clienteId", "1");

        ResponseEntity<?> response = contaControlador.criar(payload);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ContaDTO);
        ContaDTO dto = (ContaDTO) response.getBody();
        assertEquals("12345", dto.getNumeroConta());
        assertEquals("João", dto.getClienteNome());
        verify(contaRepo, times(1)).save(any(Conta.class));
        verify(clienteRepo, times(1)).findById(1L);
    }

    @Test
    void testCriarClienteNaoEncontrado() {
        when(clienteRepo.findById(1L)).thenReturn(Optional.empty());

        Map<String, Object> payload = new HashMap<>();
        payload.put("numeroConta", "12345");
        payload.put("saldo", "1000.0");
        payload.put("tipoConta", "CONTA_CORRENTE");
        payload.put("clienteId", "1");

        ResponseEntity<?> response = contaControlador.criar(payload);

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        verify(contaRepo, never()).save(any(Conta.class));
    }
}