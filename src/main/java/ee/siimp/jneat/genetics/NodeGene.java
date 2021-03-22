package ee.siimp.jneat.genetics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeGene implements Gene {

    private final int index;

    protected NodeGene(int index) {
        this.index = index;
    }

    // Neuron output value
    private double value;

    private double bias = 0.0;
}
