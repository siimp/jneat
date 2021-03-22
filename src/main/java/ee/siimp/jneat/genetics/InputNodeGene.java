package ee.siimp.jneat.genetics;

public class InputNodeGene extends NodeGene {

    protected InputNodeGene(int index) {
        super(index);
    }

    @Override
    public boolean isInput() {
        return true;
    }
}
