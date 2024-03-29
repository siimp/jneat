package ee.siimp.jneat.genetics;

import ee.siimp.jneat.neuralnetwork.NeuralNetwork;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class GenomeUtils {

    private static final Random RANDOM = new Random();

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

    /*
Matching genes are inherited
randomly, whereas disjoint genes (those that do not match in the middle) and excess
genes (those that do not match in the end) are inherited from the more fit parent. In
this case, equal fitnesses are assumed, so the disjoint and excess genes are also inherited
randomly. The disabled genes may become enabled again in future generations:
there’s a preset chance that an inherited gene is disabled if it is disabled in either parent.
 */
    public static Genome getOffspring(Genome parent, Genome otherParent) {
        boolean hasEqualFitness = false;
        Genome moreFitParent;
        Genome lessFitParent;

        if (parent.compareTo(otherParent) > 0) {
            moreFitParent = parent;
            lessFitParent = otherParent;
        } else if (parent.compareTo(otherParent) < 0) {
            moreFitParent = otherParent;
            lessFitParent = parent;
        } else {
            hasEqualFitness = true;
            if (RANDOM.nextBoolean()) {
                moreFitParent = parent;
                lessFitParent = otherParent;
            } else {
                moreFitParent = otherParent;
                lessFitParent = parent;
            }
        }

        Genome offspring = new Genome();
        offspring.setInputNodeGenes(moreFitParent.getInputNodeGenes());
        offspring.setOutputNodeGenes(moreFitParent.getOutputNodeGenes());

        // randomly add all matching genes
        for (int i = 0; i < moreFitParent.getConnectionGenes().size(); i++) {
            ConnectionGene moreFitParentConnectionGene = moreFitParent.getConnectionGenes().get(i);
            boolean matchingGeneFound = false;
            for (int j = 0; j < lessFitParent.getConnectionGenes().size(); j++) {
                ConnectionGene lessFitParentConnectionGene = lessFitParent.getConnectionGenes().get(j);
                if (moreFitParentConnectionGene.getInnovation() == lessFitParentConnectionGene.getInnovation()) {
                    // match
                    if (RANDOM.nextBoolean()) {
                        offspring.addGene(ConnectionGene.clone(moreFitParentConnectionGene));
                    } else {
                        offspring.addGene(ConnectionGene.clone(lessFitParentConnectionGene));
                    }
                    matchingGeneFound = true;
                    break;
                }
            }

            // all disjoint and excess genes are inherited from more fit parent
            if (!matchingGeneFound) {
                if (!hasEqualFitness) {
                    offspring.addGene(ConnectionGene.clone(moreFitParentConnectionGene));
                } else {
                    if (RANDOM.nextBoolean()) {
                        offspring.addGene(ConnectionGene.clone(moreFitParentConnectionGene));
                    }
                }
            }
        }

        if (hasEqualFitness) {
            for (int j = 0; j < lessFitParent.getConnectionGenes().size(); j++) {
                ConnectionGene lessFitParentConnectionGene = lessFitParent.getConnectionGenes().get(j);
                boolean matchingGeneFound = false;

                ConnectionGene moreFitParentConnectionGene = null;
                for (int i = 0; i < moreFitParent.getConnectionGenes().size(); i++) {
                    moreFitParentConnectionGene = moreFitParent.getConnectionGenes().get(i);
                    if (moreFitParentConnectionGene.getInnovation() == lessFitParentConnectionGene.getInnovation()) {
                        matchingGeneFound = true;
                        break;
                    }
                }

                if (!matchingGeneFound) {
                    if (RANDOM.nextBoolean() && moreFitParentConnectionGene != null) {
                        offspring.addGene(ConnectionGene.clone(moreFitParentConnectionGene));
                    } else {
                        offspring.addGene(ConnectionGene.clone(lessFitParentConnectionGene));
                    }
                }

            }
        }

        return offspring;
    }

}
