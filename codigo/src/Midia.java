package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Midia {

    // #region variável de instância
    private String nome;
    private Idioma idioma;
    private Genero genero;
    private LocalDate data;
    private String identificador;
    private int assistidaPorClientes;
    private static Random rd = new Random();
    private boolean lancamentoFuturo;
    private ArrayList<Avaliacao> avaliacoes;
    // #endregion

    /**
     * Cria um objeto da classe Midia com os parâmetros fornecidos.
     *
     * @param nome             O nome da mídia.
     * @param identificador    O identificador da mídia.
     * @param data             A data da mídia.
     * @param lancamentoFuturo Indica se a mídia é um lançamento futuro.
     */
    public Midia(String nome, String identificador, LocalDate data, boolean lancamentoFuturo) {
        this.nome = nome;
        this.data = data;
        this.identificador = identificador;
        this.lancamentoFuturo = (lancamentoFuturo == true) ? true : false;
        this.idioma = sorteiaEnum(Idioma.class); // Atribui um valor aleatório ao idioma
        this.genero = sorteiaEnum(Genero.class); // Atribui um valor aleatório ao gênero
        this.avaliacoes = new ArrayList<>(); // Inicializa uma lista vazia de avaliações
        this.assistidaPorClientes = 0; // Define o número inicial de clientes que assistiram a mídia como 0
    }

    /**
     * Sorteia um valor aleatório de um enum específico, assim fazendo as Mídias
     * ficarem com Idiomas e Gêneros aleatórios.
     *
     * @param <T>       O tipo do enum.
     * @param enumClass A classe do enum.
     * @return Um valor aleatório do enum.
     */
    private <T extends Enum<T>> T sorteiaEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        int indiceAleatorio = rd.nextInt(values.length);
        return values[indiceAleatorio];
    }

    /**
     * Retorna o valor da propriedade isLancamentoFuturo, que indica se a mídia é um
     * lançamento futuro.
     *
     * @return true se a mídia for um lançamento futuro, false caso contrário.
     */
    public boolean getIsLancamentoFuturo() {
        return this.lancamentoFuturo;
    }

    /**
     * Retorna a data de lançamento da Mídia.
     *
     * @return data de lançamento da Mídia.
     */
    public String getData() {
        return data.format(Aplicacao.DATA_FORMATTER);
    }

    /**
     * Incrementa o contador de assistidos da Mídia.
     *
     */
    public void adicionaAssistido() {
        this.assistidaPorClientes++;
    }

    /**
     * Adiciona uma avaliação a lista de avaliações da Mídia.
     *
     */
    public void addAvaliacaoToAvaliacoesList(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    /**
     * Retorna a lista de avaliações do cliente.
     *
     * @return ArrayList de avaliações.
     */
    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    /**
     * Retorna o nome da Mídia.
     *
     * @return String contendo o nome da Mídia.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o idioma da Mídia.
     *
     * @return Idioma da Mídia.
     */
    public Idioma getIdioma() {
        return idioma;
    }

    /**
     * Retorna o gênero da Mídia.
     *
     * @return Genero da Mídia.
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Retorna o identificador da Mídia.
     *
     * @return String contendo o identificador da Mídia.
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Retorna a quantidade de clientes que assistiram a Mídia.
     *
     * @return int da quantidade de clientes que assistiram.
     */
    public int getAssistidaPorClientes() {
        return assistidaPorClientes;
    }

    /**
     * Responsável pelo cálculo das médias de avaliações da Mídia.
     * Calcula a média das avaliações da Mídia com base nas avaliações armazenadas.
     * Se não houver avaliações, retorna 0.0.
     *
     * @return A média das avaliações da Mídia.
     */
    public double calculaMediaAvaliacoes() {
        // Verifica se não há avaliações
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }
        double totalAvaliacoes = 0;
        // Percorre cada avaliação na lista de avaliações
        for (Avaliacao avaliacao : avaliacoes) {
            // Soma a avaliação atual ao total
            totalAvaliacoes += avaliacao.getAvaliacao();
        }
        // Calcula a média dividindo a soma total pelas quantidade de avaliações
        return (double) totalAvaliacoes / avaliacoes.size();
    }

    /**
     * Descrição da Midia em string, com os seus respectivos dados.
     *
     * @return String com nome, idioma, genero, data de lançamento, quantidade de
     *         clientes que assistiram e a media de avaliações dos clientes.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Idioma: ").append(idioma).append("\n");
        sb.append("Gênero: ").append(genero).append("\n");
        sb.append("Data: ").append(getData()).append("\n");
        sb.append("Assista por: ").append(assistidaPorClientes).append(" pessoas").append("\n");
        sb.append("Avaliação média: ").append(String.format("%.2f", calculaMediaAvaliacoes()).replace(",", "."))
                .append(" estrelas")
                .append("\n");
        return sb.toString();
    }

    /**
     * Compara se o objeto atual é igual ao objeto fornecido.
     *
     * @param object O objeto a ser comparado.
     * @return true se os objetos são iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Midia)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        Midia compare = (Midia) object;

        return compare.getNome() == this.getNome();
    }

}
