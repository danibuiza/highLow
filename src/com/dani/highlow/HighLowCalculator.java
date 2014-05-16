package com.dani.highlow;

import java.util.ArrayList;
import java.util.List;

public class HighLowCalculator
{

    void calculateHighLow( int[] arrayWithNumbers ) throws InterruptedException
    {

        long start = System.currentTimeMillis();
        HighLowTuple result = calculateIteratingWholeArray( arrayWithNumbers );
        long end = System.currentTimeMillis();
        System.out.println( "duration calculateIteratingWholeArray " + ( end - start ) + " ms" );
        System.out.println( "results calculateIteratingWholeArray: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividing( arrayWithNumbers );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividing " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividing: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( arrayWithNumbers, 3 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 3 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 3: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( arrayWithNumbers, 4 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 4 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 4: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( arrayWithNumbers, 5 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 5 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 5: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( arrayWithNumbers, 10 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 10 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 10: high=" + result.high + " low="
            + result.low );

    }

    private HighLowTuple calculateUsingDividing( int[] arrayWithNumbers )
    {

        int part = arrayWithNumbers.length / 3;

        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        HighLowTuple result1 = calculateIteratingWholeArray( arrayWithNumbers, 0, part );
        HighLowTuple result2 = calculateIteratingWholeArray( arrayWithNumbers, part, part * 2 );
        HighLowTuple result3 = calculateIteratingWholeArray( arrayWithNumbers, part * 2, arrayWithNumbers.length );

        result.high = Math.max( result1.high, result2.high );
        result.low = Math.min( result1.low, result2.low );

        result.high = Math.max( result.high, result3.high );
        result.low = Math.min( result.low, result3.low );

        return result;

    }

    private HighLowTuple calculateUsingDividingMultiThread( int[] arrayWithNumbers, int factor )
            throws InterruptedException
    {
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        int part = arrayWithNumbers.length / factor;
        List<Thread> threads = new ArrayList<Thread>();
        List<CalculateRunnable> runnables = new ArrayList<CalculateRunnable>();
        for( int i = 0; i < factor; i++ )
        {
            CalculateRunnable calculateRunnable = new CalculateRunnable( arrayWithNumbers, part * i, part + ( part * i ) );
            runnables.add( calculateRunnable );
            Thread t = new Thread( calculateRunnable );
            threads.add( t );
            t.start();
        }

        for( int i = 0; i < factor; i++ )
        {
            threads.get( i ).join();
            int value = runnables.get( i ).result.high;

            if( value > result.high )
            {
                result.high = value;
            }

            value = runnables.get( i ).result.low;

            if( value < result.low )
            {
                result.low = value;
            }
        }

        return result;

    }

    private HighLowTuple calculateIteratingWholeArray( int[] arrayWithNumbers )
    {

        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );
        for( int i = 0; i < arrayWithNumbers.length; i++ )
        {

            int value = arrayWithNumbers[i];

            if( value > result.high )
            {
                result.high = value;
            }

            if( value < result.low )
            {
                result.low = value;
            }

        }
        return result;
    }

    private HighLowTuple calculateIteratingWholeArray( int[] arrayWithNumbers, int from, int to )
    {

        // long start = System.currentTimeMillis();
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );
        for( int i = from; i < to; i++ )
        {

            int value = arrayWithNumbers[i];

            if( value > result.high )
            {
                result.high = value;
            }

            if( value < result.low )
            {
                result.low = value;
            }

        }
        // long end = System.currentTimeMillis();
        // System.out.println( "duration internal calculateIteratingWholeArray from " + from +
        // " to + " + to + "  "
        // + ( end - start ) + " ms" );
        return result;
    }

    class CalculateRunnable implements Runnable
    {

        private int[]        numbers;
        // private boolean ready;
        private HighLowTuple result;
        private int          from;
        private int          to;

        public CalculateRunnable( int[] arrayWithNumbers, int from, int to )
        {
            this.from = from;
            this.to = to;
            // ready = false;
            numbers = arrayWithNumbers;
        }

        public void run()
        {
            // System.out.println( "called CalculateRunnable from " + from + " to + " + to );
            result = calculateIteratingWholeArray( numbers, from, to );
            // ready = true;
            // System.out.println( "called CalculateRunnable finished" );
        }

    }

}
