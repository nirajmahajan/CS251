# import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--data", required=True)

args = parser.parse_args()
inpath = args.data

data = pd.read_csv(inpath)

instance_group = data.groupby('instance')

i=1

for instance_name, instance_data in instance_group:
	algo_group = instance_data.groupby('algorithm')

	for algo_name, algo_data in algo_group:
		if (algo_name == 'epsilon-greedy') :
			greedy_group = algo_data.groupby('epsilon')

			for epsilon_name, epsilon_data in greedy_group:
				plot_data = epsilon_data.groupby('horizon')
				x_cords = [x for x,y in plot_data]
				plot_data = plot_data.mean()
				y_cords = list(plot_data['REG'])
				pltlabel = 'epsilon-greedy '+ str(epsilon_name)
				plt.plot(x_cords, y_cords, label=pltlabel)

		else:
			plot_data = algo_data.groupby('horizon')
			x_cords = [x for x,y in plot_data]
			plot_data = plot_data.mean()
			y_cords = list(plot_data['REG'])
			pltlabel = algo_name
			plt.plot(x_cords, y_cords, label=pltlabel)

	plt.xscale('log')
	plt.yscale('log')
	plt.legend()
	plt.title('Figure '+str(i) +": " + instance_name)
	plt.xlabel('horizon')
	plt.ylabel('regret')
	plt.savefig('instance'+str(i)+".png")
	i = i+1
	plt.close()
