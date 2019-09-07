import numpy as np
import matplotlib.pyplot as plt
from scipy.misc import derivative
from mpl_toolkits.mplot3d import Axes3D

num = 1000
def fn_plot1d(fn, x_min, x_max, filename):
    ref=np.linspace(x_min,x_max,num)
    f=np.vectorize(fn)
    temp=f(ref)
    plt.plot(ref,temp)
    plt.savefig(filename)
    plt.clf()

def nth_derivative_plotter(fn, n, x_min, x_max, filename):
    ref,step=np.linspace(x_min,x_max,num,retstep=True)
    bool_a=np.ones((num,),dtype=bool)
    temp=np.copy(ref)
    f=np.vectorize(fn)
    if(n%2==1):
        n1=n+4
    else:
        n1=n+5
    temp[bool_a]=derivative(f, temp[bool_a],step,n,order=n1)
    plt.plot(ref,temp)
    plt.savefig(filename)
    plt.clf()

def fn_plot2d(fn, x_min, x_max, y_min, y_max, filename):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    x = np.linspace(x_min,x_max,num)
    y = np.linspace(y_min,y_max,num)
    X, Y = np.meshgrid(x, y)
    f=np.vectorize(fn)
    zs = np.array(f(np.ravel(X), np.ravel(Y)))
    Z = zs.reshape(X.shape)
    ax.plot_surface(X, Y, Z)
    plt.savefig(filename)
    plt.clf()

def b(x):
    return g(abs(x))

def g(x):
    temp=h(2-x)
    return temp/(temp+h(x-1))

def h(x):
    if(x > 0):
        return np.exp(-1/(x**2))
    else:
        return 0

def twodsinc(x,y):
    temp=np.sqrt((x**2)+(y**2))
    if(temp<=0):
        return 1
    else:
        return np.sin(temp)/temp

fn_plot1d(b,-2,2,'fn1plot.png')
fn_plot2d(twodsinc, -1.5*np.pi,1.5*np.pi,-1.5*np.pi,1.5*np.pi,'fn2plot.png')
for x in range(10):
    filen="bd_"+str(x+1)+".png"
    nth_derivative_plotter(b,x+1,-2,2,filen)