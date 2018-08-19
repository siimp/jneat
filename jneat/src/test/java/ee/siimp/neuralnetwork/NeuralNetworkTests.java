package ee.siimp.neuralnetwork;

import ee.siimp.genetics.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        connectionGene1.setInput(input1);
        connectionGene1.setOutput(output);
        genome.addGene(connectionGene1);

        ConnectionGene connectionGene2 = new ConnectionGene();
        connectionGene2.setInput(input2);
        connectionGene2.setOutput(output);
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
        connectionGene1.setInput(input);
        connectionGene1.setOutput(hidden);
        genome.addGene(connectionGene1);

        ConnectionGene connectionGene2 = new ConnectionGene();
        connectionGene2.setInput(hidden);
        connectionGene2.setOutput(output);
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
        connectionGene.setInput(input);
        connectionGene.setOutput(output);
        connectionGene.setExpressed(false);
        genome.addGene(connectionGene);

        input.setValue(-1);

        genome.calculate();
        assertEquals( 0, output.getValue(),0.1);

    }

    @Test
    public void testBiasNode() {
        Genome genome = new Genome();

        OutputNodeGene output = new OutputNodeGene();
        genome.addGene(output);

        InputNodeGene input = new InputNodeGene();
        genome.addGene(input);

        BiasNodeGene bias = new BiasNodeGene();
        genome.addGene(bias);

        ConnectionGene connectionGene = new ConnectionGene();
        connectionGene.setInput(input);
        connectionGene.setOutput(output);
        genome.addGene(connectionGene);

        ConnectionGene biasConnectionGene = new ConnectionGene();
        biasConnectionGene.setInput(bias);
        biasConnectionGene.setOutput(output);
        genome.addGene(biasConnectionGene);

        input.setValue(1);

        genome.calculate();
        assertEquals( 2, output.getValue(),0.1);

    }
}
