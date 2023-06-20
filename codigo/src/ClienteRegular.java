package src;

/**
 * Implementação da interface ICliente para um cliente regular.
 */
public class ClienteRegular implements ICliente {

    /**
     * Verifica se um cliente regular pode terminar de assistir a uma mídia.
     *
     * @param cliente O objeto Cliente que representa o cliente atual.
     * @param midia   A mídia que o cliente deseja terminar de assistir.
     * @return true se o cliente pode terminar de assistir a mídia, false caso
     *         contrário.
     * @throws IllegalArgumentException se a mídia já foi vista pelo cliente ou se a
     *                                  mídia só pode ser vista por um cliente
     *                                  profissional.
     */
    @Override
    public boolean terminarMidia(Cliente cliente, Midia midia) {
        // Verifica se o cliente já assistiu a mídia
        boolean jaAssistiu = cliente.getMidiasAssistidas()
                .stream()
                .anyMatch(midiaA -> midiaA.getNome().equals(midia.getNome()));

        if (jaAssistiu) {
            throw new IllegalArgumentException("Essa mídia já foi vista");
        } else {
            if (midia.getIsLancamentoFuturo()) {
                throw new IllegalArgumentException("Essa mídia só pode ser vista por um cliente profissional");
            } else {
                return true;
            }
        }
    }
}
