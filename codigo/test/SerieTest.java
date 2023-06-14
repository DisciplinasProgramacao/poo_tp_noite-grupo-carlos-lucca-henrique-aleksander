package test;

import src.Aplicacao;
import src.Serie;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

public class SerieTest {

    @Test
    public void testCriacaoFilme() {
        Serie serie = new Serie("Mushoku Tensei: Jobless Reincarnation", "000012", LocalDate.of(2021, 01, 01), 23);

        assertEquals("Mushoku Tensei: Jobless Reincarnation", serie.getNome());
        assertEquals("000012", serie.getIdentificador());
        assertEquals(LocalDate.of(2021, 01, 01).format(Aplicacao.DATA_FORMATTER), serie.getData());
        assertEquals(23, serie.getQtdEpisodios());
    }
}
