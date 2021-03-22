package ee.siimp.jneat.genetics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenePoolTests {

    private GenePool genePool;

    @BeforeEach
    public void beforeEach() {
        genePool = new GenePool();
    }

    @Test
    public void sameInnovationNumberForSameStructure() {
        Genome genome1 = new Genome();
        genome1.addGene(genePool.getInputNodeGene(0));
        genome1.addGene(genePool.getOutputNodeGene(0));
        genome1.addGene(genePool.getConnectionGene(genome1.getInputNodeGenes().get(0),
                genome1.getOutputNodeGenes().get(0)));

        Genome genome2 = new Genome();
        genome2.addGene(genePool.getInputNodeGene(0));
        genome2.addGene(genePool.getOutputNodeGene(0));
        genome2.addGene(genePool.getConnectionGene(genome2.getInputNodeGenes().get(0),
                genome2.getOutputNodeGenes().get(0)));

        assertEquals(genome1.getInputNodeGenes().get(0),
                genome2.getInputNodeGenes().get(0));
        assertEquals(genome1.getOutputNodeGenes().get(0),
                genome2.getOutputNodeGenes().get(0));

        ConnectionGene genome1ConnectionGene = genome1.getInputs().get(genome1.getOutputNodeGenes().get(0)).get(0);
        ConnectionGene genome2ConnectionGene = genome2.getInputs().get(genome2.getOutputNodeGenes().get(0)).get(0);

        assertEquals(genome1ConnectionGene.getInnovation(), genome2ConnectionGene.getInnovation());
    }

}
