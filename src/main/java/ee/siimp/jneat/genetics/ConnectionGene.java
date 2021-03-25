package ee.siimp.jneat.genetics;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ConnectionGene implements Gene {

    private final NodeGene source;

    private final NodeGene destination;

    private final int innovation;

    @Setter
    private double weight = 1.0;

    @Setter
    private boolean expressed = true;

    protected ConnectionGene(NodeGene source, NodeGene destination, int innovation) {
        this.source = source;
        this.destination = destination;
        this.innovation = innovation;
    }

    @Override
    public boolean isConnection() {
        return true;
    }

    public static Gene clone(ConnectionGene connectionGene) {
        ConnectionGene clonedConnectionGene = new ConnectionGene(
                connectionGene.getSource(),
                connectionGene.getDestination(),
                connectionGene.getInnovation()
        );
        clonedConnectionGene.setWeight(connectionGene.getWeight());
        clonedConnectionGene.setExpressed(connectionGene.isExpressed());
        return clonedConnectionGene;
    }
}
