package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

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
        midia3 = new Midia("Midia 3", "345678", LocalDate.of(2004, 02, 9));
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

    // @Test
    // public void terminarMidia_deveIncrementarContagemAssistidos() {
    // cliente.terminarMidia(midia1);
    // cliente.terminarMidia(midia2);
    // cliente.terminarMidia(midia3);

    // int contagemAssistidos = midia1.getContagemAssistidos();
    // Assertions.assertEquals(1, contagemAssistidos);
    // }

    @Test
    public void buscarMidia() {
        cliente.adicionarMidiaFutura(midia1);
        cliente.adicionarMidiaFutura(midia2);
        Midia midiaEncontrada = cliente.buscarMidia(midia2);
        Assertions.assertEquals(midia2, midiaEncontrada);
    }

    @Test
    public void buscarMidia_deveRetornarNullSeMidiaNaoEncontrada() {
        cliente.adicionarMidiaFutura(midia1);
        cliente.adicionarMidiaFutura(midia2);
        Midia midiaEncontrada = cliente.buscarMidia(midia3);
        Assertions.assertNull(midiaEncontrada);
    }
}