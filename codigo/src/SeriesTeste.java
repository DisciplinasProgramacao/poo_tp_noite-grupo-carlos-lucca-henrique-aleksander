package src;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class SeriesTeste {
    ArrayList<String> idioma = new ArrayList<>();
    ArrayList<String> genero = new ArrayList<>();

    @Before
    public void setup() {
        idioma.add("Japones");
        genero.add("ação");
    }

    @Test
    public void testAdicionarAssistido() {
        Serie serie = new Serie("Attack on Titan", "000", idioma, genero, LocalDate.now(), 0);
        assertEquals(0, serie.getAssistidaPorClientes());
        serie.adicionaAssistido();
        assertEquals(1, serie.getAssistidaPorClientes());
    }
}