package ee.siimp.jneat.samples;

import ee.siimp.jneat.evolution.Population;
import ee.siimp.jneat.evolution.PopulationUtils;
import ee.siimp.jneat.genetics.Genome;
import ee.siimp.jneat.genetics.GenomeUtils;


public class XorSample {

    private static final double DELTA = 0.01;
    
    private static final double[][] INPUTS = new double[][]{
        new double[]{0.0, 0.0},
        new double[]{1.0, 0.0},
        new double[]{0.0, 1.0},
        new double[]{1.0, 1.0}
    };

    private static final double[][] OUTPUTS = new double[][]{
            new double[]{0.0},
            new double[]{1.0},
            new double[]{1.0},
            new double[]{0.0},
    };

    public static void main(String[] args) {
        System.out.println("Running XOR sample");
        Population population = Population.create(2, 1);
        System.out.println(String.format("Population with size of %d created", population.getGenomes().size()));


        double maxFitness = -Double.MAX_VALUE;
        double minFitness = Double.MAX_VALUE;
        for (Genome genome: population.getGenomes()) {
            GenomeUtils.calculateFitness(genome, INPUTS, OUTPUTS);
            if (genome.getFitness() > maxFitness) {
                maxFitness = genome.getFitness();
            }

            if (genome.getFitness() < minFitness) {
                minFitness = genome.getFitness();
            }

            if (Math.abs(genome.getFitness()) <= DELTA) {
                System.out.println("Solution found");
                break;
            }
        }

        System.out.println(String.format("Min fitness %.4f and max fitness %.4f", minFitness, maxFitness));

        PopulationUtils.evolve(population);
    }
}
