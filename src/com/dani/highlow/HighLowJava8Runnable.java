package com.dani.highlow;

import java.util.List;

public class HighLowJava8Runnable implements Runnable
{

    private List<Integer> numbers;
    private int           from;
    private int           to;
    private HighLowTuple  result;

    HighLowJava8Runnable( List<Integer> numbers, int from, int to )
    {
        this.numbers = numbers;
        this.from = from;
        this.to = to;
        result = new HighLowTuple( -1, Integer.MAX_VALUE );
    }

    @Override
    public void run()
    {
        calculateHighLow();

    }

    private void calculateHighLow()
    {

        numbers.sort( ( a, b ) -> a - b );
        result.high = numbers.get( 0 );
        result.low = numbers.get( numbers.size() - 1 );
    }

    public HighLowTuple getResult()
    {
        return result;
    }

}
