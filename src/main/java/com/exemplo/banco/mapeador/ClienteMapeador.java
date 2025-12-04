package com.exemplo.banco.mapeador;

import com.exemplo.banco.modelo.Cliente;
import com.exemplo.banco.dto.ClienteDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapeador {

    public ClienteDTO paraDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setEmail(cliente.getEmail());
        return clienteDTO;
    }

    public Cliente paraModelo(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        return cliente;
    }
}