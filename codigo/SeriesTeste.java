package codigo;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeriesTeste {
    @Test
    public void testAdicionarAssistido() {
        Series series = new Series("Attack on Titan", "Japonês", "anime", "001", 0);
        assertEquals(0, series.getAssistidaPorClientes());
        series.adicionaAssistido();
        assertEquals(1, series.getAssistidaPorClientes());
    }
}