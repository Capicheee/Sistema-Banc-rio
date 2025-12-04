package com.exemplo.banco.servico;

import com.exemplo.banco.modelo.Cliente;
import java.util.List;

public interface ClienteServico {
    Cliente criarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Cliente buscarClientePorId(Long id);
    void deletarCliente(Long id);
}