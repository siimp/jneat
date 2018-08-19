package ee.siimp.genetics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionGene implements Gene {

    private NodeGene input;

    private NodeGene output;

    private double weight = 1.0;

    private boolean expressed = true;

    private int innovation;

    @Override
    public boolean isConnection() {
        return true;
    }
}
