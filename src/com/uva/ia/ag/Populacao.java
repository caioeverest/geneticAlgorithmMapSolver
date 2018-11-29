package com.uva.ia.ag;

import com.uva.ia.helper.Tabuleiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Populacao {
    private double scoreGlobal;
    private int tamanho;
    private List<Individuo> populacao = new ArrayList<>();
    private Individuo maisApto;
    private Tabuleiro tabuleiro;

    public Populacao(Tabuleiro tabuleiro, int tamanho) {
        this.tabuleiro = tabuleiro;
        this.tamanho = tamanho;
        init();
    }

    public Populacao(Tabuleiro tabuleiro, List<Individuo> populacao) {
        this.tabuleiro = tabuleiro;
        this.populacao = populacao;
        defineMaisApto();
        calculaScoreDaPopulacao();
    }

    public double getScoreGlobal() {
        return scoreGlobal;
    }

    public List<Individuo> getPopulacao() {
        return populacao;
    }

    public Individuo getMaisApto() {
        return maisApto;
    }

    private void init() {
        for (int i = 0; i < this.tamanho; i++) {
            this.populacao.add(new Individuo(this.tabuleiro));
        }
        defineMaisApto();
        calculaScoreDaPopulacao();
    }

    private void defineMaisApto() {
        Collections.sort(this.populacao);
        this.maisApto = this.populacao.get(0);
    }

    private void calculaScoreDaPopulacao() {
        double out = 0.0;
        for (Individuo i: populacao) {
            out += i.getScore();
        }
        scoreGlobal = out;
    }

    @Override
    public String toString() {
        return "Score da populacao: " + scoreGlobal
                + "\nTamanho da populacao: " + populacao.size()
                + "\n" + maisApto.toString();
    }
}
