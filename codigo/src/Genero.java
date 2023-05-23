package src;

public enum Genero {
    ACAO("Ação"),
    ANIME("Anime"),
    ANIMACAO("Animação"),
    AVENTURA("Aventura"),
    COMEDIA("Comédia"),
    COMEDIA_DE_ACAO("Comédia de Ação"),
    COMEDIA_DE_TERROR("Comédia de Terror"),
    COMEDIA_ROMANTICA("Comédia Romântica"),
    DANCA("Dança"),
    DOCUMENTARIO("Documentário"),
    DRAMA("Drama"),
    ESPIONAGEM("Espionagem"),
    FAROESTE("Faroeste"),
    FANTASIA("Fantasia"),
    FANTASIA_CIENTIFICA("Fantasia Científica"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    GUERRA("Guerra"),
    MISTERIO("Mistério"),
    MUSICAL("Musical"),
    ROMANCE("Romance"),
    TERROR("Terror");

    private String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

