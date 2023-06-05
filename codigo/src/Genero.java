package src;

public enum Genero {
    ACAO("Ação"),
    ANIME("Anime"),
    AVENTURA("Aventura"),
    COMEDIA("Comédia"),
    DOCUMENTARIO("Documentário"),
    DRAMA("Drama"),
    ESPIONAGEM("Policial"),
    ROMANCE("Romance"),
    TERROR("Suspense");

    private String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

