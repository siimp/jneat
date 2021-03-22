package ee.siimp.jneat.genetics;

public class HiddenNodeGene extends NodeGene {

    protected HiddenNodeGene(int index) {
        super(index);
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
