package org.exame.gui;

import javafx.scene.control.cell.PropertyValueFactory;
import org.exame.dao.AlunoDAO;
import org.exame.modelo.Aluno;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;


public class DatabaseUI {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Adicionando variáveis do banco de dados (textField, buttons etc)
    @FXML
    private Label labelCandidato;


    //Campos de texto
    @FXML
    private TextField alunoIDCampo;

    @FXML
    private TextField nomeCampo;

    @FXML
    private TextField CPFCampo;

    @FXML
    private TextField pesoCampo;

    @FXML
    private TextField alturaCampo;

    @FXML
    private TextField dataNacimentoCampo;


    //Botões
    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnBuscar;

    //Elementos da tabela
    @FXML
    private TableView<Aluno> contentTable;

    @FXML
    private TableColumn<Aluno, Integer> colunaID;
    @FXML
    private TableColumn<Aluno, String> colunaNome;
    @FXML
    private TableColumn<Aluno, Integer> colunaCPF;
    @FXML
    private TableColumn<Aluno, Double> colunaPeso;
    @FXML
    private TableColumn<Aluno, Double> colunaAltura;
    @FXML
    private TableColumn<Aluno, String> colunaDataNascimento;

    //Declaração do objeto candidatoDAO
    private final AlunoDAO alunoDAO = new AlunoDAO();


    @FXML
    private void Cadastrar() {
        try {
            Aluno novo_aluno = new Aluno();
            novo_aluno.setNome(nomeCampo.getText());
            novo_aluno.setCpf(Double.parseDouble(CPFCampo.getText()));
            novo_aluno.setPeso(Double.parseDouble(pesoCampo.getText()));
            novo_aluno.setAltura(Double.parseDouble(alturaCampo.getText()));
            novo_aluno.setDataNascimento(dataNacimentoCampo.getText());

            if (nomeCampo.getText().isBlank()) {
                showAlert("Erro", "O campo Nome está vazio!");
            } else {
                alunoDAO.cadastrar(novo_aluno);
                showAlert("Sucesso", "Candidato cadastrado com sucesso!");
                limparCampos();
            }
        }catch(NumberFormatException e){
            showAlert("Erro", "Certifique-se de que CPF, Peso e Altura estão no formato correto!");
        }
    }

    @FXML
    private void Deletar() {
        // Verificar se o campo de ID está preenchido
        if (alunoIDCampo.getText().isBlank()) {
            showAlert("Erro", "Por favor, insira o ID do Aluno para deletar.");
            return;
        }

        try {
            // Converte o ID do aluno de String para inteiro
            int alunoID = Integer.parseInt(alunoIDCampo.getText());

            // Criar um objeto Aluno com o ID fornecido
            Aluno alunoParaDeletar = new Aluno();
            alunoParaDeletar.setID_Aluno(alunoID);

            // Chamar o método de deletar
            alunoDAO.deletar(alunoParaDeletar);

            // Mostrar mensagem de sucesso
            showAlert("Sucesso", "Aluno deletado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (NumberFormatException e) {
            showAlert("Erro", "ID do Aluno deve ser um número válido.");
        } catch (Exception e) {
            showAlert("Erro", "Erro ao deletar o aluno: " + e.getMessage());
        }
    }


    //Método atualizar
    @FXML
    private void Atualizar() {
        try {
            Aluno alunoAtualizado = new Aluno();
            alunoAtualizado.setID_Aluno(Integer.parseInt(alunoIDCampo.getText()));
            alunoAtualizado.setNome(nomeCampo.getText());
            alunoAtualizado.setCpf(Integer.parseInt(CPFCampo.getText()));
            alunoAtualizado.setPeso(Double.parseDouble(pesoCampo.getText()));
            alunoAtualizado.setAltura(Double.parseDouble(alturaCampo.getText()));
            alunoAtualizado.setDataNascimento(dataNacimentoCampo.getText());

            alunoDAO.atualizar(alunoAtualizado);
            showAlert("Sucesso", "Aluno atualizado com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            showAlert("Erro", "Verifique os dados inseridos!");
        }
    }


    //Método buscar
    @FXML
    private void Buscar() {
        String filtro = alunoIDCampo.getText().trim(); // Filtrar por ID ou nome
        List<Aluno> alunos;

        if (!filtro.isEmpty()) {
            try {
                int id = Integer.parseInt(filtro);
                Aluno aluno = alunoDAO.buscarPorID(id); // Implementar no DAO
                if (aluno != null) {
                    carregarTabela(List.of(aluno));
                } else {
                    showAlert("Atenção", "Nenhum aluno encontrado com o ID especificado.");
                }
            } catch (NumberFormatException e) {
                alunos = alunoDAO.buscarPorNome(filtro); // Implementar no DAO
                carregarTabela(alunos);
            }
        } else {
            alunos = alunoDAO.listarTodos(); // Implementar no DAO
            carregarTabela(alunos);
        }
    }


    //Inicializar a tabela
    @FXML
    public void initialize() {
        colunaID.setCellValueFactory(new PropertyValueFactory<>("ID_Aluno"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colunaAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
    }

    private void carregarTabela(List<Aluno> alunos) {
        contentTable.getItems().clear();
        contentTable.getItems().addAll(alunos);
    }


    private void limparCampos() {
        nomeCampo.clear();
        CPFCampo.clear();
        pesoCampo.clear();
        alturaCampo.clear();
        dataNacimentoCampo.clear();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }





    private void atualizarTabela() {
        // Implemente este método para atualizar os dados da tabela contentTable
        List<Aluno> alunos = alunoDAO.buscarTodos(); // Supondo que exista um método buscarTodos()
        contentTable.getItems().clear();
        contentTable.getItems().addAll(alunos);
    }



    //Botão para sair da aplicação do banco de dados
    @FXML
    private void Sair() {
        System.exit(0);
    }



}
