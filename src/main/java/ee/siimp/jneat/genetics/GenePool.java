package ee.siimp.jneat.genetics;

/**
 * However, by keeping a list of the innovations that occurred in the
 * current generation, it is possible to ensure that when the same structure
 * arises more than once through independent mutations in the same
 * generation, each identical mutation is assigned the same innovation
 * number. Thus, there is no resultant explosion of innovation numbers
 */
public class GenePool {

    private static int globalInnovationNumber = 1;

    public static ConnectionGene getConnectionGene(NodeGene source, NodeGene destination) {
        ConnectionGene connectionGene = new ConnectionGene(source, destination, globalInnovationNumber++);
        return connectionGene;
    }

}
