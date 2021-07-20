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

    /**
     * In contrast, NEAT
     * biases the search towards minimal-dimensional spaces by starting out with a uniform
     * population of networks with zero hidden nodes (i.e., all inputs connect directly to out-
     * puts).
     */
    public static Genome create(GenePool genePool, int numberOfInputs, int numberOfOutputs) {
        Genome genome = new Genome();
        for (int outputNodeIndex = 0; outputNodeIndex < numberOfOutputs; outputNodeIndex++) {
            genome.addGene(genePool.getOutputNodeGene(outputNodeIndex));
        }

        for (int inputNodeIndex = 0; inputNodeIndex < numberOfInputs; inputNodeIndex++) {
            InputNodeGene input = genePool.getInputNodeGene(inputNodeIndex);
            genome.addGene(input);
            for (OutputNodeGene output: genome.getOutputNodeGenes()) {
                genome.addGene(genePool.getNewConnectionGene(input, output));
            }
        }

        return genome;
    }

    @Override
    public int compareTo(Genome other) {
        return Double.compare(this.getFitness(), other.getFitness());
    }
}
