package com.dani.highlow;

import java.util.ArrayList;
import java.util.List;

// from http://www.geeksforgeeks.org/find-a-peak-in-a-given-array/ c++ originally

// is finding just one peak...not the highest high

public class PeakUtil
{

    // A binary search based function that returns index of a peak element
    public static int findPeakUtil( List<Integer> arr, int low, int high, int n )
    {
        // Fin index of middle element
        int mid = low + ( high - low ) / 2; /* (low + high)/2 */

        System.out.println( "high " + high );
        System.out.println( "low " + low );
        System.out.println( "mid " + mid );

        // Compare middle element with its neighbours (if neighbours exist)
        if( ( mid == 0 || arr.get( mid - 1 ) <= arr.get( mid ) )
            && ( mid == n - 1 || arr.get( mid + 1 ) <= arr.get( mid ) ) )
        {
            System.out.println( "retuning mid " + mid );

            return mid;
        }

        // If middle element is not peak and its left neighbor is greater than it
        // then left half must have a peak element
        else if( mid > 0 && arr.get( mid - 1 ) > arr.get( mid ) )
        {
            System.out.println( "checking in  low " + low + " mid -1 " + ( mid - 1 ) );
            return findPeakUtil( arr, low, ( mid - 1 ), n );
        }

        // If middle element is not peak and its right neighbor is greater than it
        // then right half must have a peak element
        else
        {
            System.out.println( "checking in  mid + 1 " + ( mid + 1 ) + " high " + high );
            return findPeakUtil( arr, ( mid + 1 ), high, n );
        }
    }

    // A wrapper over recursive function findPeakUtil()
    public static int findPeak( List<Integer> arr, int n )
    {
        return findPeakUtil( arr, 0, n - 1, n );
    }

    /* Driver program to check above functions */
    public static void main( String[] args )
    {

        List<Integer> arr = new ArrayList<Integer>();
        arr.add( 1 );
        arr.add( 3 );
        arr.add( 201 );
        arr.add( 10 );
        arr.add( 1555 );
        arr.add( 12 );
        arr.add( 1 );

        int n = arr.size();
        int peakIndex = findPeak( arr, n );
        System.out.println( "Index of a peak point is " + peakIndex );
        System.out.println( "Peak point is " + arr.get( peakIndex ) );
    }

}
