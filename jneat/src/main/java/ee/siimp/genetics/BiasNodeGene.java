package ee.siimp.genetics;

public class BiasNodeGene extends NodeGene {

    public BiasNodeGene() {
        setValue(1.0);
    }

    @Override
    public boolean isBias() {
        return true;
    }
}
