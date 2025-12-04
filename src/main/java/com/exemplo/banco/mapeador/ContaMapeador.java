package com.exemplo.banco.mapeador;

import com.exemplo.banco.dto.ContaDTO;
import com.exemplo.banco.modelo.Conta;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import org.springframework.stereotype.Component;

@Component
public class ContaMapeador {

    public ContaDTO paraDTO(Conta conta) {
        if (conta == null) {
            return null;
        }
        return new ContaDTO(
            conta.getId(),
            conta.getNumeroConta(),
            conta.getSaldo(),
            conta.getTipoConta() != null ? conta.getTipoConta().name() : null,
            conta.getCliente() != null ? conta.getCliente().getId() : null,
            conta.getCliente() != null ? conta.getCliente().getNome() : null
        );
    }

    public List<ContaDTO> paraDTOList(List<Conta> contas) {
        if (contas == null || contas.isEmpty()) {
            return Collections.emptyList();
        }
        return contas.stream()
                     .map(this::paraDTO)
                     .collect(Collectors.toList());
    }

    public Conta paraEntidade(ContaDTO dto) {
        if (dto == null) {
            return null;
        }
        Conta conta = new Conta();
        conta.setId(dto.getId());
        conta.setNumeroConta(dto.getNumeroConta());
        conta.setSaldo(dto.getSaldo());
        // TipoConta e Cliente devem ser setados separadamente conforme o contexto
        return conta;
    }
}