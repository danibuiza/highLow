package com.dani.highlow;

public class HighLow
{

    public static void main( String[] args ) throws InterruptedException
    {
        int[] arrayWithNumbers = new int[500000000];

        for( int i = 0; i < arrayWithNumbers.length; i++ )
        {
            arrayWithNumbers[i] = (int)( Math.random() * 100 );
            // System.out.println(arrayWithNumbers[i]);
        }

        new HighLowCalculator().calculateHighLow( arrayWithNumbers );

    }

}
