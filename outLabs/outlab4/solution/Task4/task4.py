import numpy as np

def mean_filter(arr,kernel_size):
	a=np.cumsum(arr,dtype=float)
	k=int((kernel_size-1)/2)
	bool_a=np.ones((a.size,),dtype=bool)
	bool_a[:k]=False
	bool_a[arr.size-k:]=False
	temp=np.arange(0,arr.size)
	out=np.zeros((arr.size,),dtype=float)
	out[bool_a]=a[temp[bool_a]+k]-a[temp[bool_a]-k-1]
	out[np.logical_not(bool_a)]=0
	out[k]=a[kernel_size-1]
	return out/kernel_size

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
