package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import src.Aplicacao;
import src.Midia;

public class MidiaTest {
    @Test
    public void testCriacaoFilme() {
        Midia midia = new Midia("Os três mosqueteiros", "654321", null);

        assertEquals("Os três mosqueteiros", midia.getNome());
        assertEquals("123456", filme.getIdentificador());
        assertEquals(LocalDate.of(2014, 11, 7).format(Aplicacao.DATA_FORMATTER), filme.getData());
        assertEquals(169, filme.getDuracao());
    }
}
