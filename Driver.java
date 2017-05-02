package cosc_314_programming_assignment_3;

import java.util.Scanner;

/**
 * @author      Michael Boettner
 *              E01474622
 *              COSC 314
 *              Programming Assignment #3
 * @version     1.0
 * Driver Class containing main method to determine whether a simple 
 * connected undirected graph is bipartite or not, and outputting the vertex
 * set in case the graph is bipartite.
 */
public class Driver {
    
    /**
    * Keyboard input
    */
    static Scanner keyboard = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**
        * Number of vertices in the simple connected undirected graph
        */
        int numberOfVertices;
        
        /**
        * 2D array to store the adjacency matrix
        */
        boolean[][] myMatrix;
        
        /**
        * 1D array to store the chromatic values (char 'r' or 'b')
        */
        char[] chromaticValue;
        
        /**
        * Used to break the bipartition-checking algorithm early if it is
        * determined that the graph is not bipartite. Also used to
        * determine whether the vertex set should be printed in the case
        * of a bipartite graph
        */
        boolean isBipartite;
        
        /**
        * Stores each row of the adjacency matrix as it is entered by the user
        */
        String rowString;
        
        /**
        * Tracks the number of "red" vertices, used for comma placement in
        * output of the bi-partition
        */
        int rCount;
        
        /**
        * Tracks the number of "blue" vertices, used for comma placement in
        * output of the bi-partition
        */
        int bCount;
        
        /**
        * Controls program loop to allow additional matrices to be entered and
        * checked for bipartition
        */
        boolean runAgain = true;
        
        /**
        * User input to decide whether to run program again
        */
        char userAnswer;
        
        System.out.println("******************************************");
        System.out.println("*  Determine whether a simple connected  *");
        System.out.println("*     undirected graph is bipartite      *");
        System.out.println("*                                        *");
        System.out.println("*            Michael Boettner            *");
        System.out.println("******************************************");
        
        while(runAgain)
        {
            isBipartite = true;
            bCount = 0;
            rCount = 0;
                    
            System.out.print(
                    "\nEnter number of vertices (positive integer): ");
        
            numberOfVertices = keyboard.nextInt();

            //initialize new user-specified-sized 2D array
            myMatrix = new boolean[numberOfVertices][numberOfVertices];

            //initialize 1D array to store color assignments
            chromaticValue = new char[numberOfVertices];

            System.out.println("\n" + "Enter the adjacency matrix one row at "
                    + "a time (no spaces)");

            //loop prompts user to enter each row, one at a time
            for(int i = 0; i < numberOfVertices; i++)
            {
                System.out.print("Row " + (i+1) + ": ");

                rowString = keyboard.next();

                //convert each string character to boolean and store
                for(int j = 0; j < numberOfVertices; j++)
                {
                    myMatrix[i][j] = rowString.charAt(j) - 48 != 0;
                }
            }

            //logic to determine if the graph is bipartite
            for(int i = 0; i < numberOfVertices; i++)
            {
                /*Because there are only two colors allowed in a bipartite
                    graph, we start by assigning the first vertex red, and\
                    then its adjacent vertices blue. In a bipartite graph, the
                    remaining non-adjacent vertices will also be red.
                    */
                if(chromaticValue[i] != 'b')
                {
                    chromaticValue[i] = 'r';
                    rCount++;
                }
                
                /*nested loop to process the 2D array and compare adjacent
                    vertices
                    */
                for(int j = 0; j < numberOfVertices; j++)
                {
                    /*if the matrix value is true (1) we continue to check/
                        determine color value assignments
                        */
                    if(myMatrix[i][j])
                    {
                        /*if the vertex is red, we check to make sure it is
                            not adjacent to another red vertex
                            */
                        if(chromaticValue[i] == 'r')
                        {
                            if(chromaticValue[j] == 'r')
                            {
                                isBipartite = false;
                                break; //not bipartite
                            }
                            /*the vertex in question has not yet been colored,
                                so we assign it the opposite color, which is
                                blue
                               */
                            chromaticValue[j] = 'b';
                        }
                        /*else if the vertex is blue, we check to make sure it
                            is not adjacent to another blue vertex
                            */
                        else
                        {
                            if(chromaticValue[j] == 'b')
                            {
                                isBipartite = false;
                                break; //not bipartite
                            }
                            chromaticValue[j] = 'r';
                        }     
                    }
                }
                /*used to short circuit out of the loop if the graph is
                    determined to not be bipartite
                    */
                if(!isBipartite)
                {
                    break;
                }
            }

            //prints the bipartition of the vertex set if graph is bipartite
            if(isBipartite)
            {
                //calculate the number of blue-colored vertices
                bCount = numberOfVertices - rCount;

                System.out.print("\nThe graph is bipartite."
                        + " The bi-partition is:\n{");

                for(int i = 0; i < numberOfVertices; i++)
                {
                    if(chromaticValue[i] == 'r')
                    {
                        System.out.print((char)(i+97));
                        //check to see if comma is needed
                        if(rCount > 1)
                        {
                            System.out.print(", ");
                            rCount--;
                        }
                    }    
                }

                System.out.print("} and {");

                for(int i = 0; i < numberOfVertices; i++)
                {
                    if(chromaticValue[i] == 'b')
                    {
                        System.out.print((char)(i+97));
                        if(bCount > 1)
                        {
                            System.out.print(", ");
                            bCount--;
                        }
                    }    
                }

                System.out.println("}");
            }
            //else displays a message if the graph is not bipartite
            else
                System.out.println("\nThe graph is not bipartite.");
            
            System.out.print("\nRun again? (y/n): ");
            
            userAnswer = keyboard.next().charAt(0);
            
            if(userAnswer != 'y' && userAnswer != 'Y')
                runAgain = false;
        }
    }
}

/*
run:
******************************************
*  Determine whether a simple connected  *
*     undirected graph is bipartite      *
*                                        *
*            Michael Boettner            *
******************************************

Enter number of vertices (positive integer): 6

Enter the adjacency matrix one row at a time (no spaces)
Row 1: 000110
Row 2: 000111
Row 3: 000011
Row 4: 110000
Row 5: 111000
Row 6: 011000

The graph is bipartite. The bi-partition is:
{a, b, c} and {d, e, f}

Run again? (y/n): y

Enter number of vertices (positive integer): 6

Enter the adjacency matrix one row at a time (no spaces)
Row 1: 010100
Row 2: 101001
Row 3: 010100
Row 4: 101010
Row 5: 000101
Row 6: 010010

The graph is not bipartite.

Run again? (y/n): n
BUILD SUCCESSFUL (total time: 1 minute 16 seconds)
*/