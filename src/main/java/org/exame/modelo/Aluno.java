package org.exame.modelo;

import org.exame.modelo.Aluno;


public class Aluno {
    //Atributos
    private int aluno_ID;
    private String nome;
    private String cpf;
    private double peso;
    private double altura;
    private String dataNascimento;


    //Constructor
    public Aluno() {

    }

    public Aluno(String nome, String cpf, double peso, double altura) {
        this.nome = nome;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
    }

    //Métodos get and set
    //ID
    public int getAluno_ID() {
        return aluno_ID;
    }

    public void setAluno_ID(int aluno_ID) {
        this.aluno_ID = aluno_ID;
    }


    //Nome
    public String getNome() {
        return nome;
    }
    public void setNome(String Nome) {
        this.nome = Nome;
    }

    //CPF
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    //Peso
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    //Altura
    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    //Data de Nascimento
    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    //Outros métodos
    public double IMC(){
        return peso/(altura*altura);
    }

}