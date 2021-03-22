package ee.siimp.jneat.genetics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeGene implements Gene {

    // Neuron properties
    private double value;

    private double bias = 0.0;
}
