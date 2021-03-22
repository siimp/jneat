package ee.siimp.jneat.neuralnetwork;

import ee.siimp.jneat.genetics.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NeuralNetworkTests {

    @Test
    public void testNoActivationFunctionBetweenInputAndOutput() {
        Genome genome = new Genome();

        OutputNodeGene output = new OutputNodeGene();
        genome.addGene(output);

        InputNodeGene input1 = new InputNodeGene();
        genome.addGene(input1);

        InputNodeGene input2 = new InputNodeGene();
        genome.addGene(input2);


        genome.addGene(GenePool.getConnectionGene(input1, output));

        genome.addGene(GenePool.getConnectionGene(input2, output));

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

        OutputNodeGene output = new OutputNodeGene();
        genome.addGene(output);

        InputNodeGene input = new InputNodeGene();
        genome.addGene(input);

        HiddenNodeGene hidden = new HiddenNodeGene();
        genome.addGene(hidden);

        genome.addGene(GenePool.getConnectionGene(input, hidden));

        genome.addGene(GenePool.getConnectionGene(hidden, output));

        input.setValue(-100);

        NeuralNetwork.calculate(genome);
        assertEquals( 0, output.getValue(),0.1);

        input.setValue(0);

        NeuralNetwork.calculate(genome);
        assertEquals( 0.5, output.getValue(), 0.1);

        input.setValue(100);

        NeuralNetwork.calculate(genome);
        assertEquals( 1, output.getValue(), 0.1);

    }

    @Test
    public void testNotExpressedCalculation() {
        Genome genome = new Genome();

        OutputNodeGene output = new OutputNodeGene();
        genome.addGene(output);

        InputNodeGene input = new InputNodeGene();
        genome.addGene(input);

        ConnectionGene connectionGene = GenePool.getConnectionGene(input, output);
        connectionGene.setExpressed(false);
        genome.addGene(connectionGene);

        input.setValue(-1);

        NeuralNetwork.calculate(genome);
        assertEquals( 0, output.getValue(),0.1);

    }

    @Test
    public void testBias() {
        Genome genome = new Genome();

        OutputNodeGene output = new OutputNodeGene();
        genome.addGene(output);
        output.setBias(1);

        InputNodeGene input = new InputNodeGene();
        genome.addGene(input);


        genome.addGene(GenePool.getConnectionGene(input, output));

        input.setValue(1);

        NeuralNetwork.calculate(genome);
        assertEquals( 2, output.getValue(),0.1);

    }
}
