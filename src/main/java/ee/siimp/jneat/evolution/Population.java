package ee.siimp.jneat.evolution;

import ee.siimp.jneat.genetics.ConnectionGene;
import ee.siimp.jneat.genetics.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

    private static final Random RANDOM = new Random();

    private List<Genome> genomes = new ArrayList<>();

    /*
    Matching genes are inherited
    randomly, whereas disjoint genes (those that do not match in the middle) and excess
    genes (those that do not match in the end) are inherited from the more fit parent. In
    this case, equal fitnesses are assumed, so the disjoint and excess genes are also inherited
    randomly. The disabled genes may become enabled again in future generations:
    there’s a preset chance that an inherited gene is disabled if it is disabled in either parent.
     */
    public static Genome getOffspring(Genome parent, Genome otherParent) {
        Genome offspring = new Genome();
        offspring.setInputNodeGenes(parent.getInputNodeGenes());
        offspring.setOutputNodeGenes(parent.getOutputNodeGenes());

        for (int i = 0; i < parent.getConnectionGenes().size(); i++) {
            ConnectionGene parentConnectionGene = parent.getConnectionGenes().get(i);
            for (int j = 0; j < otherParent.getConnectionGenes().size(); j++) {
                ConnectionGene otherParentConnectionGene = otherParent.getConnectionGenes().get(j);
                if (parentConnectionGene.getInnovation() == otherParentConnectionGene.getInnovation()) {
                    // match
                    if (RANDOM.nextBoolean()) {
                        offspring.addGene(parentConnectionGene);
                    } else {
                        offspring.addGene(otherParentConnectionGene);
                    }
                    break;
                }
                // disjoint
                // excess
            }
        }

        return offspring;
    }

}
