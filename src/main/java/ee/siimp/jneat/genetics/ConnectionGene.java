package ee.siimp.jneat.genetics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionGene implements Gene {

    private NodeGene source;

    private NodeGene destination;

    private double weight = 1.0;

    private boolean expressed = true;

    private int innovation;

    @Override
    public boolean isConnection() {
        return true;
    }
}
