package com.exemplo.banco.servico.impl;

import com.exemplo.banco.modelo.Cliente;
import com.exemplo.banco.repositorio.ClienteRepositorio;
import com.exemplo.banco.servico.ClienteServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicoImpl implements ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public Cliente criarCliente(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        return clienteRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Override
    public void deletarCliente(Long id) {
        Cliente cliente = buscarClientePorId(id);
        if (cliente.getContas() != null && !cliente.getContas().isEmpty()) {
            throw new RuntimeException("Não é possível deletar cliente com contas vinculadas");
        }
        clienteRepositorio.deleteById(id);
    }
}