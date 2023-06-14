package test;

import src.Aplicacao;
import src.Filme;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class FilmeTest {

    @Test
    public void testCriacaoFilme() {
        Filme filme = new Filme("Interstellar", "123456", LocalDate.of(2014, 11, 7), 169);

        assertEquals("Interstellar", filme.getNome());
        assertEquals("123456", filme.getIdentificador());
        assertEquals(LocalDate.of(2014, 11, 7).format(Aplicacao.DATA_FORMATTER), filme.getData());
        assertEquals(169, filme.getDuracao());
    }
}
