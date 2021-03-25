package ee.siimp.jneat.neuralnetwork;

import ee.siimp.jneat.genetics.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NeuralNetworkTests {

    private static final double DELTA = 0.0001;

    private static final double[][] XOR_INPUTS = new double[][]{
            new double[]{0.0, 0.0},
            new double[]{1.0, 0.0},
            new double[]{0.0, 1.0},
            new double[]{1.0, 1.0}
    };

    private static final double[][] XOR_OUTPUTS = new double[][]{
            new double[]{0.0},
            new double[]{1.0},
            new double[]{1.0},
            new double[]{0.0},
    };

    private GenePool genePool;

    @BeforeEach
    public void beforeEach() {
        genePool = new GenePool();
    }

    @Test
    public void testNoActivationFunctionBetweenInputAndOutput() {
        Genome genome = new Genome();

        OutputNodeGene output = genePool.getOutputNodeGene(0);
        genome.addGene(output);

        InputNodeGene input1 = genePool.getInputNodeGene(0);
        genome.addGene(input1);

        InputNodeGene input2 = genePool.getInputNodeGene(1);
        genome.addGene(input2);

        genome.addGene(genePool.getConnectionGene(input1, output));
        genome.addGene(genePool.getConnectionGene(input2, output));

        double[] result = NeuralNetwork.calculate(genome, -1.0, -1.0);
        assertEquals(-2.0, result[0], DELTA);

        result = NeuralNetwork.calculate(genome, 1.0, 1.0);
        assertEquals(2.0, result[0], DELTA);

    }

    @Test
    public void testSingleHiddenNodeHasActivationFunction() {
        Genome genome = new Genome();

        OutputNodeGene output = genePool.getOutputNodeGene(0);
        genome.addGene(output);

        InputNodeGene input = genePool.getInputNodeGene(0);
        genome.addGene(input);

        HiddenNodeGene hidden = genePool.getHiddenNodeGene(0);
        genome.addGene(hidden);

        genome.addGene(genePool.getConnectionGene(input, hidden));

        genome.addGene(genePool.getConnectionGene(hidden, output));

        double[] result = NeuralNetwork.calculate(genome, -100.0);
        assertEquals(0.0, result[0], DELTA);

        result = NeuralNetwork.calculate(genome, 0.0);
        assertEquals(0.0, result[0], DELTA);

        result = NeuralNetwork.calculate(genome, 100.0);
        assertEquals(100.0, result[0], DELTA);

    }

    @Test
    public void testNotExpressedCalculation() {
        Genome genome = new Genome();

        OutputNodeGene output = genePool.getOutputNodeGene(0);
        genome.addGene(output);

        InputNodeGene input = genePool.getInputNodeGene(0);
        genome.addGene(input);

        ConnectionGene connectionGene = genePool.getConnectionGene(input, output);
        connectionGene.setExpressed(false);
        genome.addGene(connectionGene);

        double[] result = NeuralNetwork.calculate(genome, -1.0);
        assertEquals(0, result[0], DELTA);

    }

    @Test
    public void testBias() {
        Genome genome = new Genome();

        OutputNodeGene output = genePool.getOutputNodeGene(0);
        genome.addGene(output);
        output.setBias(1);

        InputNodeGene input = genePool.getInputNodeGene(0);
        genome.addGene(input);

        genome.addGene(genePool.getConnectionGene(input, output));

        double[] result = NeuralNetwork.calculate(genome, 1.0);
        assertEquals(2, result[0], DELTA);
    }

    @Test
    public void testXorNetworkCalculation() {

        Genome genome = getXorGenome();

        double[] result = NeuralNetwork.calculate(genome, 0.0, 0.0);
        assertEquals(0.0, result[0], DELTA);

        result = NeuralNetwork.calculate(genome, 1.0, 0.0);
        assertEquals(1.0, result[0], DELTA);

        result = NeuralNetwork.calculate(genome, 0.0, 1.0);
        assertEquals(1.0, result[0], DELTA);

        result = NeuralNetwork.calculate(genome, 1.0, 1.0);
        assertEquals(0.0, result[0], DELTA);

    }

    @Test
    public void testXorNetworkFitness() {
        Genome genome = getXorGenome();
        GenomeUtils.calculateFitness(genome, XOR_INPUTS, XOR_OUTPUTS);
        assertEquals(0.0, genome.getFitness(), DELTA);
    }

    @Test
    public void testXorCalculationPerformance() {
        Genome genome = getXorGenome();

        // warmup
        for (int i = 0; i < 100_000_000; i++) {
            NeuralNetwork.calculate(genome, 0.0, 0.0);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++) {
            NeuralNetwork.calculate(genome, 0.0, 0.0);
        }
        long durationInMillis = System.currentTimeMillis() - start;
        System.out.println("Duration " + durationInMillis + " millis");
        assertTrue(durationInMillis < 500);
    }

    private Genome getXorGenome() {
        Genome genome = new Genome();

        OutputNodeGene output = genePool.getOutputNodeGene(0);
        genome.addGene(output);

        InputNodeGene input1 = genePool.getInputNodeGene(0);
        genome.addGene(input1);
        genome.addGene(genePool.getConnectionGene(input1, output));

        InputNodeGene input2 = genePool.getInputNodeGene(1);
        genome.addGene(input2);
        genome.addGene(genePool.getConnectionGene(input2, output));

        HiddenNodeGene hiddenNodeGene = genePool.getHiddenNodeGene(0);
        hiddenNodeGene.setBias(-1.0);
        genome.addGene(hiddenNodeGene);
        genome.addGene(genePool.getConnectionGene(input1, hiddenNodeGene));
        genome.addGene(genePool.getConnectionGene(input2, hiddenNodeGene));
        ConnectionGene connectionGene = genePool.getConnectionGene(hiddenNodeGene, output);
        connectionGene.setWeight(-2.0);
        genome.addGene(connectionGene);
        return genome;
    }
}
