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
        String sql = "INSERT INTO Aluno (nome, cpf, peso, altura, dataNascimento) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, aluno.getNome());
            stmt.setDouble(2, aluno.getCpf());
            stmt.setDouble(3, aluno.getPeso());
            stmt.setDouble(4, aluno.getAltura());
            stmt.setString(5, aluno.getDataNascimento());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Método para atualizar ou modificar os itens do banco de dados - Atualizar
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE Aluno SET nome = ? WHERE aluno_ID = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, aluno.getID_Aluno());
            stmt.setString(2, aluno.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a tabela candidato: ", e);
        }
    }

    //Método para deletar um
    public void deletar(Aluno aluno){
        String sql = "DELETE FROM Aluno WHERE aluno_ID = ? OR nome = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setInt(1, aluno.getID_Aluno());
            stmt.setString(2, aluno.getNome());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Buscar aluno pelo ID
    public Aluno buscarPorID(int id) {
        String sql = "SELECT * FROM Aluno WHERE ID_Aluno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearAluno(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Buscar alunos pelo nome
    public List<Aluno> buscarPorNome(String nome) {
        String sql = "SELECT * FROM Aluno WHERE nome LIKE ?";
        List<Aluno> alunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alunos;
    }

    // Listar todos os alunos
    public List<Aluno> listarTodos() {
        String sql = "SELECT * FROM Aluno";
        List<Aluno> alunos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alunos;
    }

    // Método auxiliar para mapear o Aluno
    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setID_Aluno(rs.getInt("ID_Aluno"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getInt("cpf"));
        aluno.setPeso(rs.getDouble("peso"));
        aluno.setAltura(rs.getDouble("altura"));
        aluno.setDataNascimento(rs.getString("dataNascimento"));
        return aluno;
    }



    public List<Aluno> buscarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setID_Aluno(rs.getInt("ID_Aluno"));
                aluno.setNome(rs.getString("Nome"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os alunos: ", e);
        }

        return alunos;
    }



}
