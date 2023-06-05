package src.Comparators;
import java.util.Comparator;

public class ComparaGenero implements Comparator<String> {
    @Override
    public int compare(String m1, String m2) {
        return m1.equals(m2)? 0: 1;
    }
}
