import numpy as np
import task4 as t4
import matplotlib.pyplot as plt

def driver():
	clean_sin = t4.generate_sin_wave(2,(-2,8),1000)
	ref = np.linspace(-2,8,1000)
	plt.plot(ref,clean_sin)
	plt.savefig('clean_sin.png')
	plt.clf()
	dirty_sin = t4.noisify(clean_sin,0.05**2)
	plt.plot(ref,dirty_sin)
	plt.savefig('dirty_sin.png')
	plt.clf()
	cleaned_sin = t4.mean_filter(dirty_sin,3)
	plt.plot(ref,cleaned_sin)
	plt.savefig('cleaned_sin.png')
	plt.clf()
