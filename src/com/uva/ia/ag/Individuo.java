package com.uva.ia.ag;

import com.uva.ia.helper.Helper;
import com.uva.ia.helper.Tabuleiro;
import com.uva.ia.util.Constantes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Individuo implements Comparable<Individuo> {
    private double score = 0.0;
    private int quantidadeDeAcidentes = 0;
    private List<Point> cromossoma = new ArrayList<>();
    private Tabuleiro tabuleiro;

    public Individuo(Tabuleiro t){
        this.tabuleiro = new Tabuleiro(t.getTabuleiro(), t.getTamanho(), t.getQtdDeMarcos(), t.getEntrada(), t.getSaida(), t.getParedes());
        init(0);
    }

    public Individuo(Tabuleiro t, List<Point> cromossoma) {
        this.tabuleiro = new Tabuleiro(t.getTabuleiro(), t.getTamanho(), t.getQtdDeMarcos(), t.getEntrada(), t.getSaida(), t.getParedes());
        this.cromossoma = cromossoma;
        calculaAcidentes();
        calculaScore();
    }

    public List<Point> getCromossoma() {
        return cromossoma;
    }

    public double getScore() {
        return score;
    }

    public int compareTo(Individuo individuo) {
        if(this.score < individuo.score) {
            return -1;
        }
        if(this.score > individuo.score) {
            return 1;
        }
        return 0;
    }

    public void setCromossoma(int i, Point p) {
        this.cromossoma.add(i, p);
        calculaAcidentes();
        calculaScore();
    }

    @Override
    public String toString() {
        return "\nScore: " + score
                + "\nPontos: " + Helper.stringFyPontos(cromossoma)
                + "\nQuantidade de acidentes: " + quantidadeDeAcidentes
                + "\n";
    }

    private void init(int count) {
        Random r = new Random();

        if(count > tabuleiro.getQtdDeMarcos()) {
            calculaAcidentes();
            calculaScore();
            return;
        }

        cromossoma.add(count, new Point(r.nextInt(tabuleiro.getTamanho()-2),
                r.nextInt(tabuleiro.getTamanho()-2)));
        count++;
        init(count);
    }

    private void calculaAcidentes() {
        Point atual, proximo;

        for (int i = 0; i < cromossoma.size(); i++) {

            if(i == 0) {
                atual = tabuleiro.getEntrada();
                proximo = cromossoma.get(0);

            } else if (i == cromossoma.size()-1) {
                atual = cromossoma.get(i);
                proximo = tabuleiro.getSaida();

            } else {
                atual = cromossoma.get(i);
                proximo = cromossoma.get(i+1);
            }

            for (Point parede: tabuleiro.getParedes()) {
                if(entre(atual, proximo, parede)) {
                    quantidadeDeAcidentes++;
                }
            }
        }
    }


    private Boolean entre (Point A, Point B, Point C) {
        if (A.x == C.x) return B.x == C.x;
        if (A.y == C.y) return B.y == C.y;
        return (A.x - C.x)*(A.y - C.y) == (C.x - B.x)*(C.y - B.y);
    }

    private void calculaScore() {
        int tmp = 0;

        for(int i = 0; i < cromossoma.size()-1; i++) {
            tmp += cromossoma.get(i).
                    distance(cromossoma.get(i+1));
        }
        tmp += quantidadeDeAcidentes * 1000;

        score = tmp;
    }
}
