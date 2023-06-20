package src;

/**
 * Interface que define as operações para um cliente.
 */
public interface ICliente {
    /**
     * Termina de assistir a uma determinada mídia.
     *
     * @param cliente O objeto Cliente que representa o cliente atual.
     * @param midia   A mídia que o cliente deseja terminar de assistir.
     * @return true se o cliente pode terminar de assistir a mídia, false caso
     *         contrário.
     */
    boolean terminarMidia(Cliente cliente, Midia midia);
}
