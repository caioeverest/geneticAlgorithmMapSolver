package com.uva.ia.helper;

import com.uva.ia.ag.Individuo;
import com.uva.ia.ag.Populacao;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Helper {
    //Selecao
    public static class Escolhidos {
        public Individuo pai1;
        public Individuo pai2;

        public Escolhidos() {}
    }

    public static TreeMap<Double, Individuo> criaRoleta(Populacao p) {
        double pi;
        TreeMap<Double, Individuo> roleta = new TreeMap<>();

        for (Individuo i: p.getPopulacao()) {
            pi = i.getScore()/p.getScoreGlobal();
            roleta.put(pi, i);
            pi = 0.0;
        }

        return roleta;
    }

    public static Individuo encontraNaRoleta(TreeMap<Double, Individuo> roleta, Double chave) {
        Map.Entry<Double, Individuo> menor = roleta.floorEntry(chave);
        Map.Entry<Double, Individuo> maior = roleta.ceilingEntry(chave);

        if(menor != null && maior != null) {
            return Math.abs(chave - menor.getKey()) < Math.abs(chave - maior.getKey())
                    ? menor.getValue()
                    : maior.getValue();
        }

        return menor != null ? menor.getValue() : maior.getValue();
    }

    public static Individuo torneio(Populacao p) {
        Random sorte = new Random();
        Helper.Escolhidos escolhidos = new Helper.Escolhidos();

        Individuo selecionado1 = p.getPopulacao().get(sorte.nextInt(p.getPopulacao().size()));
        Individuo selecionado2 = p.getPopulacao().get(sorte.nextInt(p.getPopulacao().size()));

        return (selecionado1.getScore() < selecionado2.getScore()) ? selecionado1 : selecionado2;
    }

    public static StringBuilder stringFyPontos(List<Point> conjunto) {
        StringBuilder pontos = new StringBuilder();
        for (Point ponto:conjunto) {
            pontos.append("{")
                    .append(ponto.getLocation().x)
                    .append(",")
                    .append(ponto.getLocation().y)
                    .append("} ");
        }
        return pontos;
    }
}
