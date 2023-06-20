package src;

public class ClienteRegular implements ICliente {
    @Override
    public boolean terminarMidia(Cliente cliente, Midia midia) {
        boolean jaAssistiu = cliente.getMidiasAssistidas()
                .stream()
                .anyMatch(midiaA -> midiaA.getNome().equals(midia.getNome()));
        if (jaAssistiu) {
            throw new IllegalArgumentException("Essa midia ja foi vista");
        } else {
            if (midia.getIsLancamentoFuturo()) {
                throw new IllegalArgumentException("Essa midia so pode ser vista por um cliente profissional");
            } else {
                return true;
            }
        }
    }
}
