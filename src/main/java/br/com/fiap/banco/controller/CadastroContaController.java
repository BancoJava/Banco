package br.com.fiap.banco.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.banco.model.CadastroConta;
import br.com.fiap.banco.service.bancoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("{id}")
    public Long get(@PathVariable Long id) {
        log.info("Buscando categoria " + id);
        return get(id);
    }

    @PostMapping
    public ResponseEntity<CadastroConta> create(@RequestBody CadastroConta cadastroConta) {
        log.info("Cadastro concluido de sua conta " + cadastroConta.getNome());
        repositorio.add(cadastroConta);
        return ResponseEntity.status(201).body(cadastroConta);
    }

    // Encerrar
    @PutMapping("/encerrar/{id}")
    public ResponseEntity<String> encerrarConta(@PathVariable Long id) {
        // Simulando um repositório em memória (por exemplo, um mapa)
        Map<Long, CadastroConta> contas = new HashMap<>(); // Apenas para exemplo

        CadastroConta conta = contas.get(id); // Busca a conta pelo id (sem usar findById)

        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Conta não encontrada.");
        }

        // Marca a conta como inativa
        conta.setAtiva(false);

        // Neste exemplo, como estamos em memória, não há necessidade de "salvar"
        // O estado da conta já foi alterado.

        return ResponseEntity.ok("Conta encerrada com sucesso.");
    }

    // Depósito
    @PostMapping("/{cpf}/depositar")
    public ResponseEntity<CadastroConta> depositar(@PathVariable int cpf, @RequestParam double valor) {
        try {
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
    public ResponseEntity<CadastroConta> transferir(@PathVariable int cpfOrigem, @PathVariable int cpfDestino,
            @RequestParam double valor) {
        try {
            CadastroConta contaDestino = BancoService.transferir(cpfOrigem, cpfDestino, valor);
            return ResponseEntity.ok(contaDestino);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private CadastroConta getCadastro(Long id) {
        return repositorio.stream()
                .filter(c -> c.getId(id).equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Categoria " + id + "  não encontrada"));
    }
}
