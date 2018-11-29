package com.uva.ia.helper;

import com.uva.ia.util.Constantes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tabuleiro {

    private String tabuleiro[][];
    private Integer tamanho;
    private Integer qtdDeMarcos;
    private Point entrada;
    private Point saida;
    private List<Point> paredes = new ArrayList<>();

    public Tabuleiro(int tamanho, int quantidadeDeObstaculos) {
        this.tamanho = tamanho;
        this.qtdDeMarcos = (tamanho * 0.1) > 3 ? Math.round(tamanho * (float) 0.1) : 3;
        init(tamanho, quantidadeDeObstaculos);
    }

    public Tabuleiro(Integer tamanho, Integer qtdDeMarcos, Point entrada, Point saida, List<Point> paredes) {
        this.tamanho = tamanho;
        this.qtdDeMarcos = qtdDeMarcos > 3 ? qtdDeMarcos : 3;
        this.entrada = entrada;
        this.saida = saida;
        this.paredes = paredes;
    }

    public Point getEntrada() {
        return this.entrada;
    }

    public List<Point> getParedes() {
        return this.paredes;
    }

    public Point getSaida() {
        return this.saida;
    }

    private void init(int tamanho, int quantidadeDeObstaculos){
        this.tabuleiro = new String[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (i == 0 || j == 0) {
                    tabuleiro[i][j] = Constantes.PAREDE;
                } else if (i == tamanho-1 || j == tamanho-1) {
                    tabuleiro[i][j] = Constantes.PAREDE;
                } else {
                    tabuleiro[i][j] = Constantes.CELULA_VAZIA;
                }
            }
        }

        gerarObstaculos(quantidadeDeObstaculos, 0);
        gerarEntrada();
        gerarSaida();
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getQtdDeMarcos() {
        return qtdDeMarcos;
    }

    public String[][] getTabuleiro() {
        return tabuleiro;
    }

    public Boolean parede(int x, int y){
        return tabuleiro[x][y].equals(Constantes.PAREDE);
    }

    public Boolean marcavel(int x, int y){
        return tabuleiro[x][y].equals(Constantes.CELULA_VAZIA);
    }

    private void gerarEntrada() {
        Random gerador = new Random();
        int posicaoX, posicaoY;

        posicaoX = gerador.nextInt((tamanho-1) - 1) + 1;
        posicaoY = gerador.nextInt((tamanho-1) - (tamanho/2)) + (tamanho/2);

        tabuleiro[posicaoX][posicaoY] = Constantes.BOT;
        entrada = new Point(posicaoX, posicaoY);
    }

    private void gerarSaida() {
        Random gerador = new Random();
        int posicaoX, posicaoY;

        posicaoX = gerador.nextInt((tamanho-1) - 1) + 1;
        posicaoY = gerador.nextInt((tamanho/2) - 1) + 1;

        tabuleiro[posicaoX][posicaoY] = Constantes.FINAL;
        saida = new Point(posicaoX, posicaoY);
    }

    private void gerarObstaculos(int quantidadeDeObstaculos, int count) {
        Random gerador = new Random();
        int posicaoX = gerador.nextInt((tamanho-3) - 3) + 3;
        int posicaoY = gerador.nextInt((tamanho-3) - 3) + 3;

        if(count < quantidadeDeObstaculos) {

            if(gerador.nextBoolean()) {
                tabuleiro[posicaoX][posicaoY] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX, posicaoY));
                tabuleiro[posicaoX][posicaoY-1] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX, posicaoY-1));
                tabuleiro[posicaoX][posicaoY-2] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX, posicaoY-2));
                tabuleiro[posicaoX+1][posicaoY-2] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX+1, posicaoY-2));

            } else {
                tabuleiro[posicaoX][posicaoY] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX, posicaoY));
                tabuleiro[posicaoX+1][posicaoY] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX+1, posicaoY));
                tabuleiro[posicaoX+2][posicaoY] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX+2, posicaoY));
                tabuleiro[posicaoX+2][posicaoY+1] = Constantes.PAREDE;
                paredes.add(new Point(posicaoX+2, posicaoY+1));
            }
            count++;
            gerarObstaculos(quantidadeDeObstaculos, count);
        }
    }

    public String imprime() {
        StringBuilder out = new StringBuilder();
        for(int linha = 0; linha < tamanho; linha++) {
            for(int coluna = 0; coluna < tamanho; coluna++) {
                out.append(tabuleiro[linha][coluna]);
            }
            out.append("|\n");
        }

        return "Entrada: {" + entrada.x + "," + entrada.y + "}\n"
                + "Saida: {" + saida.x + "," + saida.y + "}\n"
                + "Paredes: " + Helper.stringFyPontos(paredes) + "\n"
                + out.toString();
    }
}
