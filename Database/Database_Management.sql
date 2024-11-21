create database secondExam;
use secondExam;
show databases;

create table Aluno(
	aluno_ID int primary key auto_increment,
    nome varchar(30),
    cpf double,
    peso double,
    altura double,
    dataNascimento varchar(12)
);



SELECT * FROM Aluno;

DROP TABLE Aluno;

