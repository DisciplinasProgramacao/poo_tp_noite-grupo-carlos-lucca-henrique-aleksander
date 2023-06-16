package test;

import org.junit.jupiter.api.Assertions;
import src.Aplicacao;
import src.Filme;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class FilmeTest {

    @Test
    public void testCriacaoFilme() {
        Filme filme = new Filme("Interstellar", "123456", LocalDate.of(2014, 11, 7), 169);

        Assertions.assertEquals("Interstellar", filme.getNome());
        Assertions.assertEquals("123456", filme.getIdentificador());
        Assertions.assertEquals(LocalDate.of(2014, 11, 7).format(Aplicacao.DATA_FORMATTER), filme.getData());
        Assertions.assertEquals(169, filme.getDuracao());
    }
}
