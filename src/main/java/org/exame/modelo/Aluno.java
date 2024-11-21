package org.exame.modelo;

import org.exame.modelo.Aluno;


public class Aluno {
    //Atributos
    private int ID_Aluno;
    private static String nome;
    private double cpf;
    private double peso;
    private double altura;
    private String dataNascimento;


    //Constructor
    public Aluno() {

    }

    public Aluno(String nome, double cpf, double peso, double altura) {
        this.nome = nome;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
    }

    //Métodos get and set
    //ID
    public int getID_Aluno() {
        return ID_Aluno;
    }

    public void setID_Aluno(int ID_Aluno) {
        this.ID_Aluno = ID_Aluno;
    }


    //Nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        Aluno.nome = nome;
    }

    //CPF
    public double getCpf() {
        return cpf;
    }

    public void setCpf(double cpf) {
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