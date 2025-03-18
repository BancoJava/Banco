package br.com.fiap.banco.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.banco.model.CadastroConta;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/conta")
public class CadastroContaController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private List<CadastroConta> repositorio = new ArrayList<>();

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

}