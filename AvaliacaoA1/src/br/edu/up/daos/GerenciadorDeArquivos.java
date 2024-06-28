package br.edu.up.daos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.up.models.Aluno;

public class GerenciadorDeArquivos {
    private String headerAluno = "";
    private String arquivoAluno = "C:\\Users\\autologon\\Documents\\A1-33824215\\AvaliacaoA1\\src\\br\\edu\\up\\alunos.csv";

    public List<Aluno> getAlunos() {
        List<Aluno> listaDeAlunos = new ArrayList<>();

        try (Scanner leitorAluno = new Scanner(new File(arquivoAluno))){

            headerAluno = leitorAluno.nextLine();
            System.out.println("1");
            while (leitorAluno.hasNextLine()) {
                System.out.println("2");
                String linha = leitorAluno.nextLine();
                String[] dados = linha.split(";");

                String matricula = dados[0];
                String nome = dados[1];
                String nota = dados[2];
                try {
                    Integer matriculaInt = Integer.parseInt(matricula);
                    Double notaDouble = Double.parseDouble(nota);

                    Aluno aluno = new Aluno(matriculaInt, nome, notaDouble);
                    listaDeAlunos.add(aluno);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter matrícula ou nota para número: " + e.getMessage());
                }
            }

            leitorAluno.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de alunos não encontrado! " + e.getMessage());
        }

        return listaDeAlunos;
    }

    public String gerarArquivoCSV(List<Aluno> listaAlunos) {
        int qtdAlunos = listaAlunos.size();
        int qtdAprovados6 = 0;
        int qtdReprovados = 0;
        double menorNota = 10;
        double maiorNota = 0;
        double mediaGeral = 0;
        double somaNotas = 0;
        String csvFile = "C:\\Users\\autologon\\Documents\\A1-33824215\\AvaliacaoA1\\src\\br\\edu\\up\\resumo.csv";
        try {
            boolean existeArquivo = new File(csvFile).exists();

            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true));
            if (!existeArquivo) {
                writer.write(
                        "quantidadeDeAlunos;qtdAprovadosNotaSuperior6;qtdReprovados;menorNota;maiorNota;mediaGeral");
            }

            for (Aluno aluno : listaAlunos) {
                double nota = aluno.getNota();
                if (nota >= 6) {
                    qtdAprovados6++;
                } else {
                    qtdReprovados++;
                }
                if (nota < menorNota) {
                    menorNota = nota;
                }
                if (nota > maiorNota) {
                    maiorNota = nota;
                }
                somaNotas += nota;
            }
            mediaGeral = somaNotas / qtdAlunos;

            writer.write("\n"+qtdAlunos + ";" + qtdAprovados6 + ";" + qtdReprovados + ";" + menorNota + ";" + maiorNota + ";"
                    + mediaGeral + "\n");

            writer.close();

            return "Arquivo CSV gerado com sucesso: " + csvFile;
        } catch (Exception e) {
            return "Erro ao gerar arquivo CSV: " + e.getMessage();
        }
    }

}
