import numpy as np

def mean_filter(arr,k):
	bool_a=np.ones((arr.size,),dtype=bool)
	a=np.append(np.zeros((k+1,)),np.cumsum(arr,dtype=float))
	bool_a[arr.size-k:]=False
	temp=np.arange(0,arr.size)
	out=np.zeros((arr.size,),dtype=float)
	out[bool_a]=a[temp[bool_a]+2*k+1]-a[temp[bool_a]]
	bool_a=np.logical_not(bool_a)
	out[bool_a]=a[arr.size+k]-a[temp[bool_a]]
	return out/(2*k+1)

def generate_sin_wave(period,range_,num):
	(xmin,xmax) = range_
	temp = np.linspace(xmin,xmax,num)
	temp.astype('float64')
	f = lambda x: np.sin(2 * np.pi * x / period)
	return f(temp)

def noisify(array,var):
	temp = np.copy(array)
	noise = np.random.normal(0,np.sqrt(var),array.size)
	return temp + noise
