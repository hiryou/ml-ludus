
# Code from Chapter 3 of Machine Learning: An Algorithmic Perspective (2nd Edition)
# by Stephen Marsland (http://stephenmonika.net)

# You are free to use, change, or redistribute the code in any way you wish for
# non-commercial purposes, but please maintain the name of the original author.
# This code comes with no warranty of any kind.

# Stephen Marsland, 2008, 2014

# Demonstration of the Perceptron on the Pima Indian dataset
# This dataset is now on kaggle https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database

# Just press F5 - runs interactively in IDE

import pylab as plt
import os
import numpy as np


def plot_histogram(plt, dataset, xlabel: str, is_density=False):
    plt.figure()
    #print(dataset)
    plt.hist(dataset, density=is_density)  # density=False would make counts
    plt.ylabel('Count')
    plt.xlabel(xlabel)


included_header = np.genfromtxt(os.getcwd() + '/diabetes.csv', delimiter=',')
pima = included_header[1:]

# # plot an arbitrary pair of features against classes
# filter of datapoints belong to 1st class
indices0 = np.where(pima[:,8]==0)
# filter of datapoints belong to 2nd class
indices1 = np.where(pima[:,8]==1)
# Plot the feature 1 and 2 for each class
#pl.ion()
#plt.plot(pima[indices0,0], pima[indices0,1], 'go')
#plt.plot(pima[indices1,0], pima[indices1,1], 'rx')

# # histogram of Pregnancies
plot_histogram(plt, pima[:,0], '# of pregnancy')
# # histogram of Glucose
plot_histogram(plt, pima[:,1], 'Glucose level')
# # histogram of BloodPressure
plot_histogram(plt, pima[:,2], 'BloodPressure')
# # histogram of BMI
plot_histogram(plt, pima[:,5], 'BMI', is_density=True)

# # histogram of ages
plot_histogram(plt, pima[:,7], 'Age')


plt.show()
