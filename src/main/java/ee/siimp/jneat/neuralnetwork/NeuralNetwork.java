package ee.siimp.jneat.neuralnetwork;

import ee.siimp.jneat.genetics.ConnectionGene;
import ee.siimp.jneat.genetics.Genome;
import ee.siimp.jneat.genetics.NodeGene;

public class NeuralNetwork {

    public static void calculate(Genome genome) {
        for (NodeGene nodeGene : genome.getOutputNodeGenes()) {
            nodeGene.setValue(calculateRecursive(genome, nodeGene));
        }
    }

    private static double calculateRecursive(Genome genome, NodeGene nodeGene) {
        double summedInputs = 0.0;

        for (ConnectionGene connectionGene : genome.getInputs().get(nodeGene)) {
            if (connectionGene.isExpressed()) {
                if (connectionGene.getSource().isInput()) {
                    summedInputs += connectionGene.getWeight() * connectionGene.getSource().getValue();
                } else {
                    summedInputs += connectionGene.getWeight() * calculateRecursive(genome, connectionGene.getSource());
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

    private static double applyActivationFunction(double sum) {
        return applyRelu(sum);
    }

    private static double applySigmoid(double sum) {
        return 1.0 / (1.0 + Math.pow(Math.E, -1.0 * sum));
    }

    private static double applyRelu(double sum) {
        return Math.max(0, sum);
    }

}
