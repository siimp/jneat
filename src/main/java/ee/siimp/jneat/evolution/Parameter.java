package ee.siimp.jneat.evolution;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Parameter {

    public static final int POPULATION_SIZE = 150;

    public static final double MUTATION_NEW_NODE_PROBABILITY = 0.03;

    public static final double MUTATION_NEW_CONNECTION_PROBABILITY = 0.05;

    public static final double MUTATION_CONNECTION_WEIGHT_CHANGE_PROBABILITY = 0.8;

    // 10% chance of being assigned a new random value instead of uniformly perturbed
    public static final double MUTATION_CONNECTION_WEIGHT_NEW_RANDOM_VALUE_PROBABILITY = 0.1;

    // There was a 75% chance that an inherited gene was disabled if it was disabled in either parent.
    public static final double INHERITED_GENE_STAYS_DISABLED_PROBABILITY = 0.75;

    public static final double WITHOUT_CROSSOVER_TO_NEXT_GENERATION = 0.25;

    public static final double C1 = 1.0;

    public static final double C2 = 1.0;

    public static final double C3 = 0.4;

    public static final double DT = 3.0;




    /*
4.1
 Parameter Settings
The same experimental settings are used in all experiments; they were not tuned specif-
ically for any particular problem. The one exception is the hardest pole balancing prob-
lem (Double pole, no velocities, or DPNV) where a larger population size was used to
match those of other systems in this task. Because some of NEAT’s system parameters
are sensitive to population size, we altered them accordingly.
All experiments except DPNV, which had a population of 1,000, used a population
of 150 NEAT networks. The coefficients for measuring compatibility were c1 = 1.0,
c2 = 1.0, and c3 = 0.4. With DPNV, c3 was increased to 3.0 in order to allow for finer
distinctions between species based on weight differences (the larger population has
room for more species). In all experiments, δt = 3.0, except in DPNV where it was 4.0,
to make room for the larger weight significance coefficient c3 . If the maximum fitness of
a species did not improve in 15 generations, the networks in the stagnant species were
not allowed to reproduce. The champion of each species with more than five networks
was copied into the next generation unchanged. There was an 80% chance of a genome
having its connection weights mutated, in which case each weight had a 90% chance of
being uniformly perturbed and a 10% chance of being assigned a new random value.
(The system is tolerant to frequent mutations because of the protection speciation provides.)
There was a 75% chance that an inherited gene was disabled if it was disabled
in either parent. In each generation, 25% of offspring resulted from mutation without
crossover. The interspecies mating rate was 0.001. In smaller populations, the proba-
bility of adding a new node was 0.03 and the probability of a new link mutation was
0.05. In the larger population, the probability of adding a new link was 0.3, because a
larger population can tolerate a larger number of prospective species and greater topo-
logical diversity. We used a modified sigmoidal transfer function, φ(x) = 1
 , at
1+e−4.9x
all nodes. The steepened sigmoid allows more fine tuning at extreme activations. It
is optimized to be close to linear during its steepest ascent between activations −0.5
and 0.5. These parameter values were found experimentally: links need to be added
significantly more often than nodes, and an average weight difference of 3.0 is about as
significant as one disjoint or excess gene. Performance is robust to moderate variations
in these values.
     */
}
