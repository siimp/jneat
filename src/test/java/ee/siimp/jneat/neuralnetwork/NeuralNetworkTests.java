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

        ConnectionGene connectionGene1 = new ConnectionGene();
        connectionGene1.setSource(input1);
        connectionGene1.setDestination(output);
        genome.addGene(connectionGene1);

        ConnectionGene connectionGene2 = new ConnectionGene();
        connectionGene2.setSource(input2);
        connectionGene2.setDestination(output);
        genome.addGene(connectionGene2);

        input1.setValue(-1);
        input2.setValue(-1);

        genome.calculate();
        assertEquals( -2, output.getValue(),0.1);

        input1.setValue(1);
        input2.setValue(1);

        genome.calculate();
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

        ConnectionGene connectionGene1 = new ConnectionGene();
        connectionGene1.setSource(input);
        connectionGene1.setDestination(hidden);
        genome.addGene(connectionGene1);

        ConnectionGene connectionGene2 = new ConnectionGene();
        connectionGene2.setSource(hidden);
        connectionGene2.setDestination(output);
        genome.addGene(connectionGene2);

        input.setValue(-100);

        genome.calculate();
        assertEquals( 0, output.getValue(),0.1);

        input.setValue(0);

        genome.calculate();
        assertEquals( 0.5, output.getValue(), 0.1);

        input.setValue(100);

        genome.calculate();
        assertEquals( 1, output.getValue(), 0.1);

    }

    @Test
    public void testNotExpressedCalculation() {
        Genome genome = new Genome();

        OutputNodeGene output = new OutputNodeGene();
        genome.addGene(output);

        InputNodeGene input = new InputNodeGene();
        genome.addGene(input);

        ConnectionGene connectionGene = new ConnectionGene();
        connectionGene.setSource(input);
        connectionGene.setDestination(output);
        connectionGene.setExpressed(false);
        genome.addGene(connectionGene);

        input.setValue(-1);

        genome.calculate();
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


        ConnectionGene connectionGene = new ConnectionGene();
        connectionGene.setSource(input);
        connectionGene.setDestination(output);
        genome.addGene(connectionGene);


        input.setValue(1);

        genome.calculate();
        assertEquals( 2, output.getValue(),0.1);

    }
}
