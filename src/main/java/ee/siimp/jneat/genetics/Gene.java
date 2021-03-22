package ee.siimp.jneat.genetics;

public interface Gene {


    default boolean isInput() {
        return false;
    }

    default boolean isOutput() {
        return false;
    }

    default boolean isHidden() {
        return false; //!isInput() && !isOutput() && !isBias() && !isConnection();
    }

    default boolean isConnection() {
        return false;
    }
}
