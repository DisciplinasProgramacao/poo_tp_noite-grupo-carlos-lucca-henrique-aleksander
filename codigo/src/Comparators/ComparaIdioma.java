package src.Comparators;
import java.util.Comparator;
import src.Midia;
public class ComparaIdioma implements Comparator<Midia> {
    @Override
    public int compare(Midia m1, Midia m2) {
        return m1.getIdioma().equals(m2.getIdioma())? 0: 1;
    }
}