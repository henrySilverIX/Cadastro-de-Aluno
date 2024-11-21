# Cadastro de Aluno
Cadastro de alunos (CRUD) usando Java


# Configurações para esse projeto

Aqui estão algumas configurações necessárias para esse projeto:

## Configuração para o JavaFX
Para configurar o JavaFX, primeiro devemos baixá-lo. Para esse projeto, eu usei o JavaFX SDK 22. É possível fazer o
download nesse site: <br>
https://jdk.java.net/javafx23/ <br>

Após baixar o arquivo .zip, coloque a pasta em qualquer lugar que desejar em seu computador.
No IntelliJ IDEA, siga o seguinte caminho: <br>
Run → Edit Configurations... <br>
Uma nova janela irá se abrir. Nessa janela, clique no símbolo de mais para adicionar uma nova configuração. Nessa
opção, seleciona a primeira (Application).
Nessa nova opção, dê um nome para essa nova configuração. Depois disso clique na opção:
**Modify options** e clique em **Add VM options.** Um novo campo irá aparecer chamado *VM Options.* Nesse campo,
coloque a seguinte configuração: <br>
**--module-path "caminho do javaFX no seu computador" --add-modules javafx.controls,javafx.fxml** <br>
Após isso, é só clicar em **Apply** e sair dessa janela. <br>

