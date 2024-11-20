package org.exame.dao;


import org.exame.modelo.Aluno;
import org.exame.factory.ConnectionFactory;
import java.sql.SQLException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class AlunoDAO {
    private final Connection connection;

    //Constructor
    public AlunoDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }


    //Métodos do CRUD
    //Create - Criar
    public void cadastrar(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, peso) VALUES(?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getCpf());
            stmt.setDouble(3, aluno.getPeso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update - Atualizar
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE Aluno SET nome = ? WHERE ID_Aluno = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getID_Aluno());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a tabela candidato: ", e);
        }
    }

    //Delete - deletar
    public void deletar(Aluno aluno){
        String sql = "DELETE FROM Aluno WHERE UniqueID = ? OR nome = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setInt(1, aluno.getID_Aluno());
            stmt.setString(2, aluno.getNome());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





}
