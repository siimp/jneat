package ee.siimp.jneat.genetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * However, by keeping a list of the innovations that occurred in the
 * current generation, it is possible to ensure that when the same structure
 * arises more than once through independent mutations in the same
 * generation, each identical mutation is assigned the same innovation
 * number. Thus, there is no resultant explosion of innovation numbers
 */
public class GenePool {

    private final List<InputNodeGene> INPUT_NODE_GENES = new ArrayList<>();

    private final List<OutputNodeGene> OUTPUT_NODE_GENES = new ArrayList<>();

    private final List<HiddenNodeGene> HIDDEN_NODE_GENES = new ArrayList<>();

    private final Map<Integer, Map<Integer, Integer>> SOURCE_DESTINATION_INNOVATION_NUMBER = new HashMap<>();

    private int globalInnovationNumber = 1;

    public ConnectionGene getNewConnectionGene(NodeGene source, NodeGene destination) {
        Integer innovationNumber = getInnovationNumber(source, destination);
        return new ConnectionGene(source, destination, innovationNumber);
    }

    private Integer getInnovationNumber(NodeGene source, NodeGene destination) {
        if (!SOURCE_DESTINATION_INNOVATION_NUMBER.containsKey(source.getIndex())) {
            SOURCE_DESTINATION_INNOVATION_NUMBER.put(source.getIndex(), new HashMap<>());
        }

        Integer innovationNumber = SOURCE_DESTINATION_INNOVATION_NUMBER
                .get(source.getIndex()).get(destination.getIndex());

        if (innovationNumber == null) {
            innovationNumber = globalInnovationNumber++;
            SOURCE_DESTINATION_INNOVATION_NUMBER.get(source.getIndex())
                    .put(destination.getIndex(), innovationNumber);
        }
        return innovationNumber;
    }

    public InputNodeGene getInputNodeGene(int index) {
        return add(INPUT_NODE_GENES, index, () ->
            new InputNodeGene(index)
        );
    }

    public HiddenNodeGene getHiddenNodeGene(int index) {
        return add(HIDDEN_NODE_GENES, index, () ->
                new HiddenNodeGene(index)
        );
    }

    public OutputNodeGene getOutputNodeGene(int index) {
        return add(OUTPUT_NODE_GENES, index, () ->
                new OutputNodeGene(index)
        );
    }

    private <T> T add(List<T> genes, int index, Supplier<T> supplier) {
        if (index == genes.size()) {
            genes.add(supplier.get());
        } else if (index > genes.size()) {
            throw new RuntimeException(index + " is not next index");
        }
        return genes.get(index);
    }

}
