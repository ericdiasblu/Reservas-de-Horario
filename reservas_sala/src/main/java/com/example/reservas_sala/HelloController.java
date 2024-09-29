package com.example.reservas_sala;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HelloController {

    @FXML
    private Pane painel1, painel2, painel3;

    @FXML
    private Button horario1, horario2, horario3, horario4, horario5, horario6;
    @FXML
    private Button horario7, horario8, horario9, horario10, horario11, horario12;
    @FXML
    private Button horario13, horario14, horario15, horario16, horario17, horario18;

    @FXML
    private ProgressBar barraProgressoSala1, barraProgressoSala2, barraProgressoSala3;

    // Definindo os horários para cada sala
    private final String[] sala1 = new String[6];
    private final String[] sala2 = new String[6];
    private final String[] sala3 = new String[6];

    @FXML
    private void initialize() {
        // Inicializando o status dos horários como "Disponível"
        Arrays.fill(sala1, "Disponível");
        Arrays.fill(sala2, "Disponível");
        Arrays.fill(sala3, "Disponível");

        // Agrupando os botões por sala
        List<Button> botoesSala1 = Arrays.asList(horario1, horario2, horario3, horario4, horario5, horario6);
        List<Button> botoesSala2 = Arrays.asList(horario7, horario8, horario9, horario10, horario11, horario12);
        List<Button> botoesSala3 = Arrays.asList(horario13, horario14, horario15, horario16, horario17, horario18);

        // Mapeando os botões para sala1, sala2 e sala3
        mapearBotoes(botoesSala1, sala1, barraProgressoSala1);
        mapearBotoes(botoesSala2, sala2, barraProgressoSala2);
        mapearBotoes(botoesSala3, sala3, barraProgressoSala3);
    }

    // Metodo genérico para mapear botões com a sala correspondente
    private void mapearBotoes(List<Button> botoes, String[] sala, ProgressBar barraProgresso) {
        for (int i = 0; i < botoes.size(); i++) {
            final int index = i;
            Button botao = botoes.get(i);
            botao.setOnAction(event -> {
                if (Objects.equals(sala[index], "Ocupado")) {
                    // Exibe o pop-up para confirmar cancelamento
                    confirmarCancelamento(botao, sala, index, barraProgresso);
                } else {
                    sala[index] = "Ocupado";
                    botao.setStyle("-fx-background-color: #b81414;");
                    JOptionPane.showMessageDialog(null, "Reservado");
                    atualizarBarraProgresso(sala, barraProgresso);  // Atualiza a barra de progresso
                }
            });
        }
    }

    // Função que exibe uma caixa de diálogo de confirmação para cancelar a reserva
    private void confirmarCancelamento(Button botao, String[] sala, int index, ProgressBar barraProgresso) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Reserva");
        alert.setHeaderText("Reserva Ocupada");
        alert.setContentText("Você deseja cancelar a reserva deste horário?");

        // Botões de confirmação
        ButtonType buttonSim = new ButtonType("Sim");
        ButtonType buttonNao = new ButtonType("Não");

        alert.getButtonTypes().setAll(buttonSim, buttonNao);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonSim) {
                // Se o usuário confirmar, cancelar a reserva
                sala[index] = "Disponível";
                botao.setStyle("-fx-background-color: #3C6E71;"); // Volta ao estado "Disponível"
                exibirMensagem("Reserva Cancelada");
                atualizarBarraProgresso(sala, barraProgresso);  // Atualiza a barra de progresso
            }
        });
    }

    // Função para atualizar a barra de progresso
    private void atualizarBarraProgresso(String[] sala, ProgressBar barraProgresso) {
        int totalHorarios = sala.length;
        int horariosOcupados = (int) Arrays.stream(sala).filter(status -> status.equals("Ocupado")).count();
        double progresso = (double) horariosOcupados / totalHorarios;
        barraProgresso.setProgress(progresso);  // Atualiza a barra
    }

    // Função auxiliar para exibir mensagens
    private void exibirMensagem(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reserva");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void mostrarPainel1() {
        // Alterna a visibilidade do pane
        painel1.setVisible(!painel1.isVisible());
    }

    @FXML
    private void mostrarPainel2() {
        // Alterna a visibilidade do pane
        painel2.setVisible(!painel2.isVisible());
    }

    @FXML
    private void mostrarPainel3() {
        // Alterna a visibilidade do pane
        painel3.setVisible(!painel3.isVisible());
    }

    @FXML
    private void gerarRelatorio() {
        try {
            // Carregar o FXML do relatório
            FXMLLoader loader = new FXMLLoader(getClass().getResource("relatorio.fxml"));
            Parent root = loader.load();

            // Obter o controlador do relatório
            RelatorioController relatorioController = loader.getController();
            relatorioController.initialize(sala1, sala2, sala3); // Passando as reservas

            // Criar uma nova cena e janela para o relatório
            Stage stage = new Stage();
            stage.setTitle("Relatório de Reservas");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
