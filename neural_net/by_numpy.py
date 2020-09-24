from builtins import classmethod

import numpy as np
from datetime import datetime as dt

"""
Inspired by https://repl.it/repls/OrganicVainDoom#main.py
"""


class NeuralNet(object):
    train_cnt = 0
    epoch = 0
    eta = 0.5

    # TODO make constructor-only param
    h_layers = [3]

    X = None
    Y = None
    X_size = 0  # neural count
    Y_size = 0  # neural count

    # hidden layers & last output layers
    W = list()
    H = list()

    def __init__(self, X, Y, epoch):
        self.X, self.Y = self.__scaled(X, Y)
        self.train_cnt = len(self.X)
        self.X_size = len(self.X[0])
        self.Y_size = len(self.Y[0])
        self.epoch = epoch

        self.h_layers.append(self.Y_size)
        left_neuron_cnt = self.X_size
        for neuron_cnt in self.h_layers:
            ww = np.random.randn(left_neuron_cnt, neuron_cnt)
            hh = np.full((self.train_cnt, neuron_cnt), -0.0001)
            self.W.append(ww)
            self.H.append(hh)
            left_neuron_cnt = neuron_cnt
            pass

    @staticmethod
    def sigmoid(s):
        return 1 / (1 + np.exp(-s))

    @staticmethod
    def sigmoid_prime(sig):
        return sig * (1 - sig)

    def get_train_loss(self):
        Y = self.__scaled_back(self.Y)
        H_last = self.__scaled_back(self.H[-1])
        return np.mean(
            np.square(Y - H_last)
        )
        pass

    def do_train(self):
        for i in range(self.epoch):
            self.__forward(self.X)
            self.__backward()
            #print("epoch = {}: loss = {}".format( i, str(self.get_train_loss()) ))

    def __scaled(self, X, Y):
        # normalize
        # max 24h a day
        # max score = 100
        return X/24, Y/100

    def __scaled_back(self, Y):
        # max score = 100
        return Y*100

    def __forward(self, X):
        left_mt = X
        for idx in range(len(self.h_layers)):
            net_H_idx = np.dot(left_mt, self.W[idx])
            self.H[idx] = self.sigmoid(net_H_idx)
            left_mt = self.H[idx]

        return self.H[-1]

    def __backward(self):
        # delta: start initially from layer H2 (output)
        delta_H = [None for idx in range(len(self.h_layers))]
        delta_H[-1] = (self.Y - self.H[-1]) * self.sigmoid_prime(self.H[-1])
        # then delta: reversed loop from semi-last element -> beginning
        for idx in range(len(self.h_layers)-2, -1, -1):
            delta_H[idx] = delta_H[idx+1].dot(self.W[idx+1].T) * self.sigmoid_prime(self.H[idx])
            pass

        # update weights: start from right most layer
        for idx in range(len(self.h_layers) - 1, 0, -1):
            self.W[idx] += (1 / self.train_cnt) * self.eta * self.H[idx-1].T.dot(delta_H[idx])
            pass
        # update weights: at layer W0 back to input
        self.W[0] += (1 / self.train_cnt) * self.eta * self.X.T.dot(delta_H[0])


f = open('study-sleep-grade.txt')
lines = f.readlines()
f.close()
# print(lines)

x_all = []
y_all = []
for line in lines:
    p = line.strip().split(", ")
    y = p[0].strip().split(' ')
    x = p[1].strip().split(' ')
    x_all.append(x)
    y_all.append(y)

INP = np.array((x_all[:-1]), dtype=float)
Y = np.array((y_all[:-1]), dtype=float)
nn = NeuralNet(INP, Y, epoch=1000)

print("-------------------------")
print("training ...")
tic = dt.now().microsecond
nn.do_train()
toc = dt.now().microsecond
print("-------------------------")
print("train loss = {}".format( str(nn.get_train_loss()) ))
print("Train taken {} micro-secs".format('{:,}'.format(toc - tic)))

