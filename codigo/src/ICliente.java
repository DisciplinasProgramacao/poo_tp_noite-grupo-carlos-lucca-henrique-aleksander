package src;


public interface ICliente {
    Avaliacao avaliar(Avaliacao avaliacao, Cliente cliente);
    boolean terminarMidia(Cliente cliente, Midia midia);
}
