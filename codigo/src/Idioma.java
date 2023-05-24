package src;

public enum Idioma {
    ALEMAO("Alemão"),
    ARABE("Árabe"),
    BULGARO("Búlgaro"),
    COREANO("Coreano"),
    CROATA("Croata"),
    DINAMARQUES("Dinamarquês"),
    ESLOVACO("Eslovaco"),
    ESPANHOL("Espanhol"),
    FRANCES("Francês"),
    GREGO("Grego"),
    HOLANDES("Holandês"),
    INGLES("Inglês"),
    IRLANDES("Irlandês"),
    ITALIANO("Italiano"),
    JAPONES("Japonês"),
    MANDARIM("Mandarim"),
    NORUEGUES("Norueguês"),
    PORTUGUES("Português"),
    RUSSO("Russo"),
    TURCO("Turco"),
    UCRANIANO("Ucraniano"),
    ZULU("Zulu");

    private String descricao;

    Idioma(String descricao) {
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

