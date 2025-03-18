package br.com.fiap.banco.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.banco.model.CadastroConta;
import br.com.fiap.banco.service.bancoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/conta")
public class CadastroContaController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private List<CadastroConta> repositorio = new ArrayList<>();

    @Autowired
    private bancoService BancoService;

    @GetMapping
    public List<CadastroConta> index() {
        return repositorio;
    }

    @PostMapping
    public ResponseEntity<CadastroConta> create(@RequestBody CadastroConta cadastroConta) {
        log.info("Cadastro concluido de sua conta " + cadastroConta.getNome());
        repositorio.add(cadastroConta);
        return ResponseEntity.status(201).body(cadastroConta);
    }

    // Depósito
    @PostMapping("/{cpf}/depositar")
    public ResponseEntity<CadastroConta> depositar(@PathVariable int cpf, @RequestParam double valor) {
        try {
            // Chamando o método depositar a partir da instância de bancoService
            CadastroConta contaAtualizada = BancoService.depositar(cpf, valor);
            return ResponseEntity.ok(contaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Saque
    @PostMapping("/{cpf}/sacar")
    public ResponseEntity<CadastroConta> sacar(@PathVariable int cpf, @RequestParam double valor) {
        try {
            CadastroConta contaAtualizada = BancoService.sacar(cpf, valor);
            return ResponseEntity.ok(contaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Pix
    @PostMapping("/{cpfOrigem}/transferir/{cpfDestino}")
    public ResponseEntity<CadastroConta> transferir(@PathVariable int cpfOrigem, @PathVariable String cpfDestino,
            @RequestParam double valor) {
        try {
            CadastroConta contaDestino = BancoService.transferir(cpfOrigem, cpfDestino, valor);
            return ResponseEntity.ok(contaDestino);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}