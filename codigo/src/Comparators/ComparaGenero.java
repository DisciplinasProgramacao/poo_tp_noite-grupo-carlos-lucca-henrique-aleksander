package src.Comparators;
import java.util.Comparator;
import src.Midia;

public class ComparaGenero implements Comparator<Midia> {
    @Override
    public int compare(Midia m1, Midia m2) {
        return m1.getGenero().equals(m2.getGenero())? 0: 1;
    }
}
