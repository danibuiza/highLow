package com.dani.highlow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HighLowCalculator
{

    void calculateHighLow( List<Integer> listWithIntegers ) throws InterruptedException
    {

        long start = System.currentTimeMillis();
        HighLowTuple result = calculateIteratingWholeArray( listWithIntegers );
        long end = System.currentTimeMillis();
        System.out.println( "duration calculateIteratingWholeArray " + ( end - start ) + " ms" );
        System.out.println( "results calculateIteratingWholeArray: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividing( listWithIntegers );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividing " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividing: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( listWithIntegers, 3 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 3 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 3: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( listWithIntegers, 4 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 4 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 4: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( listWithIntegers, 5 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 5 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 5: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThread( listWithIntegers, 10 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThread factor 10 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThread factor 10: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingStream( listWithIntegers );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingStream " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingStream: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingStreamParallel( listWithIntegers );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingStreamParallel " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingStreamParallel: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingSummaryStats( listWithIntegers );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingSummaryStats " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingSummaryStats: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingForEachWithoutStream( listWithIntegers );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingForEachWithoutStream " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingForEachWithoutStream: high=" + result.high + " low=" + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThreadUsingForEach( listWithIntegers, 5 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThreadUsingForEach factor 5 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThreadUsingForEach factor 5: high=" + result.high + " low="
            + result.low );
        
        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThreadUsingForEach( listWithIntegers, 50 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThreadUsingForEach factor 50 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThreadUsingForEach factor 50: high=" + result.high + " low="
            + result.low );

        start = System.currentTimeMillis();
        result = calculateUsingDividingMultiThreadPerformant( listWithIntegers, 5 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingDividingMultiThreadPerformant factor 5 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingDividingMultiThreadPerformant factor 5: high=" + result.high + " low="
            + result.low );

        
        start = System.currentTimeMillis();
        result = calculateUsingSorting(  listWithIntegers, 1000 );
        end = System.currentTimeMillis();
        System.out.println( "duration calculateUsingSorting factor 5 " + ( end - start ) + " ms" );
        System.out.println( "results calculateUsingSorting factor 5: high=" + result.high + " low="
            + result.low );


    }

    private HighLowTuple calculateUsingSummaryStats( List<Integer> arrayWithIntegers )
    {

        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        IntSummaryStatistics stats = arrayWithIntegers.stream().mapToInt( ( x ) -> x ).summaryStatistics();

        result.high = stats.getMax();
        result.low = stats.getMin();

        return result;
    }

    private HighLowTuple calculateUsingStream( List<Integer> listWithIntegers )
    {
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        /* this is obviously not performant because the list is iterated somehow twice */
        // Comparator<Integer> comparator = new Comparator<Integer>()
        // {
        //
        // @Override
        // public int compare( Integer x, Integer y )
        // {
        // return x - y;
        // }
        // };
        // result.high = arrayWithIntegers.stream().max( comparator ).get();
        // result.low = arrayWithIntegers.stream().min( comparator ).get();

        Consumer<Integer> highlow = new Consumer<Integer>()
        {

            @Override
            public void accept( Integer number )
            {
                if( result.high < number )
                    result.high = number;

                if( result.low > number )
                    result.low = number;

            }
        };
        listWithIntegers.stream().forEach( highlow );
        return result;
    }

    private HighLowTuple calculateUsingStreamParallel( List<Integer> arrayWithIntegers )
    {
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        Consumer<Integer> highlow = new Consumer<Integer>()
        {

            @Override
            public void accept( Integer number )
            {
                if( result.high < number )
                    result.high = number;

                if( result.low > number )
                    result.low = number;

            }
        };
        arrayWithIntegers.stream().parallel().forEach( highlow );
        return result;
    }

    private HighLowTuple calculateUsingForEachWithoutStream( List<Integer> arrayWithIntegers )
    {

        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        Consumer<Integer> highlow = new Consumer<Integer>()
        {

            @Override
            public void accept( Integer number )
            {
                if( result.high < number )
                    result.high = number;

                if( result.low > number )
                    result.low = number;

            }
        };
        arrayWithIntegers.forEach( highlow );
        return result;
    }

    private HighLowTuple calculateUsingDividing( List<Integer> arrayWithNumbers )
    {

        int part = arrayWithNumbers.size() / 3;

        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        HighLowTuple result1 = calculateIteratingWholeArray( arrayWithNumbers, 0, part );
        HighLowTuple result2 = calculateIteratingWholeArray( arrayWithNumbers, part, part * 2 );
        HighLowTuple result3 = calculateIteratingWholeArray( arrayWithNumbers, part * 2, arrayWithNumbers.size() );

        result.high = Math.max( result1.high, result2.high );
        result.low = Math.min( result1.low, result2.low );

        result.high = Math.max( result.high, result3.high );
        result.low = Math.min( result.low, result3.low );

        return result;

    }

    private HighLowTuple calculateUsingDividingMultiThread( List<Integer> arrayWithNumbers, int factor )
            throws InterruptedException
    {
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        int part = arrayWithNumbers.size() / factor;
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
    
    
    private HighLowTuple calculateUsingDividingMultiThreadPerformant( List<Integer> arrayWithNumbers, int factor )
            throws InterruptedException
    {
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        int part = arrayWithNumbers.size() / factor;
        for( int i = 0; i < factor; i++ )
        {
            HighLowRunnable calculateRunnable = new HighLowRunnable( arrayWithNumbers, part * i, part + ( part * i ) );
            Thread t = new Thread(calculateRunnable);
            t.start();
            t.join();
            HighLowTuple resultRunnable = calculateRunnable.getResult();
            if(result.high < resultRunnable.high)
                result.high = resultRunnable.high;
            if(result.low > resultRunnable.low)
                result.low = resultRunnable.low;
            
        }


        return result;

    }
    
    
    
    private HighLowTuple calculateUsingSorting( List<Integer> arrayWithNumbers, int factor )
            throws InterruptedException
    {
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        int part = arrayWithNumbers.size() / factor;
        for( int i = 0; i < factor; i++ )
        {
            HighLowRunnable calculateRunnable = new HighLowRunnable( arrayWithNumbers, part * i, part + ( part * i ) );
            Thread t = new Thread(calculateRunnable);
            t.start();
            t.join();
            HighLowTuple resultRunnable = calculateRunnable.getResult();
            if(result.high < resultRunnable.high)
                result.high = resultRunnable.high;
            if(result.low > resultRunnable.low)
                result.low = resultRunnable.low;
            
        }


        return result;
    }

    private HighLowTuple calculateUsingDividingMultiThreadUsingForEach( List<Integer> arrayWithNumbers, int factor )
            throws InterruptedException
    {
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );

        int part = arrayWithNumbers.size() / factor;
        List<Thread> threads = new ArrayList<Thread>();
        List<CalculateRunnableUsingForEach> runnables = new ArrayList<CalculateRunnableUsingForEach>();
        for( int i = 0; i < factor; i++ )
        {
            CalculateRunnableUsingForEach calculateRunnable = new CalculateRunnableUsingForEach( arrayWithNumbers, part
                * i, part + ( part * i ) );
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

    private HighLowTuple calculateIteratingWholeArray( List<Integer> arrayWithNumbers )
    {

        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );
        for( int i = 0; i < arrayWithNumbers.size(); i++ )
        {

            int value = arrayWithNumbers.get( i );

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

    private HighLowTuple calculateIteratingWholeArray( List<Integer> arrayWithNumbers, int from, int to )
    {

        // long start = System.currentTimeMillis();
        HighLowTuple result = new HighLowTuple( -1, Integer.MAX_VALUE );
        for( int i = from; i < to; i++ )
        {

            int value = arrayWithNumbers.get( i );

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

        private List<Integer> numbers;
        // private boolean ready;
        private HighLowTuple  result;
        private int           from;
        private int           to;

        public CalculateRunnable( List<Integer> arrayWithNumbers, int from, int to )
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

    class CalculateRunnableUsingForEach implements Runnable
    {

        private List<Integer> numbers;
        // private boolean ready;
        private HighLowTuple  result;

        // private int from;
        // private int to;

        public CalculateRunnableUsingForEach( List<Integer> arrayWithNumbers, int from, int to )
        {
            // this.from = from;
            // this.to = to;
            // ready = false;
            numbers = arrayWithNumbers.subList( from, to );
        }

        public void run()
        {
            // System.out.println( "called CalculateRunnable from " + from + " to + " + to );
            result = calculateUsingForEachWithoutStream( numbers );
            // ready = true;
            // System.out.println( "called CalculateRunnable finished" );
        }

    }

}
