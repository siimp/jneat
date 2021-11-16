package ee.siimp.jneat.samples;

import ee.siimp.jneat.evolution.Population;

public class XorSample {

    private static final double DELTA = 0.01;

    private static final double[][][] INPUT_OUTPUT = new double[][][]{
            new double[][]{
                    new double[]{0.0, 0.0}, new double[]{0.0},
                    new double[]{1.0, 0.0}, new double[]{1.0},
                    new double[]{0.0, 1.0}, new double[]{1.0},
                    new double[]{1.0, 1.0}, new double[]{0.0},
            }
    };

    public static void main(String[] args) {
        Population population = Population.create(2, 1);
    }
}
