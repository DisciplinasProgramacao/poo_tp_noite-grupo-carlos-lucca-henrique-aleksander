package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import src.Idioma;

public class IdiomaTest {

    @Test
    public void testToString() {
        Idioma alemao = Idioma.ALEMAO;
        Idioma coreano = Idioma.COREANO;
        Idioma dinamarques = Idioma.DINAMARQUES;
        Idioma espanhol = Idioma.ESPANHOL;
        Idioma frances = Idioma.FRANCES;
        Idioma holandes = Idioma.HOLANDES;
        Idioma ingles = Idioma.INGLES;
        Idioma italiano = Idioma.ITALIANO;
        Idioma japones = Idioma.JAPONES;
        Idioma portugues = Idioma.PORTUGUES;
        Idioma russo = Idioma.RUSSO;

        Assertions.assertEquals("Alemão", alemao.toString());
        Assertions.assertEquals("Coreano", coreano.toString());
        Assertions.assertEquals("Dinamarquês", dinamarques.toString());
        Assertions.assertEquals("Espanhol", espanhol.toString());
        Assertions.assertEquals("Francês", frances.toString());
        Assertions.assertEquals("Holandês", holandes.toString());
        Assertions.assertEquals("Inglês", ingles.toString());
        Assertions.assertEquals("Italiano", italiano.toString());
        Assertions.assertEquals("Japonês", japones.toString());
        Assertions.assertEquals("Português", portugues.toString());
        Assertions.assertEquals("Russo", russo.toString());
    }
}