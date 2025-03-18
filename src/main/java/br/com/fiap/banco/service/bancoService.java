package br.com.fiap.banco.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.banco.model.CadastroConta;

@Service
public class bancoService {

    private List<CadastroConta> repositorio = new ArrayList<>();

    private CadastroConta save(CadastroConta conta) {

        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    // Depósito
    public CadastroConta depositar(int cpf, double valor) {
        CadastroConta conta = findById(cpf);
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo");
        }
        conta.depositar(valor);
        return save(conta);
    }

    // Saque
    public CadastroConta sacar(int cpf, double valor) {

        CadastroConta conta = findById(cpf);

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo");
        }

        if (!conta.sacar(valor)) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        return save(conta);
    }

    // Pix
    public CadastroConta transferir(int cpfOrigem, int cpfDestino, double valor) {

        CadastroConta contaOrigem = findById(cpfOrigem);

        CadastroConta contaDestino = findById(cpfDestino);

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo");
        }

        if (!contaOrigem.transferir(contaDestino, valor)) {
            throw new IllegalArgumentException("Saldo insuficiente para a transferência");
        }

        save(contaOrigem);
        save(contaDestino);
        return contaDestino;
    }

    private CadastroConta findById(int cpf) {

        for (CadastroConta conta : repositorio) {
            if (conta.getCpf() == cpf) {
                return conta;
            }
        }
        throw new IllegalArgumentException("Conta com CPF " + cpf + " não encontrada");
    }
}
