
# Code from Chapter 3 of Machine Learning: An Algorithmic Perspective (2nd Edition)
# by Stephen Marsland (http://stephenmonika.net)

# You are free to use, change, or redistribute the code in any way you wish for
# non-commercial purposes, but please maintain the name of the original author.
# This code comes with no warranty of any kind.

# Stephen Marsland, 2008, 2014

# Demonstration of the Perceptron on the Pima Indian dataset
# This dataset is now on kaggle https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database

# Just press F5 - runs interactively in IDE

# Ref kaggle solution https://www.kaggle.com/code/pradeepgurav/pima-diabetes-98-accuracy/notebook

import os

import numpy as np

included_header = np.genfromtxt(os.getcwd() + '/diabetes.csv', delimiter=',')
pima_orig = included_header[1:]

print(pima_orig.shape)
print(pima_orig.ndim)

