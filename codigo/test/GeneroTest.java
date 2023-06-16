package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.Genero;

public class GeneroTest {

    @Test
    public void testToString() {
        Genero acao = Genero.ACAO;
        Genero anime = Genero.ANIME;
        Genero aventura = Genero.AVENTURA;
        Genero comedia = Genero.COMEDIA;
        Genero documentario = Genero.DOCUMENTARIO;
        Genero drama = Genero.DRAMA;
        Genero policial = Genero.POLICIAL;
        Genero romance = Genero.ROMANCE;
        Genero suspense = Genero.SUSPENSE;

        Assertions.assertEquals("Ação", acao.toString());
        Assertions.assertEquals("Anime", anime.toString());
        Assertions.assertEquals("Aventura", aventura.toString());
        Assertions.assertEquals("Comédia", comedia.toString());
        Assertions.assertEquals("Documentário", documentario.toString());
        Assertions.assertEquals("Drama", drama.toString());
        Assertions.assertEquals("Policial", policial.toString());
        Assertions.assertEquals("Romance", romance.toString());
        Assertions.assertEquals("Suspense", suspense.toString());
    }
}