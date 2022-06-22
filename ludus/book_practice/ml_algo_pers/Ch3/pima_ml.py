
# Code from Chapter 3 of Machine Learning: An Algorithmic Perspective (2nd Edition)
# by Stephen Marsland (http://stephenmonika.net)

# You are free to use, change, or redistribute the code in any way you wish for
# non-commercial purposes, but please maintain the name of the original author.
# This code comes with no warranty of any kind.

# Stephen Marsland, 2008, 2014

# Demonstration of the Perceptron on the Pima Indian dataset
# This dataset is now on kaggle https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database

# Just press F5 - runs interactively in IDE

import os

import numpy as np

import pcn

included_header = np.genfromtxt(os.getcwd() + '/diabetes.csv', delimiter=',')
pima = included_header[1:]

# Perceptron training on the original dataset
print("Output on original data")
p = pcn.pcn(pima[:,:8], pima[:,8:9])
p.pcntrain(pima[:,:8], pima[:,8:9], 0.25, 100)
p.confmat(pima[:,:8], pima[:,8:9])

# Various preprocessing steps

# if # of pregnancies > 8, make it as 8
pima[np.where(pima[:,0]>8),0] = 8

# segment ages into age groups instead of definite numbers
pima[np.where(pima[:,7]<=30),7] = 1
pima[np.where((pima[:,7]>30) & (pima[:,7]<=40)),7] = 2
pima[np.where((pima[:,7]>40) & (pima[:,7]<=50)),7] = 3
pima[np.where((pima[:,7]>50) & (pima[:,7]<=60)),7] = 4
pima[np.where(pima[:,7]>60),7] = 5

# all features: transform to having mean == 0
pima[:,:8] = pima[:,:8] - pima[:,:8].mean(axis=0)
# then divided by variance
pima[:,:8] = pima[:,:8] / pima[:,:8].var(axis=0)
# => now all features have 0-mean & unit-variance

print('-- all features: mean = {}'.format(pima[:,:8].mean(axis=0)))
print('-- all features: var  = {}'.format(pima[:,:8].var(axis=0)))
#print pima.max(axis=0)
#print pima.min(axis=0)

trainin = pima[::2,:8]
testin = pima[1::2,:8]
traintgt = pima[::2,8:9]
testtgt = pima[1::2,8:9]

# Perceptron training on the preprocessed dataset
print("Output after preprocessing of data")
p1 = pcn.pcn(trainin,traintgt)
p1.pcntrain(trainin,traintgt,0.25,100)
p1.confmat(testin,testtgt)
