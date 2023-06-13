package src.Comparators;
import src.Midia;

public interface ComparatorMidia{
    int compare(Midia midia, String valor);

    static ComparatorMidia porNome(){
        return (midia,valor) -> midia.getNome().toLowerCase().contains(valor) ? 0 : 1;
    }

    static ComparatorMidia porGenero(){
        return (midia,valor) -> midia.getGenero().toString().equalsIgnoreCase(valor) ? 0 : 1;
    }

    static ComparatorMidia porIdioma(){
        return (midia,valor) -> midia.getIdioma().toString().equalsIgnoreCase(valor) ? 0 : 1;
    }
}
