package test;

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
        System.out.println(cliente);
    }

    @Test
    public void clienteDiferenteNulo() {
        Assertions.assertNotNull(cliente);
    }

    @Test
    public void adicionarMidiaFutura() {
        cliente.adicionarMidiaFutura(midia1);

        ArrayList<Midia> midiasFuturas = cliente.getMidiasFuturas();
        Assertions.assertTrue(midiasFuturas.contains(midia1));
    }
}
