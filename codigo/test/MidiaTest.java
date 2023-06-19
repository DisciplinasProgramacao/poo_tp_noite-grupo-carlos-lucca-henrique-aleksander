package test;

import src.Aplicacao;
import src.Avaliacao;
import src.Cliente;
import src.Midia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MidiaTest {

    private Midia midia;
    private Cliente cliente, cliente2;

    @BeforeEach
    void base() {
        midia = new Midia("Suzume", "123456", LocalDate.now(), true);
        cliente = new Cliente("João Caram", "caram123", "Caram");
        cliente2 = new Cliente("Nogueira", "nog123", "Nog");
    }

    @Test
    void getData() {
        LocalDate data = LocalDate.now();
        String dataFormatada = data.format(Aplicacao.DATA_FORMATTER);
        assertEquals(dataFormatada, midia.getData());
    }

    @Test
    void adicionaAssistido() {
        assertEquals(0, midia.getAssistidaPorClientes());
        midia.adicionaAssistido();
        assertEquals(1, midia.getAssistidaPorClientes());
    }

    @Test
    void addAvaliacaoToAvaliacoesList() {
        Avaliacao avaliacao = new Avaliacao(4, midia, cliente);
        midia.addAvaliacaoToAvaliacoesList(avaliacao);
        ArrayList<Avaliacao> avaliacoes = midia.getAvaliacoes();
        assertEquals(1, avaliacoes.size());
        assertEquals(avaliacao, avaliacoes.get(0));
    }

    @Test
    void getNome() {
        assertEquals("Suzume", midia.getNome());
    }

    @Test
    void getIdioma() {
        assertNotNull(midia.getIdioma());
    }

    @Test
    void getGenero() {
        assertNotNull(midia.getGenero());
    }

    @Test
    void getIdentificador() {
        assertEquals("123456", midia.getIdentificador());
    }

    @Test
    void getAssistidaPorClientes() {
        assertEquals(0, midia.getAssistidaPorClientes());
    }

    @Test
    void calculaMediaAvaliacoes_SemAvaliacoes() {
        assertEquals(0.0, midia.calculaMediaAvaliacoes());
    }

    @Test
    void calculaMediaAvaliacoes_ComAvaliacoes() {
        Avaliacao avaliacao1 = new Avaliacao(4, midia, cliente);
        Avaliacao avaliacao2 = new Avaliacao(5, midia, cliente2);
        midia.addAvaliacaoToAvaliacoesList(avaliacao1);
        midia.addAvaliacaoToAvaliacoesList(avaliacao2);
        assertEquals(4.5, midia.calculaMediaAvaliacoes());
    }

    @Test
    void toStringTest() {
        String expected = "Nome: " + midia.getNome() + "\n" +
                "Idioma: " + midia.getIdioma() + "\n" +
                "Gênero: " + midia.getGenero() + "\n" +
                "Data: " + midia.getData() + "\n" +
                "Assista por: 0 pessoas\n" +
                "Avaliação média: 0.0 estrelas\n";
        assertEquals(expected, midia.toString());
    }
}
