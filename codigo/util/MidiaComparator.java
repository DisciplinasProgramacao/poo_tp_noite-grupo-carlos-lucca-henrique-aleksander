package util;

import java.util.Comparator;

import src.Midia;

public class MidiaComparator implements Comparator<String>{


    // public int compare(Midia o1, String parametro) {
    //     if(o1.getNome().contains(parametro))
    //         return 0;
    //     else if(o1.getIdioma().getDescricao().equalsIgnoreCase(parametro))
    //         return -1;
    //      else if(o1.getGenero().getDescricao().equalsIgnoreCase(parametro))
    //         return 1;
    //     else return 99;   
    // }
    @Override
    public int compare(String midia1, String midia2) {
        // Comparação pelo nome
        int comparacaoNome = midia1.compareTo(midia2);
        if (comparacaoNome != 0) {
            return comparacaoNome;
        }

        // Comparação pelo idioma (enum)
        int comparacaoIdioma = midia1.compareTo(midia2);
        if (comparacaoIdioma != 0) {
            return comparacaoIdioma;
        }

        // Comparação pelo gênero (enum)
        return midia1.compareTo(midia2);
    }

}
