package src.Comparators;
import java.util.Comparator;
import src.Midia;

public class ComparaNome implements Comparator<Midia> {
    @Override
    public int compare(Midia m1, Midia m2) {
        return m1.getNome().contains(m2.getNome())? 0: 1;
    }
}
