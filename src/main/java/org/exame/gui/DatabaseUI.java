package org.exame.gui;

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
    private TextField NomeCampo;

    @FXML
    private TextField CPFCampo;

    //Botões
    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnBuscar;

    //Declaração do objeto candidatoDAO
    private final AlunoDAO alunoDAO = new AlunoDAO();



}
