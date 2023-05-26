package src;

public enum Idioma {
    ALEMAO("Alemão"),
    COREANO("Coreano"),
    DINAMARQUES("Dinamarquês"),
    ESPANHOL("Espanhol"),
    FRANCES("Francês"),
    HOLANDES("Holandês"),
    INGLES("Inglês"),
    ITALIANO("Italiano"),
    JAPONES("Japonês"),
    PORTUGUES("Português"),
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

