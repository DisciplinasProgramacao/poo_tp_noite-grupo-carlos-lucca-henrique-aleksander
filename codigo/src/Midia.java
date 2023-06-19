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

    public Midia(String nome, String identificador, LocalDate data, boolean lancamentoFuturo) {
        this.nome = nome;
        this.data = data;
        this.identificador = identificador;
        this.lancamentoFuturo = (lancamentoFuturo == true) ? true : false;
        // atribui um valor aleatório ao idioma
        this.idioma = sorteiaEnum(Idioma.class);
        // atribui um valor aleatório ao genero
        this.genero = sorteiaEnum(Genero.class);
        this.avaliacoes = new ArrayList<>();
        this.assistidaPorClientes = 0;
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
        sb.append("Avaliação média: ").append(calculaMediaAvaliacoes()).append(" estrelas").append("\n");
        return sb.toString();
    }
}
