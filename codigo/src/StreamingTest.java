import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StreamingTest {

    private Streaming streaming;

    @BeforeEach
    public void setUp() {
        streaming = new Streaming();
    }

    @Test
    public void testCadastrarCliente() {
        String result = streaming.cadastrarCliente("Cadu", "123456", "cadu123");
        Assertions.assertEquals("Usuário cadastrado com sucesso!", result);
    }

    @Test
    public void testCadastrarCliente_ExistingUsername() {
        streaming.cadastrarCliente("Aleks", "abcdef", "aleks456");
        String result = streaming.cadastrarCliente("Henrique", "lucca123", "aleks456");
        Assertions.assertEquals("Já existe uma conta com esse nome de usuário", result);
    }

    @Test
    public void testCadastrarMidia() {
        Midia midia = new Midia("Filme 1", "001");
        String result = streaming.cadastrarMidia(midia);
        Assertions.assertEquals("Midia cadastrada", result);
    }

    @Test
    public void testCadastrarMidia_DuplicateId() {
        Midia midia1 = new Midia("Filme 1", "001");
        Midia midia2 = new Midia("Filme 2", "001");
        streaming.cadastrarMidia(midia1);
        String result = streaming.cadastrarMidia(midia2);
        Assertions.assertEquals("Midia já cadastrada no sistema", result);
    }

    @Test
    public void testBuscarFilme_ByTitle() {
        Midia midia1 = new Midia("Filme 1", "001");
        Midia midia2 = new Midia("Filme 2", "002");
        streaming.cadastrarMidia(midia1);
        streaming.cadastrarMidia(midia2);
        Assertions.assertEquals(1, streaming.buscarFilme("Filme 1").size());
        Assertions.assertEquals(0, streaming.buscarFilme("Filme 3").size());
    }

    @Test
    public void testBuscarFilme_ByGenres() {
        Midia midia1 = new Midia("Filme 1", "001");
        midia1.adicionarGenero("Ação");
        midia1.adicionarGenero("Aventura");
        Midia midia2 = new Midia("Filme 2", "002");
        midia2.adicionarGenero("Drama");
        streaming.cadastrarMidia(midia1);
        streaming.cadastrarMidia(midia2);
        String[] genres1 = {"Ação", "Aventura"};
        String[] genres2 = {"Comédia"};
        Assertions.assertEquals(1, streaming.buscarFilme(genres1).size());
        Assertions.assertEquals(0, streaming.buscarFilme(genres2).size());
    }

    @Test
    public void testLogin_Successful() {
        streaming.cadastrarCliente("Aleks", "abcdef", "aleks456");
        String result = streaming.login("aleks456", "abcdef");
        Assertions.assertEquals("Login feito com sucesso", result);
    }

    @Test
    public void testLogin_WrongPassword() {
        streaming.cadastrarCliente("Cadu", "123456", "cadu123");
        String result = streaming.login("cadu123", "wrongpassword");
        Assertions.assertEquals("Senha incorreta", result);
    }

    @Test
    public void testLogin_UserNotFound() {
        String result = streaming.login("user123", "password");
        Assertions.assertEquals("Usuário não encontrado", result);
    }

    @Test
    public void testAdicionarMidiaFutura() {
        Cliente cliente = new Cliente("Cadu", "123456", "cadu123");
        Midia midia = new Midia("Filme 1", "001");
        streaming.adicionarMidiaFutura(midia);
        Assertions.assertEquals(1, cliente.getMidiasFuturas().size());
    }

    @Test
    public void testTerminarMidia() {
        Cliente cliente = new Cliente("Cadu", "123456", "cadu123");
        Midia midia = new Midia("Filme 1", "001");
        cliente.adicionarMidiaFutura(midia);
        streaming.terminarMidia("001");
        Assertions.assertEquals(0, cliente.getMidiasFuturas().size());
    }
}
