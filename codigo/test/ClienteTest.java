package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import src.Avaliacao;
import src.Cliente;
import src.Midia;

public class ClienteTest {
    Cliente cliente;
    Midia midia1, midia2, midia3;

    @BeforeEach
    void base() {
        cliente = new Cliente("João", "senha123", "joao123");
        midia1 = new Midia("Midia 1", "123456", LocalDate.of(2014, 11, 7));
        midia2 = new Midia("Midia 2", "234567", LocalDate.of(2018, 12, 20));

        cliente.adicionarMidiaFutura(midia1);
        cliente.adicionarMidiaFutura(midia2);
        cliente.terminarMidia(midia1);
    }

    @Test
    public void clienteDiferenteNulo() {
        Assertions.assertNotNull(cliente);
    }

    @Test
    public void testAdicionarMidiaFutura() {
        cliente.adicionarMidiaFutura(midia1);
        assertTrue(cliente.getMidiasFuturas().contains(midia1));
        // Adiciona uma nova mídia
        cliente.adicionarMidiaFutura(midia2);
        assertTrue(cliente.getMidiasFuturas().contains(midia2));
        assertEquals(2, cliente.getMidiasFuturas().size());
    }

    @Test
    public void adicionarMidiaFutura_naoDeveAdicionarMidiaRepetida() {
        cliente.adicionarMidiaFutura(midia1);
        cliente.adicionarMidiaFutura(midia1);

        ArrayList<Midia> midiasFuturas = cliente.getMidiasFuturas();
        Assertions.assertEquals(1, midiasFuturas.size());
    }

    @Test
    public void terminarMidia() {
        cliente.terminarMidia(midia1);
        ArrayList<Midia> midiasAssistidas = cliente.getMidiasAssistidas();
        Assertions.assertTrue(midiasAssistidas.contains(midia1));
    }

    @Test
    public void terminarMidia_naoDeveAdicionarMidiaAssistidaRepetida() {
        cliente.terminarMidia(midia1);
        cliente.terminarMidia(midia1);

        ArrayList<Midia> midiasAssistidas = cliente.getMidiasAssistidas();
        Assertions.assertEquals(1, midiasAssistidas.size());
    }

    @Test
    public void testAvaliar() {
        Avaliacao avaliacao1 = new Avaliacao(4, midia1, cliente);
        cliente.avaliar(avaliacao1, midia1);
        ArrayList<Avaliacao> avaliacoes = cliente.getAvaliacoes();
        assertEquals(1, avaliacoes.size());
        assertTrue(avaliacoes.contains(avaliacao1));
        assertEquals(1, avaliacoes.size());
    }

    @Test
    public void testAvaliar_SemTerVisto() {
        cliente.terminarMidia(midia1);
        Avaliacao avaliacao1 = new Avaliacao(4, midia1, cliente);
        cliente.avaliar(avaliacao1, midia1);
        Avaliacao avaliacao2 = new Avaliacao(4, midia2, cliente);
        // Verifique se o método avaliar() lança a exceção correta
        try {
            cliente.avaliar(avaliacao2, midia2);
            fail("Uma exceção IllegalArgumentException deveria ter sido lançada");
        } catch (IllegalArgumentException e) {
            // Verifique se a mensagem da exceção é a esperada
            String mensagemEsperada = "Você só pode avaliar uma mídia em sua lista de mídias assistidas";
            assertEquals(mensagemEsperada, e.getMessage());
        }
    }

}