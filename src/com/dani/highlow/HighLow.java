package com.dani.highlow;

import java.util.ArrayList;
import java.util.List;

public class HighLow
{

    public static void main( String[] args ) throws InterruptedException
    {
        List<Integer> arrayWithIntegers = new ArrayList<Integer>();

        for( int i = 0; i < 50000000*2; i++ )
        {
            arrayWithIntegers.add( (int)( Math.random() * 100 ) );
        }
        
        for( int i = 0; i < 50000000*2; i++ )
        {
            arrayWithIntegers.add( (int)( Math.random() * 100 ) );
        }
        
        for( int i = 0; i < 50000000*2; i++ )
        {
            arrayWithIntegers.add( (int)( Math.random() * 100 ) );
        }

        new HighLowCalculator().calculateHighLow( arrayWithIntegers );

    }

}
