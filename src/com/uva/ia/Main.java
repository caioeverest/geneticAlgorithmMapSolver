package com.uva.ia;

import com.uva.ia.ag.Ag;
import com.uva.ia.helper.Tabuleiro;

public class Main {

    public static void main(String[] args) {
        Tabuleiro t = new Tabuleiro(40000, 1000);
        Ag ag = new Ag(100, 1000, false, 0, t);
        ag.executar();
    }
}
