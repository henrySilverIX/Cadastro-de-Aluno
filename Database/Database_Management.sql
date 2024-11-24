create database secondExam;
use secondExam;
show databases;

create table Aluno(
	aluno_ID int primary key auto_increment,
    nome varchar(50),
    cpf varchar(14),
    peso double,
    altura double,
    dataNascimento varchar(12)
);



SELECT * FROM Aluno;

DROP TABLE Aluno;

TRUNCATE TABLE Aluno;

