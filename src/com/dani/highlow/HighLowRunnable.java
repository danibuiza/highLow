package com.dani.highlow;

import java.util.List;

public class HighLowRunnable implements Runnable
{

    private List<Integer> numbers;
    private int           from;
    private int           to;
    private HighLowTuple  result;

    HighLowRunnable( List<Integer> numbers, int from, int to )
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
        for( int i = from; i < to; i++ )
        {
            int value = numbers.get( i );

            if( value > result.high )
            {
                result.high = value;
            }

            value = numbers.get( i );

            if( value < result.low )
            {
                result.low = value;
            }
        }
    }

    public HighLowTuple getResult()
    { 
        return result;
    }

}
