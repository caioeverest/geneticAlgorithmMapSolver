package com.uva.ia.ag;

import com.uva.ia.helper.Helper;
import com.uva.ia.helper.Tabuleiro;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class Ag {
    private int maxGeracoes;
    private int tamanhoPorGeracao;
    private boolean elitismo;
    private long taxaMutacao;
    private Tabuleiro tabuleiro;
    private List<Populacao> geracao = new ArrayList<>();
    private Individuo ganhador;

    public Ag(int maxGeracoes, int tamanhoPorGeracao, boolean elitismo, long taxaMutacao, Tabuleiro tabuleiro) {
        this.maxGeracoes = maxGeracoes;
        this.tamanhoPorGeracao = tamanhoPorGeracao;
        this.elitismo = elitismo;
        this.taxaMutacao = taxaMutacao;
        this.tabuleiro = tabuleiro;
    }

    //Algoritimo Genetico
    public void executar() {
        Populacao populacaoInicial = new Populacao(this.tabuleiro, this.tamanhoPorGeracao);
        geracao.add(populacaoInicial);
        ganhador = populacaoInicial.getMaisApto();
        for (int g = 1; g < maxGeracoes; g++) {
            List<Individuo> novosIndividuos = new ArrayList<>();

            for(int novo = 0; novo < tamanhoPorGeracao; novo++) {
                if(elitismo && novo == tamanhoPorGeracao-1) {
                    novosIndividuos.add(geracao.get(g-1).getMaisApto());
                    break;
                }
                Helper.Escolhidos escolhidos = seleciona(geracao.get(g-1));
                Individuo novoIndividuo = new Individuo(this.tabuleiro, crossover(escolhidos));
                if(new Random().nextLong() < taxaMutacao) mutar(novoIndividuo);
                novosIndividuos.add(novoIndividuo);
            }

            Populacao novaPopulacao = new Populacao(this.tabuleiro, novosIndividuos);
            geracao.add(novaPopulacao);
            ganhador = ganhador.getScore() > geracao.get(g).getMaisApto().getScore()
                    ? geracao.get(g).getMaisApto() : ganhador;
        }
        relatorio();
    }

    //Selecao
    private static Helper.Escolhidos seleciona(Populacao p) {
        Helper.Escolhidos escolhidos = new Helper.Escolhidos();

        escolhidos.pai1 = Helper.torneio(p);
        escolhidos.pai2 = Helper.torneio(p);

        if(escolhidos.pai1.equals(escolhidos.pai2)) { return seleciona(p); }

        return escolhidos;
    }

    //Crossover
    private List<Point> crossover(Helper.Escolhidos escolhidos) {
        List<Point> novoCromossoma = new ArrayList<>();
        Random sorte = new Random();

        int pontoDeCorte = sorte.nextInt(escolhidos.pai1.getCromossoma().size());

        novoCromossoma.addAll(escolhidos.pai1.getCromossoma().subList(0, pontoDeCorte));
        novoCromossoma.addAll(escolhidos.pai2.getCromossoma().subList(pontoDeCorte, escolhidos.pai2.getCromossoma().size()));

        return novoCromossoma;
    }

    //Mutacao
    private void mutar(Individuo i) {
        Random sorte = new Random();
        int posicao = sorte.nextInt(i.getCromossoma().size());
        Point novoGen = new Point(sorte.nextInt(tabuleiro.getTamanho()-2), sorte.nextInt(tabuleiro.getTamanho()-2));
        i.getCromossoma().remove(posicao);
        i.setCromossoma(posicao, novoGen);
    }

    // Relatorio
    private void relatorio() {
        StringBuilder geracoes = new StringBuilder();
        StringBuilder relatorio = new StringBuilder();

        for (Populacao p:geracao) {
            geracoes.append("====================== Geracao " + geracao.indexOf(p) + " =========================\n");
            geracoes.append(p.toString());
            geracoes.append("\n");
        }

        relatorio.append("======================= RELATORIO DO ALGORITIMO GENETICO ==========================\n");
        relatorio.append("Total de geracoes: ");
        relatorio.append(this.maxGeracoes);
        relatorio.append("\nTotal de individuos: ");
        relatorio.append(this.tamanhoPorGeracao);
        geracoes.append("\n");
        relatorio.append(ganhador.toString());

        //System.out.println(tabuleiro.imprime());
        System.out.println(geracoes);
        System.out.println(relatorio);
    }
}
