package ee.siimp.jneat.neuralnetwork;

import ee.siimp.jneat.genetics.ConnectionGene;
import ee.siimp.jneat.genetics.NodeGene;
import ee.siimp.jneat.genetics.OutputNodeGene;

import java.util.*;

public interface NeuralNetwork {

    List<OutputNodeGene> getOutputNodeGenes();

    Map<NodeGene, List<ConnectionGene>> getInputs();

    default void calculate() {
        for (NodeGene nodeGene : getOutputNodeGenes()) {
            nodeGene.setValue(calculateRecursive(nodeGene));
        }
    }

    default double calculateRecursive(NodeGene nodeGene) {
        double summedInputs = 0.0;

        for (ConnectionGene connectionGene : getInputs().get(nodeGene)) {
            if (connectionGene.isExpressed()) {
                if (connectionGene.getSource().isInput()) {
                    summedInputs += connectionGene.getWeight() * connectionGene.getSource().getValue();
                } else {
                    summedInputs += connectionGene.getWeight() * calculateRecursive(connectionGene.getSource());
                }
            }
        }

        summedInputs += nodeGene.getBias();

        double value = summedInputs;
        if (nodeGene.isHidden()) {
            value = applyActivationFunction(summedInputs);
        }

        return value;
    }

    default double applyActivationFunction(double sum) {
        return applySigmoid(sum);
    }

    static double applySigmoid(double sum) {
        return 1.0 / (1.0 + Math.pow(Math.E, -1.0 * sum));
    }

}
