package ru.evasmall.tm;

import java.math.BigInteger;

public class FactorialThread implements Runnable {
    int start;
    int end;
    private BigInteger result;

    public FactorialThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    private BigInteger multiply() {
        BigInteger result = BigInteger.valueOf(1);
        for (int i = start; i <= end; i ++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    @Override
    public void run() {
        result = multiply();
    }

    public BigInteger getResult() {
        return result;
    }

}



