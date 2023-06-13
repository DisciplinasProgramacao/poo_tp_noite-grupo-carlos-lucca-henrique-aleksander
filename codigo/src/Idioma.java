package src;

/**
 * Enumeração representando os idiomas de uma mídia.
 */
public enum Idioma {
    ALEMAO("Alemao"),
    COREANO("Coreano"),
    DINAMARQUES("Dinamarques"),
    ESPANHOL("Espanhol"),
    FRANCES("Frances"),
    HOLANDES("Holandes"),
    INGLES("Ingles"),
    ITALIANO("Italiano"),
    JAPONES("Japones"),
    PORTUGUES("Português"),
    RUSSO("Russo");

    /**
     * Descrição do Enum corretamente formatada e com acentuação
     */
    private String descricao;

    /**
     * Construtor do enum Idioma.
     * 
     * @param descricao A descrição do idioma.
     */
    Idioma(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição do idioma.
     *
     * @return A descrição do idioma.
     */
    @Override
    public String toString() {
        return descricao;
    }
}
