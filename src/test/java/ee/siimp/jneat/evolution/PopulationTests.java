package ee.siimp.jneat.evolution;

import ee.siimp.jneat.genetics.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationTests {

    private GenePool genePool;

    @BeforeEach
    public void beforeEach() {
        genePool = new GenePool();
    }

    @Test
    public void testExcessAndDisjointGenesAreNotInheritedFromLessFitParent() {
        Genome genome1 = createSimpleGenome(1.0);
        Genome genome2 = createGenome(-1.0);

        Genome offspring = GenomeUtils.getOffspring(genome1, genome2);
        assertEquals(1, offspring.getConnectionGenes().size());

    }

    @Test
    public void testExcessAndDisjointGenesAreInheritedFromMoreFitParent() {
        Genome genome1 = createSimpleGenome(-1.0);
        Genome genome2 = createGenome(1.0);

        Genome offspring = GenomeUtils.getOffspring(genome1, genome2);
        assertEquals(2, offspring.getConnectionGenes().size());
    }

    @Test
    public void testEqualFitness() {
        Genome genome1 = createSimpleGenome(1.0);
        Genome genome2 = createGenome(1.0);

        Genome offspring = GenomeUtils.getOffspring(genome1, genome2);
        assertTrue(offspring.getConnectionGenes().size() >= 1);
    }

    @Test
    public void testCreatePopulation() {
        Population population = Population.create(1, 1);
        assertEquals(Parameter.POPULATION_SIZE, population.getGenomes().size());
    }

    private Genome createSimpleGenome(double fitness) {
        Genome genome = Genome.create(genePool, 1, 1);
        genome.setFitness(fitness);

        return genome;
    }

    private Genome createGenome(double fitness) {
        Genome genome = new Genome();
        genome.setFitness(fitness);

        OutputNodeGene output = genePool.getOutputNodeGene(0);
        genome.addGene(output);

        InputNodeGene input = genePool.getInputNodeGene(0);
        genome.addGene(input);

        HiddenNodeGene hidden = genePool.getHiddenNodeGene(0);
        genome.addGene(hidden);

        genome.addGene(genePool.getNewConnectionGene(input, hidden));

        genome.addGene(genePool.getNewConnectionGene(hidden, output));

        return genome;
    }
}
