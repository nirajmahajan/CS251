/** @file array.cpp 
* This code is used to print spiral segments of a 7x7 array,
* its minimum element and prints its selected columns
*/
#include <iostream>
#include <stdlib.h>

using namespace std;

/** \param myArr the array to be filled
  * \return Always returns 0 (useless to the code) after filling
  * 
  * This function is used to fill a given
  * 7x7 matrix with random numbers 
  * between 20 and 419 (both inclusive)
*/
int fillArray(int myArr[7][7])                 
{
	for(int i=0;i<7;i++)
	{
		for(int j=0;j<7;j++)                    
		{
			myArr[i][j]=20+rand()%400;              
		}
	}
	return 0;
}

/** \param myArr the array whose spiral path will be printed
  * 
  * This function prints the straight segments
  * of the path in a 2D array which starts 
  * from the top left corner (0,0), spiralling 
  * into the center of the matrix
*/
void printSpiral(int myArr[7][7])
{
	cout << "Array Spiral Function." << endl;
	for(int j=0;j<1;j++)                           
    {                                              
        for(int i=0;i<7;i++)
        {
            cout<< myArr[i][j]<< "  ";
        }

    }
    cout << endl;

    for(int i=6;i<7;i++)                          
    {                                            
        for(int j=1;j<7;j++)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for(int j=6;j<7;j++)                          
    {                                             
        for(int i=5;i>=0;i--)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int i=0;i<1;i++)                          
    {                                              
        for (int j=5;j>=1;j--)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int j=1; j<2;j++)
    {                                             
        for (int i=1; i<6;i++)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int i=5; i<6;i++)                        
    {
        for (int j=2; j<6; j++)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int j=5; j<6; j++)                        
    {
        for (int i=4; i>0; i--)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int i=1; i<2; i++)                        
    {
       for (int j=4; j>1; j--)
       {
           cout << myArr[i][j] << "  ";
       }
    }
    cout << endl;

    for (int j=2; j<3; j++)                        
    {
        for (int i=2; i<5; i++)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int i=4; i<5; i++)                        
    {
        for (int j=3; j<5; j++)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int j=4; j<5; j++)                        
    {
        for (int i=3; i>1; i--)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int i=2; i<3; i++)                        
    {
        for (int j=3; j<4; j++)
        {
            cout << myArr[i][j] << "  ";
        }
    }
    cout << endl;

    for (int i=3; i<4; i++)                         
    {
        for (int j=3; j<4; j++)
        {
            cout << myArr[i][j] << "  ";
        }
    }
  cout << endl << endl;
}

void printCol(int myArr[7][7], int s, int e)                     
{  
	cout << "Column Output" << endl;                
	for (int j=s-1; j<e-1; j++)
	{                                               
		for (int i=0; i<7; i++)
		{
			cout << myArr[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl << endl;
}

/** \param myArr the array to be analysed
  * \return the minimum value present in the 2D array
  * 
  * This function computes the smallest value of all
  * the elements in the 2D array
*/
int findMin (int myArr[7][7])
{
	int i,j;
	int min = myArr[0][0];

	for (i=0; i<7; i++)                             
	{
		for (j=0; j<7; j++)
		{
			if(myArr[i][j] < min)                   
			{                                       
				min = myArr[i][j];
			}                                       
		}                                           

	}
    return min;
}

/** \param myArr the array to be analysed
  * \return Average value of array elements
  * 
  * This function returns the average of all
  * the elements of the 2D array, truncated to 
  * the nearest lower integer (or floored value)
*/
int findAverage (int myArr[7][7])                  
{                                                   
	int i,j;
	int sum = 0;

	for (i=0; i<7; i++)                             
	{
	    for (j=0; j<7; j++)
	    {
	        sum = sum + myArr[i][j];
	    }

	}
    return sum/49;       
}

void printArray (int myArr[7][7])                     
{
    cout << "Normal Array Output" << endl;

    for (int i =0; i<7; i++)
    {
        for (int j=0; j<7; j++)
        {
            cout << myArr[i][j] << "  ";
        }
        cout << endl;
    }
    cout << endl;                                   
    cout << endl;
}

int main(){
	
	int myArr [7][7];
	fillArray(myArr);                               
	printArray(myArr);
	printSpiral(myArr);
	printCol(myArr, 2, 5);
	cout<<"Min:"<<findMin(myArr);
	cout<<" Average:"<<findAverage(myArr);
    cout<<endl;

	return 0;
}
 