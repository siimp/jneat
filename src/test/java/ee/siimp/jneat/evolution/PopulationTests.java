package ee.siimp.jneat.evolution;

import ee.siimp.jneat.genetics.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PopulationTests {

    private GenePool genePool;

    @BeforeEach
    public void beforeEach() {
        genePool = new GenePool();
    }

    @Test
    public void testOffspring() {

        Genome genome1 = createGenome();
        Genome genome2 = createGenome();

        Genome offspring = Population.getOffspring(genome1, genome2);
        assertNotNull(offspring);
        assertEquals(2, offspring.getConnectionGenes().size());

    }

    private Genome createGenome() {
        Genome genome = new Genome();

        OutputNodeGene output = genePool.getOutputNodeGene(0);
        genome.addGene(output);

        InputNodeGene input = genePool.getInputNodeGene(0);
        genome.addGene(input);

        HiddenNodeGene hidden = genePool.getHiddenNodeGene(0);
        genome.addGene(hidden);

        genome.addGene(genePool.getConnectionGene(input, hidden));

        genome.addGene(genePool.getConnectionGene(hidden, output));

        return genome;
    }
}
