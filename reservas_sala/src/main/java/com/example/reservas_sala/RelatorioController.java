package com.example.reservas_sala;

import com.itextpdf.kernel.colors.ColorConstants; // Para cores
import com.itextpdf.kernel.pdf.PdfWriter; // Para PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument; // Para PdfDocument
import com.itextpdf.layout.Document; // Para Document
import com.itextpdf.layout.element.Paragraph; // Para Paragraph
import com.itextpdf.layout.element.Table; // Para Table
import com.itextpdf.layout.element.Cell; // Para Cell
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

public class RelatorioController {

    @FXML
    private VBox relatorioVBox; // VBox para armazenar os labels de relatório

    @FXML
    private Button btnGerarPDF; // Botão para gerar PDF

    public void initialize(String[] sala1, String[] sala2, String[] sala3) {
        // Adiciona os dados das reservas de cada sala ao VBox
        adicionarReservasAoRelatorio("Sala 1", sala1);
        adicionarReservasAoRelatorio("Sala 2", sala2);
        adicionarReservasAoRelatorio("Sala 3", sala3);

        // Configura a ação do botão
        btnGerarPDF.setOnAction(event -> gerarPDF(sala1, sala2, sala3));
    }

    private void adicionarReservasAoRelatorio(String nomeSala, String[] reservas) {
        // Adiciona o nome da sala
        relatorioVBox.getChildren().add(new Label(nomeSala));

        // Adiciona as reservas
        String[] horarios = {"8:00", "12:00", "14:00", "16:00", "18:00", "20:00"};
        for (int i = 0; i < reservas.length; i++) {
            String status = reservas[i];
            String horario = horarios[i];
            relatorioVBox.getChildren().add(new Label(horario + ": " + status));
        }
    }

    private void gerarPDF(String[] sala1, String[] sala2, String[] sala3) {
        String caminhoPDF = "relatorio_reservas.pdf"; // Caminho para salvar o PDF

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(caminhoPDF));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Título do documento
            Paragraph titulo = new Paragraph("Relatório de Reservas")
                    .setFontSize(24)
                    .setBold()
                    .setMarginBottom(20);
            document.add(titulo);

            // Adicionar uma tabela
            Table table = new Table(new float[]{1, 1, 1, 1}); // 4 colunas
            table.addHeaderCell(new Cell().add(new Paragraph("Horário")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Sala 1")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Sala 2")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Sala 3")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY));

            // Preenchendo a tabela
            String[] horarios = {"8:00", "12:00", "14:00", "16:00", "18:00", "20:00"};
            for (int i = 0; i < horarios.length; i++) {
                table.addCell(new Cell().add(new Paragraph(horarios[i])));
                table.addCell(new Cell().add(new Paragraph(sala1[i])));
                table.addCell(new Cell().add(new Paragraph(sala2[i])));
                table.addCell(new Cell().add(new Paragraph(sala3[i])));
            }

            document.add(table);

            // Rodapé
            document.add(new Paragraph("Relatório gerado em: " + java.time.LocalDate.now())
                    .setFontSize(10)
                    .setMarginTop(20));

            // Opcional: Adicionar uma imagem (ex: logo)
            // Image img = new Image(ImageDataFactory.create("path/to/logo.png"));
            // document.add(img.setFixedPosition(450, 750)); // Ajuste a posição conforme necessário

            document.close();
            JOptionPane.showMessageDialog(null,"PDF gerado com sucesso em: " + caminhoPDF);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
