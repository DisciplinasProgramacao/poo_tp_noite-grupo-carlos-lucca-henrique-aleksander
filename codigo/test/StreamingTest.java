package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Cliente;
import src.Midia;
import src.Filme;
import src.Streaming;
import src.Exceptions.IncorrectUserNameOrPasswordException;
import src.Exceptions.NameUserExistsException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class StreamingTest {
    private Streaming streaming;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        streaming = new Streaming();
        cliente = new Cliente("Thiago", "123456", "thigas");
    }

    @Test
    public void testGetClientes() {
        HashMap<String, Cliente> clientes = streaming.getClientes();
        Assertions.assertNotNull(clientes);
    }

    @Test
    public void testCadastrarCliente() throws IOException {
        String nome = "João";
        String senha = "123456";
        String nomeUsuario = "joao123";

        String resultado = streaming.cadastrarCliente(nome, senha, nomeUsuario);

        HashMap<String, Cliente> clientes = streaming.getClientes();
        Cliente cliente = clientes.get(nomeUsuario);

        Assertions.assertEquals("Usuário cadastrado com sucesso", resultado);
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(nome, cliente.getNome());
        Assertions.assertEquals(senha, cliente.getSenha());
    }

    @Test
    public void testCadastrarCliente_NomeUsuarioExistente() throws IOException {
        String nome = "João";
        String senha = "123456";
        String nomeUsuario = "joao123";

        // Cadastrar um cliente com o mesmo nome de usuário
        streaming.cadastrarCliente(nome, senha, nomeUsuario);

        // Tentar cadastrar outro cliente com o mesmo nome de usuário deve lançar uma
        // exceção
        Assertions.assertThrows(NameUserExistsException.class,
                () -> streaming.cadastrarCliente("Maria", "abcdef", nomeUsuario));
    }

    @Test
    public void testCadastrarMidia() {
        Midia midia = new Filme("Filme A", "001", LocalDate.of(2014, 11, 7), 120, true);

        String resultado = streaming.cadastrarMidia(midia);

        HashMap<String, Midia> midias = streaming.getMidias();
        Midia midiaCadastrada = midias.get(midia.getIdentificador());

        Assertions.assertEquals("Mídia cadastrada com sucesso", resultado);
        Assertions.assertNotNull(midiaCadastrada);
        Assertions.assertEquals(midia.getNome(), midiaCadastrada.getNome());
        Assertions.assertEquals(midia.getIdentificador(), midiaCadastrada.getIdentificador());
    }

    @Test
    public void testCadastrarMidia_MidiaExistente() {
        Midia midia = new Filme("Filme A", "001",
                LocalDate.of(2014, 11, 7), 120, true);

        // Cadastrar uma mídia com o mesmo identificador
        streaming.cadastrarMidia(midia);

        // Tentar cadastrar outra mídia com o mesmo identificador deve retornar uma
        // mensagem indicando que a mídia já está cadastrada
        String resultado = streaming.cadastrarMidia(midia);

        Assertions.assertEquals("Nãp", resultado);
    }

    @Test
    public void testLogin() {
        String nomeUsuario = "joao123";
        String senha = "123456";
        Cliente cliente = new Cliente("João", senha, nomeUsuario);

        // Adicionar o cliente ao sistema
        streaming.getClientes().put(nomeUsuario, cliente);

        String resultado = streaming.login(nomeUsuario, senha);

        Cliente clienteLogado = streaming.getClienteLogado();

        Assertions.assertEquals("Login feito com sucesso", resultado);
        Assertions.assertNotNull(clienteLogado);
        Assertions.assertEquals(cliente, clienteLogado);
    }

    @Test
    public void testLogin_IncorrectUserNameOrPassword() {
        String nomeUsuario = "joao123";
        String senha = "123456";
        Cliente cliente = new Cliente("João", senha, nomeUsuario);

        // Adicionar o cliente ao sistema
        streaming.getClientes().put(nomeUsuario, cliente);

        // Tentar fazer login com uma senha incorreta deve lançar uma exceção
        Assertions.assertThrows(IncorrectUserNameOrPasswordException.class,
                () -> streaming.login(nomeUsuario, "senha_incorreta"));
    }

    @Test
    public void testAdicionarMidiaFutura() throws IOException {
        Cliente cliente = new Cliente("João", "123456", "joao123");
        Midia midia = new Filme("Filme A", "001",
                LocalDate.now().plusDays(1), 120, true);

        streaming.cadastrarCliente(cliente.getNome(), cliente.getSenha(), cliente.getNomeUsuario());
        streaming.login(cliente.getNomeUsuario(), cliente.getSenha());

        streaming.adicionarMidiaFutura(midia);

        ArrayList<Midia> midiasFuturas = streaming.getClienteLogado().getMidiasFuturas();

        Assertions.assertEquals(1, midiasFuturas.size());
        Assertions.assertEquals(midia, midiasFuturas.get(0));
    }

    @Test
    public void testTerminarMidia() throws IOException {
        Midia midia = new Filme("Filme A", "001",
                LocalDate.of(2014, 11, 7), 120, true);
        streaming.cadastrarMidia(midia);
        streaming.cadastrarCliente(cliente.getNome(), cliente.getSenha(), cliente.getNomeUsuario());
        streaming.login(cliente.getNomeUsuario(), cliente.getSenha());
        streaming.getClienteLogado().terminarMidia(midia);

        streaming.terminarMidia(midia);

        ArrayList<Midia> midiasTerminadas = streaming.getClienteLogado().getMidiasAssistidas();

        Assertions.assertEquals(1, midiasTerminadas.size());
        Assertions.assertEquals(midia, midiasTerminadas.get(0));
    }
}
