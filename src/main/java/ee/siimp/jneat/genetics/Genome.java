package ee.siimp.jneat.genetics;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Genome implements Comparable<Genome> {

    private List<OutputNodeGene> outputNodeGenes = new ArrayList<>();

    private List<InputNodeGene> inputNodeGenes = new ArrayList<>();

    private List<ConnectionGene> connectionGenes = new ArrayList<>();

    private Map<NodeGene, List<ConnectionGene>> inputs = new HashMap<>();

    private double fitness;

    public void addGene(Gene gene) {
        if (gene.isOutput()) {
            outputNodeGenes.add((OutputNodeGene) gene);
        } else if (gene.isInput()) {
            inputNodeGenes.add((InputNodeGene) gene);
        } else if (gene.isConnection()) {
            ConnectionGene connectionGene = (ConnectionGene) gene;
            connectionGenes.add(connectionGene);
            inputs.computeIfAbsent(connectionGene.getDestination(), (k) -> new ArrayList<>());
            inputs.get(connectionGene.getDestination()).add(connectionGene);
        }
    }

    @Override
    public int compareTo(Genome other) {
        return Double.compare(this.getFitness(), other.getFitness());
    }
}
