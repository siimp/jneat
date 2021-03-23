package ee.siimp.jneat.neuralnetwork;

import ee.siimp.jneat.genetics.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NeuralNetworkTests {

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

        input1.setValue(-1);
        input2.setValue(-1);

        NeuralNetwork.calculate(genome);
        assertEquals( -2, output.getValue(),0.1);

        input1.setValue(1);
        input2.setValue(1);

        NeuralNetwork.calculate(genome);
        assertEquals( 2, output.getValue(), 0.1);

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

        input.setValue(-100.0);

        NeuralNetwork.calculate(genome);
        assertEquals( 0.0, output.getValue(),0.1);

        input.setValue(0.0);

        NeuralNetwork.calculate(genome);
        assertEquals( 0.0, output.getValue(), 0.1);

        input.setValue(100.0);

        NeuralNetwork.calculate(genome);
        assertEquals( 100.0, output.getValue(), 0.1);

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

        input.setValue(-1);

        NeuralNetwork.calculate(genome);
        assertEquals( 0, output.getValue(),0.1);

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

        input.setValue(1);

        NeuralNetwork.calculate(genome);
        assertEquals( 2, output.getValue(),0.1);
    }

    @Test
    public void testOptimalXorNetworkCalculation() {

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

        input1.setValue(0.0);
        input2.setValue(0.0);
        NeuralNetwork.calculate(genome);
        assertEquals(0.0, output.getValue(), 0.1);

        input1.setValue(1.0);
        input2.setValue(0.0);
        NeuralNetwork.calculate(genome);
        assertEquals(1.0, output.getValue(), 0.1);

        input1.setValue(0.0);
        input2.setValue(1.0);
        NeuralNetwork.calculate(genome);
        assertEquals(1.0, output.getValue(), 0.1);

        input1.setValue(1.0);
        input2.setValue(1.0);
        NeuralNetwork.calculate(genome);
        assertEquals(0.0, output.getValue(), 0.1);

    }
}
