package ee.siimp.jneat.genetics;

import ee.siimp.jneat.neuralnetwork.NeuralNetwork;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GenomeUtils {

    public static void calculateFitness(Genome genome, double[][] inputs, double[][] expectedOutputs) {
        double fitness = 0.0;

        for (int i = 0; i < inputs.length; i++) {
            double[] output = NeuralNetwork.calculate(genome, inputs[i]);
            double[] expectedOutput = expectedOutputs[i];

            for (int j = 0; j < output.length; j++) {
                double distance = Math.abs(expectedOutput[j]) - Math.abs(output[j]);
                fitness -= Math.pow(distance, 2);
            }
        }

        genome.setFitness(fitness);
    }
}
