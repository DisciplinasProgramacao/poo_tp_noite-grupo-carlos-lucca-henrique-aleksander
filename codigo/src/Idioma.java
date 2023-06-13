package src;

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
    PORTUGUES("Portugues"),
    RUSSO("Russo");

    private String descricao;

    Idioma(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

