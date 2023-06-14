package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import src.Aplicacao;
import src.Midia;

public class MidiaTest {
    @Test
    public void testCriacaoMidia() {
        Midia midia = new Midia("Os três mosqueteiros", "654321", null);

        assertEquals("Os três mosqueteiros", midia.getNome());
        assertEquals("123456", midia.getIdentificador());
        assertEquals(LocalDate.of(2014, 11, 7).format(Aplicacao.DATA_FORMATTER), midia.getData());
    }
}
