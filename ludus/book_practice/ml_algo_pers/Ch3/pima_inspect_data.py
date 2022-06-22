
# Code from Chapter 3 of Machine Learning: An Algorithmic Perspective (2nd Edition)
# by Stephen Marsland (http://stephenmonika.net)

# You are free to use, change, or redistribute the code in any way you wish for
# non-commercial purposes, but please maintain the name of the original author.
# This code comes with no warranty of any kind.

# Stephen Marsland, 2008, 2014

# Demonstration of the Perceptron on the Pima Indian dataset
# This dataset is now on kaggle https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database

# Just press F5 - runs interactively in IDE

# Ref kaggle solution's notebook https://www.kaggle.com/code/pradeepgurav/pima-diabetes-98-accuracy/notebook

# Using & understanding violin plot https://ajaytech.co/2020/08/09/violin-plot/
# More on violin plots https://mode.com/blog/violin-plot-examples

import pylab as plt
import os
import numpy as np
import pandas as pd
import seaborn as sns


def plot_histogram(plt, dataset, xlabel: str, is_density=False):
    plt.figure()
    #print(dataset)
    plt.hist(dataset, density=is_density)  # density=False would make counts
    plt.ylabel('Count')
    plt.xlabel(xlabel)


DATASET_CSV_FILE = os.getcwd() + '/diabetes.csv'


df_orig = pd.read_csv(DATASET_CSV_FILE)
feature_names = df_orig.columns[:8].tolist()
print(feature_names)

__pima_w_header = np.genfromtxt(DATASET_CSV_FILE, delimiter=',')
pima = __pima_w_header[1:]

"""
# # plot an arbitrary pair of features against classes
# filter of datapoints belong to 1st class
indices0 = np.where(pima[:,8]==0)
# filter of datapoints belong to 2nd class
indices1 = np.where(pima[:,8]==1)
# Plot the feature 1 and 2 for each class
#pl.ion()
plt.plot(pima[indices0,0], pima[indices0,1], 'go')
plt.plot(pima[indices1,0], pima[indices1,1], 'rx')
"""

# histogram of Pregnancies
#plot_histogram(plt, pima[:,0], '# of pregnancy')
# histogram of Glucose
#plot_histogram(plt, pima[:,1], 'Glucose level')
# histogram of BloodPressure
#plot_histogram(plt, pima[:,2], 'BloodPressure')
# histogram of BMI
#plot_histogram(plt, pima[:,5], 'BMI', is_density=True)

# correlation between features
# plt.figure()
# sns.heatmap(df_orig.corr(), annot=True, cmap='YlGnBu')
# fig = plt.gcf()
# fig.set_size_inches(10, 8)

# violin plots of different features against outcome
plt.figure()
sns.violinplot(x='Outcome', y='Pregnancies', data=df_orig, palette='muted', inner='quartile')
plt.figure()
sns.violinplot(x='Outcome', y='Glucose', data=df_orig, palette='muted')
# plt.figure()
# sns.violinplot(x='Outcome', y='SkinThickness', data=df_orig, palette='muted')
# plt.figure()
# sns.violinplot(x='Outcome', y='BloodPressure', data=df_orig, palette='muted')
# plt.figure()
# sns.violinplot(x='Outcome', y='Insulin', data=df_orig, palette='muted')
plt.figure()
sns.violinplot(x='Outcome', y='BMI', data=df_orig, palette='muted')
plt.figure()
sns.violinplot(x='Outcome', y='DiabetesPedigreeFunction', data=df_orig, palette='muted')
# plt.figure()
# sns.violinplot(x='Outcome', y='Age', data=df_orig, palette='muted')

# histogram of ages
#plot_histogram(plt, pima[:,7], 'Age')


plt.show()
