package ru.evasmall.tm;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public class FactorialCallable implements Callable<BigInteger> {
    int start;
    int end;

    public FactorialCallable(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public BigInteger call() {
        BigInteger result = BigInteger.valueOf(1);
        for (int i = start; i <= end; i ++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

}
