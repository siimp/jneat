package ee.siimp.genetics;

import ee.siimp.neuralnetwork.NeuralNetwork;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Genome implements NeuralNetwork {

    private List<Gene> genes = new ArrayList<>();

    private List<OutputNodeGene> outputNodeGenes = new ArrayList<>();

    private List<InputNodeGene> inputNodeGenes = new ArrayList<>();

    private Map<NodeGene, List<ConnectionGene>> inputs = new HashMap<>();

    public void addGene(Gene gene) {
        genes.add(gene);

        if (gene.isOutput()) {
            outputNodeGenes.add((OutputNodeGene) gene);
        } else if (gene.isInput()) {
            inputNodeGenes.add((InputNodeGene) gene);
        } else if (gene.isConnection()) {
            ConnectionGene connectionGene = (ConnectionGene) gene;
            inputs.computeIfAbsent(connectionGene.getOutput(), (k) -> new ArrayList<>());
            inputs.get(connectionGene.getOutput()).add(connectionGene);
        }
    }

}
