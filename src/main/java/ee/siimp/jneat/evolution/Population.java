package ee.siimp.jneat.evolution;

import ee.siimp.jneat.genetics.GenePool;
import ee.siimp.jneat.genetics.Genome;
import ee.siimp.jneat.genetics.GenomeUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Population {

    private GenePool genePool = new GenePool();
    private List<Genome> genomes = new ArrayList<>();

    public static Population create(int numberOfInputs, int numberOfOutputs) {
        Population population = new Population();
        for (int i = 0; i < Parameter.POPULATION_SIZE; i++) {
            Genome genome = Genome.create(population.getGenePool(), numberOfInputs, numberOfOutputs);
            population.getGenomes().add(genome);
        }
        return population;
    }

}
