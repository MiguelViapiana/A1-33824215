package br.edu.up;

import java.util.ArrayList;
import java.util.List;

import br.edu.up.daos.GerenciadorDeArquivos;
import br.edu.up.models.Aluno;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("\nProva A1");
        GerenciadorDeArquivos gArquivos = new GerenciadorDeArquivos();
        List<Aluno> listaDeAlunos = gArquivos.getAlunos();
    
        try {
    
            System.out.println("\nLista de Alunos: ");
            if (listaDeAlunos.isEmpty()) {
                System.out.println("Não há alunos cadastrados.");
            } else {
                for (Aluno aluno : listaDeAlunos) {
                    System.out.println("Matrícula: " + aluno.getMatricula() + ", Nome: " + aluno.getNome() + ", Nota: " + aluno.getNota());
                }
            }

            gArquivos.gerarArquivoCSV(listaDeAlunos);
        } catch (Exception e) {
            System.err.println("Erro ao obter lista de alunos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
