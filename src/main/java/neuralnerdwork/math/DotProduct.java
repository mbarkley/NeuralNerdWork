package neuralnerdwork.math;

public record DotProduct(VectorFunction left, VectorFunction right) implements ScalarFunction {

    @Override
    public int inputLength() {
        return Math.max(left.inputLength(), right.inputLength());
    }

    @Override
    public double apply(double[] input) {
        final Vector lVector = left.apply(input);
        final Vector rVector = right.apply(input);

        double accum = 0.0;
        // TODO check matching length and throw with message
        for (int i = 0; i < Math.max(lVector.length(), rVector.length()); i++) {
            accum += lVector.get(i) * rVector.get(i);
        }

        return accum;
    }

    @Override
    public ScalarFunction differentiate(int variableIndex) {
        final VectorFunction leftDerivative = left.differentiate(variableIndex);
        final VectorFunction rightDerivative = right.differentiate(variableIndex);

        return new ScalarSumFunction(new DotProduct(leftDerivative, right), new DotProduct(left, rightDerivative));
    }
}
