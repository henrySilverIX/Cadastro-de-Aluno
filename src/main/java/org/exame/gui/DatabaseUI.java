package org.exame.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.exame.dao.AlunoDAO;
import org.exame.modelo.Aluno;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.io.FileWriter;


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
    private TextField alunoIDCampo, nomeCampo, CPFCampo, pesoCampo, alturaCampo, dataNascimentoCampo, BuscarCampo;



    //Botões
    @FXML
    private Button btnCadastrar, btnEditar, btnDeletar, btnBuscar, btnIMC, btnImprimir, btnLimpar;


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
            novo_aluno.setCpf(CPFCampo.getText());
            novo_aluno.setPeso(Double.parseDouble(pesoCampo.getText()));
            novo_aluno.setAltura(Double.parseDouble(alturaCampo.getText()));
            novo_aluno.setDataNascimento(dataNascimentoCampo.getText());

            if (nomeCampo.getText().isBlank()) {
                showAlert("Erro", "O campo Nome está vazio!");
            } else {
                alunoDAO.cadastrar(novo_aluno);
                showAlert("Sucesso", "Aluno cadastrado com sucesso!");
                limparCampos();
                atualizarTabela();
            }
        }catch(NumberFormatException e){
            showAlert("Erro", "Certifique-se de que CPF, Peso e Altura estão no formato correto!");
        }
    }


    @FXML
    private void Atualizar() {
        try {
            // Obter o ID do aluno
            int alunoID = Integer.parseInt(alunoIDCampo.getText());
            Aluno alunoExistente = alunoDAO.buscarPorID(alunoID);

            if (alunoExistente == null) {
                showAlert("Erro", "Aluno com ID fornecido não encontrado.");
                return;
            }

            // Atualizar apenas os campos modificados
            String nome = nomeCampo.getText().isBlank() ? alunoExistente.getNome() : nomeCampo.getText();
            String cpf = CPFCampo.getText().isBlank() ? alunoExistente.getCpf() : CPFCampo.getText();
            double peso = pesoCampo.getText().isBlank() ? alunoExistente.getPeso() : Double.parseDouble(pesoCampo.getText());
            double altura = alturaCampo.getText().isBlank() ? alunoExistente.getAltura() : Double.parseDouble(alturaCampo.getText());
            String dataNascimento = dataNascimentoCampo.getText().isBlank() ? alunoExistente.getDataNascimento() : dataNascimentoCampo.getText();

            // Atualizar os valores no objeto
            alunoExistente.setNome(nome);
            alunoExistente.setCpf(cpf);
            alunoExistente.setPeso(peso);
            alunoExistente.setAltura(altura);
            alunoExistente.setDataNascimento(dataNascimento);

            // Chamar o DAO para salvar as alterações
            alunoDAO.atualizar(alunoExistente);
            showAlert("Sucesso", "Aluno atualizado com sucesso!");
            limparCampos();
            atualizarTabela();

        } catch (NumberFormatException e) {
            showAlert("Erro", "Certifique-se de que os campos numéricos estão no formato correto!");
        } catch (Exception e) {
            showAlert("Erro", "Erro ao atualizar o aluno: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }



    //Metodo deletar
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
            alunoParaDeletar.setAluno_ID(alunoID);

            // Chamar o metodo de deletar
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





    //Metodo buscar
    @FXML
    private void Buscar() {
        String filtro = BuscarCampo.getText().trim(); // Filtra por nome ou ID

        if (!filtro.isEmpty()) {
            System.out.println("Filtro de busca: " + filtro); // Depuração para ver o filtro inserido
            List<Aluno> alunos;

            try {
                // Tenta buscar por ID
                int id = Integer.parseInt(filtro);
                Aluno aluno = alunoDAO.buscarPorID(id); // Método que busca por ID

                if (aluno != null) {
                    carregarTabela(List.of(aluno)); // Se encontrou um aluno, carrega a tabela com ele
                } else {
                    showAlert("Atenção", "Nenhum aluno encontrado com o ID especificado.");
                }

            } catch (NumberFormatException e) {
                // Caso não consiga converter para ID, busca por nome
                alunos = alunoDAO.buscarPorNome(filtro); // Buscar por nome

                // Depuração para garantir que a lista de alunos não está duplicada
                System.out.println("Alunos encontrados: " + alunos.size());
                carregarTabela(alunos); // Carregar a tabela com os resultados
            }
        } else {
            // Caso o campo de busca esteja vazio, mostra todos os alunos
            List<Aluno> alunos = alunoDAO.listarTodos(); // Chama o método para listar todos os alunos
            carregarTabela(alunos); // Carrega todos os alunos na tabela
        }
    }






    //Inicializar a tabela
    @FXML
    public void initialize() {
        colunaID.setCellValueFactory(new PropertyValueFactory<>("aluno_ID"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colunaAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));


        // Adicionar listener para selecionar linha e preencher campos
        contentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                preencherCampos(newSelection);
            }
        });
    }

    private void preencherCampos(Aluno aluno) {
        alunoIDCampo.setText(String.valueOf(aluno.getAluno_ID()));
        nomeCampo.setText(aluno.getNome());
        CPFCampo.setText(String.valueOf(aluno.getCpf()));
        pesoCampo.setText(String.valueOf(aluno.getPeso()));
        alturaCampo.setText(String.valueOf(aluno.getAltura()));
        dataNascimentoCampo.setText(aluno.getDataNascimento());
    }

    private void carregarTabela(List<Aluno> alunos) {
        // Cria uma ObservableList a partir da lista de alunos
        ObservableList<Aluno> alunosObservable = FXCollections.observableArrayList(alunos);

        // Vincula a ObservableList à TableView
        contentTable.setItems(alunosObservable);
        contentTable.getItems().clear(); // Limpa os itens anteriores
        contentTable.getItems().addAll(alunos); // Adiciona os novos alunos
        System.out.println("Tabela carregada com " + alunos.size() + " alunos");

    }


    @FXML
    private void limparCampos() {
        alunoIDCampo.clear();
        nomeCampo.clear();
        CPFCampo.clear();
        pesoCampo.clear();
        alturaCampo.clear();
        dataNascimentoCampo.clear();
        BuscarCampo.clear();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void atualizarTabela() {
        // Implemente este metodo para atualizar os dados da tabela contentTable
        List<Aluno> alunos = alunoDAO.buscarTodos(); // Supondo que exista um metodo buscarTodos()
        contentTable.getItems().clear();
        contentTable.getItems().addAll(alunos);
    }



    //Botão para sair da aplicação do banco de dados
    @FXML
    private void Sair() {
        System.exit(0);
    }


    //Parte envolvendo os arquivos de texto


    // Metodo para calcular o IMC
    @FXML
    private void CalcularIMC() {
        try {
            double peso = Double.parseDouble(pesoCampo.getText());
            double altura = Double.parseDouble(alturaCampo.getText());
            double imc = peso / (altura * altura);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cálculo do IMC");
            alert.setHeaderText("IMC Calculado com Sucesso!");
            alert.setContentText("O IMC do aluno é: " + String.format("%.2f", imc));
            alert.showAndWait();
        } catch (NumberFormatException e) {
            showError("Erro ao calcular o IMC", "Certifique-se de preencher os campos de peso e altura corretamente.");
        }
    }


    // Metodo para exibir mensagens de erro
    private void showError(String title, String message) {
        //Alert faz parte do java.scene.control
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Metodo para gerar um arquivo de texto com os dados do aluno
    @FXML
    private void Imprimir() {

        //Cria uma classe do tipo Aluno que será impresso depois que ele for pesquisado na barra de busca
        //O usuário deve selecionar a linha na tabela para poder imprimir os dados do aluno em um arquivo de texto
        Aluno alunoSelecionado = contentTable.getSelectionModel().getSelectedItem();

        //O objeto = "alunoSelecionado" do tipo Aluno não pode ser nulo. Caso contrário, uma mensagem de erro
        //irá aparecer na tela, pedindo para que o usuário selecione um aluno
        if (alunoSelecionado == null) {
            showError("Nenhum aluno selecionado", "Por favor, selecione um aluno na tabela.");
            return;
        }

        //FileChooser é uma classe que pertence a esse pacote javafx.stage.FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Dados do Aluno");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de Texto", "*.txt"));

        //A classe File pertence ao pacote java.io.File
        //Essa linha é responsável por abrir uma janela do sistema operacional, onde o usuário poderá selecionar o local
        //que será salvo o seu arquivo e também poderá nomeá-lo
        File file = fileChooser.showSaveDialog(btnImprimir.getScene().getWindow());


        //Se o objeto do tipo File não for nulo, ou seja, se uma linha na tabela estiver selecionada, esse código abaixo
        //irá ser executado
        if (file != null) {
            //Aqui é declarado um objeto do tipo FileWriter responsável por escrever os dados no arquivo .txt
            try (FileWriter escritor = new FileWriter(file)) {
                escritor.write("Dados do Aluno:\n");
                escritor.write("ID: " + alunoSelecionado.getAluno_ID() + "\n");
                escritor.write("Nome: " + alunoSelecionado.getNome() + "\n");
                escritor.write("CPF: " + alunoSelecionado.getCpf() + "\n");
                escritor.write("Peso: " + alunoSelecionado.getPeso() + "\n");
                escritor.write("Altura: " + alunoSelecionado.getAltura() + "\n");
                escritor.write("Data de Nascimento: " + alunoSelecionado.getDataNascimento() + "\n");

                // Calculando o IMC
                //Nessa parte será calculado o IMC do aluno usando o metodo IMC() presente na classe Aluno.java
                double imc = alunoSelecionado.IMC();
                escritor.write("IMC: " + String.format("%.2f", imc) + "\n");

                //Nessa parte está a interpretação do IMC, classificando o aluno de acordo com seu índice de massa corpórea
                if(imc < 16.0){
                    escritor.write("Seu IMC está abaixo do peso (Severo)");
                }
                else if(imc >= 16.0 && imc < 16.9){
                    escritor.write("Seu IMC está abaixo do peso (Moderado)");
                }
                else if(imc >= 17.0 && imc < 18.4){
                    escritor.write("Seu IMC está abaixo do peso (Leve)");
                }
                else if(imc >= 18.5 && imc < 24.9){
                    escritor.write("Seu IMC está normal");
                }
                else if(imc >= 25.0 && imc < 29.9){
                    escritor.write("Seu IMC está acima do peso (Pré-obesidade)");
                }
                else if(imc >= 30.0 && imc < 34.9){
                    escritor.write("Seu IMC está acima do peso (Obesidade Grau I)");
                }
                else if(imc >= 35.0 && imc < 39.9){
                    escritor.write("Seu IMC está acima do peso (Obesidade Grau II)");
                }
                else{
                    escritor.write("Seu IMC está acima do peso (Obesidade Grau III)");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Arquivo Gerado");
                alert.setHeaderText("Arquivo salvo com sucesso!");
                alert.setContentText("Os dados do aluno foram salvos em: " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                showError("Erro ao salvar o arquivo", "Ocorreu um erro ao tentar salvar o arquivo.");
            }
        }
    }

}
