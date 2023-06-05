package src.Comparators;
import java.util.Comparator;
public class ComparaIdioma implements Comparator<String> {
    @Override
    public int compare(String m1, String m2) {
        return m1.contains(m2)? 0: 1;
    }
}