package ee.siimp.jneat.genetics;

public class OutputNodeGene extends NodeGene {

    protected OutputNodeGene(int index) {
        super(index);
    }

    @Override
    public boolean isOutput() {
        return true;
    }
}
