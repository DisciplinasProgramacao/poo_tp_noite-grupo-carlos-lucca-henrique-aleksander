package test;

import src.Avaliacao;
import src.Cliente;
import src.Midia;
import src.Exceptions.InvalidAvaliacaoException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class AvaliacaoTest {
    private Midia midia;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        midia = new Midia("Suzume", "123456", LocalDate.now(), true);
        cliente = new Cliente("João Caram", "caram123", "Caram");
    }

    @Test
    public void testConstrutorComentario() {
        Avaliacao avaliacao = new Avaliacao(4, "Bom filme", midia, cliente);

        Assertions.assertEquals(4, avaliacao.getAvaliacao());
        Assertions.assertEquals("Bom filme", avaliacao.getComentario());
        Assertions.assertEquals(midia, avaliacao.getMidiaAvaliada());
        Assertions.assertEquals(cliente, avaliacao.getCliente());
    }

    @Test
    public void testConstrutorSemComentario() {
        Avaliacao avaliacao1 = new Avaliacao(3, midia, cliente, LocalDate.now());
        Assertions.assertEquals(3, avaliacao1.getAvaliacao());
        Assertions.assertEquals("Sem comentário", avaliacao1.getComentario());
        Assertions.assertEquals(midia, avaliacao1.getMidiaAvaliada());
        Assertions.assertEquals(cliente, avaliacao1.getCliente());
    }

    @Test
    public void testAvaliacaoSemComentario() {
        Avaliacao avaliacao = new Avaliacao(5, midia, cliente);
        Assertions.assertEquals("Sem comentário", avaliacao.getComentario());
    }

    @Test
    public void testAvaliacaoComComentario() {
        Avaliacao avaliacao = new Avaliacao(5, "Ótimo filme", midia, cliente);
        Assertions.assertEquals("Ótimo filme", avaliacao.getComentario());
    }

    @Test
    public void testAddComentario() {
        Avaliacao avaliacao = new Avaliacao(5, midia, cliente);
        avaliacao.addComentario("Excelente filme");
        Assertions.assertEquals("Excelente filme", avaliacao.getComentario());
    }

    @Test
    public void testInvalidAvaliacaoException() {
        Assertions.assertThrows(InvalidAvaliacaoException.class, () -> {
            Avaliacao avaliacao = new Avaliacao(6, midia, cliente);
        });
    }
}
