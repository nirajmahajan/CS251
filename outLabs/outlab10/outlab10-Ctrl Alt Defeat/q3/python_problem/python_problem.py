## @package python_problem
# This program four different functions, all of them involving arrays
from sys import argv
from copy import deepcopy

##
# @param filename the string which contains the path of the file to be read
# @return a list of elements where each element is a line from file
#
# This function loads and reads a given file and stores each line of the file 
# in the form of a list
def make(filename):
	file = open (filename)
	l=[]
	for line in file:
		a=int(line)
		l.append(a)
	return l

##
# @param l a list of any data type, provided that ordering for such data exists
# @return the original list, but with elements sorted in ascending order
#
# This function implements the bubblesort algorithm to sort a given array, 
# this function builds the sorted array from end of the array 
def fun1(l):	
	size=len(l)
	for i in range(0,size-1):
		for j in range(0,size-i-1):
			if l[j] > l[j+1]:
				temp=l[j]
				l[j]=l[j+1]
				l[j+1]=temp
			
	return l

##
# @param l the sorted list (of integers) to be used for searching
# @param x any data type which can be converted to integer
# @return the number of iterations in which binary search finds a given integer
#
# This function finds the number of binary searches it takes to find 
# a given number in a list, but returns -1 if element is not found
def fun2(l,x):
	n=int(x)
	probes=0
	s=0
	e=len(l)-1
	flag=0
	while(s<=e):
		probes=probes+1
		m=(s+e)//2
		print ("Probe "+str(probes)+":",m,l[m])
		if(l[m]==n):
			flag=1
			break
		elif(l[m]>n):
			e=m-1
		elif(l[m]<n):
			s=m+1
	if(flag==1):
		return probes
	else:	
		return -1

##
# @param L any nxn matrix (list of 2 dimensions)
# @return the determinant of the input matrix
#
# This function computes the determinant of a given matrix, by 
# recursively calling itself and reducing the matrix size each time
def fun3(L):
	d=0
	n=len(L)
	if(n==1):
		return L[0][0]
	for i in range(0,n):
		A=deepcopy(L)
		for j in range(0,len(A)):
			A[j].pop(i)
		A.pop(0)
		d=d+L[0][i]*((-1)**i)*fun3(A)
	return d

# The main driver code for testing the functions defined in this file
L = make("data")
print (L)
L = fun1(L)
print ("\nAfter Fun1:")
print (L)
print ("\nFun2 Starts:")
ans = fun2(L, 48)
print ("Result of Fun2:", ans)

print ("\n")
# Change the elements and observe the result  
L = [[1, 2, 3], [10, 54, 23], [9, 27, 11]]
print(L)
ans = fun3(L)
print ("Result of Fun3:", ans)
