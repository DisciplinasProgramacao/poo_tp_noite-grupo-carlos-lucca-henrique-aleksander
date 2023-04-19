package src;
public class Cliente {
    private String nomeUsuario;
    public String nome;
    private String senha;

    public Cliente(String nomeUsuario, String nome, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.nome = nome;
        this.senha = senha;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public String terminarSerie(Serie serie){
        serie.adicionaAssistido();
        return "Serie adicionada com sucesso";
    }
}