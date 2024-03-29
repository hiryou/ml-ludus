
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

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
from sklearn.metrics import confusion_matrix, accuracy_score, f1_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

from sandbox.ann.np_bare_ann import NeuralNet_By_Numpy


def do_confusion_matrix(y_train, y_pred, plt):
    plt.figure()
    cm = confusion_matrix(y_train, y_pred)
    sns.set(color_codes=True)
    sns.set(font_scale=1)
    sns.heatmap(cm, annot=True, fmt='g')


SMALL_TRAIN_DS = '/diabetes.csv'
BIG_TRAIN_DS = '/diabetes75pc_100_times.csv'
DATASET_CSV_FILE = os.getcwd() + BIG_TRAIN_DS

EPOCH = 49


df_orig = pd.read_csv(DATASET_CSV_FILE)
feature_names = df_orig.columns[:8].tolist()
print(f'-- all features: {feature_names}')

X = df_orig[feature_names]
y = df_orig.Outcome
#print(X.shape)
#print(y.shape)

# Assumed best chosen features from the ref solution
selected_features = ['Pregnancies', 'Glucose', 'BMI', 'DiabetesPedigreeFunction']
print(f'-- selected features: {selected_features}')
# StandardScaler() scales each dimension to 0-mean and unit variance e.g. var == 1
X = StandardScaler().fit_transform(X[selected_features])
# Splitting  data into training and testing
X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.25, # 25%
    stratify=y, # maintain balance between classes https://stackoverflow.com/questions/54600907/does-the-train-test-split-function-keep-the-balance-between-classes
    #random_state=1989
)
print(f'-- Train size = {X_train.shape[0]}')
print(f'-- Test size  = {X_test.shape[0]}')
train_0 = np.count_nonzero(y_train==0)
train_1 = np.count_nonzero(y_train==1)
test_0 = np.count_nonzero(y_test==0)
test_1 = np.count_nonzero(y_test==1)
print(f'-- in test: diabetic/non-diabetic  = {test_1/test_0}')
print(f'-- in train: diabetic/non-diabetic = {train_1/train_0}')
print(f'-- X_train shape: {X_train.shape}')
print(f'-- y_train shape: {y_train.shape}')

# start training
print('Training..')
nnet = NeuralNet_By_Numpy(X_train, y_train, batch_size=16, hidden_layers=[128])
for _ in range(EPOCH):
    nnet.iteration_train()
    y_pred = nnet.iteration_predict()
    y_pred = np.where(y_pred > 0.5, 1, 0)
    print('  -- Accuracy: {:.2f}%'.format(accuracy_score(y_train, y_pred) * 100))
    print('  -- F1      : {:.2f}%'.format(f1_score(y_train, y_pred) * 100))
# confusion matrix over training data
y_pred = nnet.predict(X_train)
y_pred = np.where(y_pred > 0.5, 1, 0)
do_confusion_matrix(y_train, y_pred, plt)

# test perf
print('Testing..')
y_pred = nnet.predict(X_test)
y_pred = np.where(y_pred > 0.5, 1, 0)
print('  -- Accuracy: {:.2f}%'.format(accuracy_score(y_test, y_pred) * 100))
print('  -- F1      : {:.2f}%'.format(f1_score(y_test, y_pred) * 100))
# confusion matrix over training data
do_confusion_matrix(y_test, y_pred, plt)

# check training perf
#train_pred = nnet.predict(X_train)
#train_pred = np.where(train_pred > 0.5, 1, 0)
# train: confusion matrix
#train_cm = confusion_matrix(y_train, train_pred)
#print(train_cm)

plt.show()
