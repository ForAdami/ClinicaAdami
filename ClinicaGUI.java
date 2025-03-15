import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ClinicaGUI extends Application {
    private ListaPacientes listaPacientes = new ListaPacientes(100); // Gerencia a lista de pacientes
    private FilaAtendimento filaAtendimento = new FilaAtendimento(50); // Gerencia a fila de atendimento
    private PilhaHistorico historico = new PilhaHistorico(100); // Armazena o histórico de operações

    public static void main(String[] args) {
        launch(args); // Lança a aplicação JavaFX
    };

    @Override
    public void start(Stage primaryStage) {
        // Rótulo inicial da aplicação
        Label label = new Label("Bem-vindo à Clínica Adami!");

        // Botões principais da interface
        Button cadastrarPacienteButton = new Button("Cadastrar Paciente");
        Button adicionarFilaButton = new Button("Adicionar à Fila");
        Button chamarPacienteButton = new Button("Chamar Paciente");
        Button verFilaButton = new Button("Ver Fila");
        Button verHistoricoButton = new Button("Ver Histórico");
        Button desfazerButton = new Button("Desfazer Última Operação");
        Button buscarApagarButton = new Button("Buscar/Apagar Paciente");
        buscarApagarButton.setOnAction(e -> buscarOuApagarPaciente());
        Button sairButton = new Button("Sair");

        // Ações de cada botão, chamadas de métodos específicos
        cadastrarPacienteButton.setOnAction(e -> cadastrarPaciente());
        adicionarFilaButton.setOnAction(e -> adicionarPacienteFila());
        chamarPacienteButton.setOnAction(e -> chamarPaciente());
        verFilaButton.setOnAction(e -> filaAtendimento.exibirFila());
        verHistoricoButton.setOnAction(e -> filaAtendimento.exibirHistorico());
        desfazerButton.setOnAction(e -> desfazerUltimaOperacao());
        buscarApagarButton.setOnAction(e -> buscarOuApagarPaciente());
        sairButton.setOnAction(e -> System.exit(0));

        // Layout vertical para organizar os elementos na tela
        VBox vbox = new VBox(10, label, cadastrarPacienteButton, adicionarFilaButton, chamarPacienteButton,
                verFilaButton, verHistoricoButton, desfazerButton, buscarApagarButton, sairButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Cena principal da aplicação
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Clínica Adami"); // Título da janela
        primaryStage.setScene(scene);
        primaryStage.show(); // Exibe a janela
    }

    // Método para cadastrar um novo paciente
    private void cadastrarPaciente() {
        // Cria uma nova janela para o cadastro de pacientes
        Stage cadastroStage = new Stage();

        // Layout vertical para organizar os campos e o botão
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Campos para entrada de dados do paciente
        Label idLabel = new Label("Número de Identificação (ID):");
        TextField idField = new TextField();

        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();

        Label idadeLabel = new Label("Idade:");
        TextField idadeField = new TextField();

        Label motivoLabel = new Label("Motivo da Consulta:");
        TextField motivoField = new TextField();

        Label emergenciaLabel = new Label("Situação de Emergência (Sim/Não):");
        TextField emergenciaField = new TextField();

        // Botão para salvar o cadastro do paciente
        Button cadastrarButton = new Button("Cadastrar");

        // Define a ação do botão
        cadastrarButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText()); // Converte o ID para inteiro
                if (listaPacientes.buscar(id) != null) { // Verifica se o ID já existe
                    Alert alert = new Alert(Alert.AlertType.ERROR, "ID já cadastrado! Escolha outro.");
                    alert.showAndWait();
                    return;
                }

                // Cria um paciente com os dados inseridos
                String nome = nomeField.getText();
                int idade = Integer.parseInt(idadeField.getText());
                String motivo = motivoField.getText();
                boolean emergencia = emergenciaField.getText().equalsIgnoreCase("Sim");

                Paciente paciente = new Paciente(nome, id, idade, motivo, emergencia);
                listaPacientes.adicionar(paciente); // Adiciona o paciente à lista

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Paciente cadastrado com sucesso!");
                successAlert.showAndWait();
                cadastroStage.close(); // Fecha a janela de cadastro
            } catch (NumberFormatException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "O ID e a idade devem ser números válidos!");
                errorAlert.showAndWait();
            }
        });

        // Adiciona os campos e o botão ao layout
        vbox.getChildren().addAll(idLabel, idField, nomeLabel, nomeField, idadeLabel, idadeField,
                motivoLabel, motivoField, emergenciaLabel, emergenciaField, cadastrarButton);

        // Cria a cena e exibe a janela
        Scene scene = new Scene(vbox, 300, 400);
        cadastroStage.setScene(scene);
        cadastroStage.setTitle("Cadastro de Paciente");
        cadastroStage.show();
    }

    // Método para buscar ou apagar um paciente
    private void buscarOuApagarPaciente() {
        // Cria um diálogo para entrada de ID ou nome do paciente
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar/Apagar Paciente");
        dialog.setHeaderText("Digite o ID ou Nome do paciente:");
        dialog.setContentText("ID ou Nome:");

        // Exibe o diálogo e processa o que o usuário insere
        dialog.showAndWait().ifPresent(input -> {
            Paciente[] resultado = new Paciente[1]; // Usar um array para armazenar o paciente encontrado

            try {
                int id = Integer.parseInt(input); // Tenta interpretar o input como um número
                resultado[0] = listaPacientes.buscar(id); // Busca o paciente pelo ID
            } catch (NumberFormatException e) {
                resultado[0] = listaPacientes.buscarPorNome(input); // Se falhar, busca pelo nome
            }

            // Se o paciente for encontrado
            if (resultado[0] != null) {
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Apagar Paciente");
                confirmDialog.setHeaderText("Paciente Encontrado: " + resultado[0].getNome());
                confirmDialog.setContentText("Deseja apagar este paciente?");

                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) { // Se o usuário confirmar
                        if (listaPacientes.remover(resultado[0].getId())) { // Remove o paciente pelo ID
                            Alert successDialog = new Alert(Alert.AlertType.INFORMATION, "Paciente removido com sucesso!");
                            successDialog.showAndWait();
                        } else {
                            Alert errorDialog = new Alert(Alert.AlertType.ERROR, "Erro ao remover o paciente.");
                            errorDialog.showAndWait();
                        }
                    }
                });
            } else {
                Alert notFoundDialog = new Alert(Alert.AlertType.ERROR, "Paciente não encontrado!");
                notFoundDialog.showAndWait();
            }
        });
    }

    // Método para adicionar um paciente à fila de atendimento
    private void adicionarPacienteFila() {
        // Cria um diálogo para que o usuário insira o ID do paciente
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar à Fila");
        dialog.setHeaderText("Digite o ID do paciente que deseja adicionar à fila:");
        dialog.setContentText("ID:");

        // Processa a entrada do usuário
        dialog.showAndWait().ifPresent(idInput -> {
            try {
                int id = Integer.parseInt(idInput); // Converte o ID fornecido para número inteiro
                Paciente paciente = listaPacientes.buscar(id); // Busca o paciente pelo ID fornecido
                if (paciente != null) {
                    filaAtendimento.entrarNaFila(paciente); // Adiciona o paciente à fila
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Paciente adicionado à fila com sucesso!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Paciente não encontrado.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "O ID deve ser um número válido!");
                alert.showAndWait();
            }
        });
    }

    // Método para chamar o próximo paciente na fila
    private void chamarPaciente() {
        Paciente paciente = filaAtendimento.chamarPaciente(); // Obtém o próximo paciente da fila
        if (paciente != null) {
            // Exibe o nome e o motivo da consulta do paciente chamado
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Próximo paciente:\n" + "Nome: " + paciente.getNome() + "\nMotivo: " + paciente.getMotivoConsulta());
            alert.showAndWait();

            // Registra a chamada no histórico
            String registro = "Atendido: " + paciente.getNome() + " - " + paciente.getMotivoConsulta();
            historico.registrarAtendimento(registro);
        } else {
            // Caso não haja pacientes na fila
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhum paciente na fila.");
            alert.showAndWait();
        }
    }

    // Método para desfazer a última operação registrada
    private void desfazerUltimaOperacao() {
        String operacaoDesfeita = historico.desfazerUltimaOperacao(); // Remove a última operação da pilha de histórico
        if (operacaoDesfeita != null) {
            // Exibe uma mensagem indicando qual operação foi desfeita
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "A seguinte operação foi desfeita:\n" + operacaoDesfeita);
            alert.showAndWait();
        } else {
            // Caso não haja operações para desfazer
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhuma operação para desfazer.");
            alert.showAndWait();
        }
    }
}