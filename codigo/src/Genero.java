package src;

/**
 * Enumeração representando os gêneros de uma mídia.
 */
public enum Genero {
    ACAO("Ação"),
    ANIME("Anime"),
    AVENTURA("Aventura"),
    COMEDIA("Comédia"),
    DOCUMENTARIO("Documentário"),
    DRAMA("Drama"),
    POLICIAL("Policial"),
    ROMANCE("Romance"),
    SUSPENSE("Suspense");

    /**
     * Descrição do Enum corretamente formatada e com acentuação
     */
    private String descricao;

    /**
     * Construtor do enum Genero.
     * 
     * @param descricao A descrição do gênero.
     */
    Genero(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição do gênero.
     *
     * @return A descrição do gênero.
     */
    @Override
    public String toString() {
        return descricao;
    }
}
