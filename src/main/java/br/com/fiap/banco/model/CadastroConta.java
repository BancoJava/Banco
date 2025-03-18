package br.com.fiap.banco.model;

import java.util.Date;

public class CadastroConta {

    private Long numeroAgencia;
    private Long id;
    private String nome;
    private int cpf;
    private Date date;
    private double saldoInicial;
    private double valor;
    private boolean ativa;
    private String tipo;

    public Long getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(Long numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public Long getId(Long id) {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CadastroConta(Long numeroAgencia, Long id, String nome, int cpf, Date date, double saldoInicial,
            double valor,
            boolean ativa, String tipo) {
        this.numeroAgencia = numeroAgencia;
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.date = date;
        this.saldoInicial = saldoInicial;
        this.valor = valor;
        this.ativa = ativa;
        this.tipo = tipo;

    }

    // Depositar
    public void depositar(double Valor) {
        saldoInicial += Valor;
    }

    // Sacar
    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldoInicial) {
            return false;
        } else
            saldoInicial -= valor;
        return true;
    }

    // Pix
    public boolean transferir(CadastroConta contaTransfe, double valor) {
        if (this.sacar(valor)) {
            contaTransfe.depositar(valor);
            return true;
        }
        return false;

    }

}
