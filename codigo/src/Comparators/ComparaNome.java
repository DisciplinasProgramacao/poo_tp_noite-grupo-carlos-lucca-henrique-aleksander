package src.Comparators;
import java.util.Comparator;
import src.Midia;

public class ComparaNome implements Comparator<Midia>{

    @Override
    public int compare(Midia o1, Midia o2) {
        if (o1.getNome().contains(o2.getNome())) {
            return 0;
        }
        else{
            return -1;
        }
    }
}
