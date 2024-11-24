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
            stmt.setString(2, aluno.getCpf());
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
        String sql = "UPDATE Aluno SET nome = ?, cpf = ?, peso = ?, altura = ?, dataNascimento = ? WHERE aluno_ID = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setDouble(3, aluno.getPeso());
            stmt.setDouble(4, aluno.getAltura());
            stmt.setString(5, aluno.getDataNascimento());
            stmt.setInt(6, aluno.getAluno_ID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a tabela candidato: ", e);
        }
    }

    //Método para deletar um aluno do banco de dados
    public void deletar(Aluno aluno){
        String sql = "DELETE FROM Aluno WHERE aluno_ID = ? OR nome = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setInt(1, aluno.getAluno_ID());
            stmt.setString(2, aluno.getNome());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Buscar aluno pelo ID
    public Aluno buscarPorID(int id) {
        String sql = "SELECT * FROM Aluno WHERE aluno_ID = ?";
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
        List<Aluno> alunos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM Aluno WHERE nome LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + nome + "%"); // A busca deve ser insensível a maiúsculas e minúsculas
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setAluno_ID(resultSet.getInt("aluno_ID"));
                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setPeso(resultSet.getDouble("peso"));
                    aluno.setAltura(resultSet.getDouble("altura"));
                    aluno.setDataNascimento(resultSet.getString("dataNascimento"));
                    alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    // Listar todos os alunos
    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno"; // Ajuste o nome da tabela conforme necessário
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()){
                Aluno aluno = new Aluno();
                aluno.setAluno_ID(rs.getInt("aluno_ID"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setPeso(rs.getDouble("peso"));
                aluno.setAltura(rs.getDouble("altura"));
                aluno.setDataNascimento(rs.getString("dataNascimento"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    // Metodo auxiliar para mapear o Aluno
    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setAluno_ID(rs.getInt("aluno_ID"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));
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
                aluno.setAluno_ID(rs.getInt("aluno_ID"));
                aluno.setNome(rs.getString("nome"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os alunos: ", e);
        }

        return alunos;
    }



}
