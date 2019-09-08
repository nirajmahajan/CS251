import numpy as np
from scipy.cluster.vq import kmeans2
import argparse
import imageio

parser = argparse.ArgumentParser()
parser.add_argument("--input", required=True)
parser.add_argument("--k", required=True, type = int)
parser.add_argument("--output", required=True)

args = parser.parse_args()
inpath = args.input
outpath = args.output
karg = args.k

inim = np.asarray(imageio.imread(inpath), dtype='float')
r,c,dim = inim.shape
if (not dim == 3) :
	inim = inim[:,:,np.s_[:3]]
	dim=3

inim = inim.reshape(r*c, dim)
centroid, label = kmeans2(inim, karg, iter=300, minit='++')
outim = np.empty((r*c, dim))
for i in range(r*c):
	outim[i,:] = centroid[label[i],:]
outim = np.asarray(outim, dtype='uint8')
outim = outim.reshape(r,c,dim)
imageio.imwrite(outpath, outim)