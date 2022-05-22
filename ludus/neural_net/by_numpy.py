from datetime import datetime as dt

import numpy as np

"""
Inspired by https://repl.it/repls/OrganicVainDoom#main.py
"""


class NeuralNet(object):
    eta = 0.5

    def __init__(self, X, Y, epoch):
        self.X, self.Y = self.__scaled(X, Y)
        self.train_cnt = len(self.X)
        self.X_size = len(self.X[0])  # neuron count
        self.Y_size = len(self.Y[0])  # neuron count
        self.epoch = epoch

        # TODO make constructor-only param
        # len of this array = number of hidden layers; each num is # of neurons in each layer
        self.h_layers = [3]
        # to simplify algo, we consider output Y as last element of h_layers
        self.h_layers.append(self.Y_size)

        self.W = list()  # weight matrix for each layer: hidden layers & last output layer
        self.H = list()  # matrix [#datapoint x neuron count] for each layer: hidden layers & last output layer

        left_neuron_cnt = self.X_size
        for neuron_cnt in self.h_layers:
            ww = np.random.randn(left_neuron_cnt, neuron_cnt)
            hh = np.full((self.train_cnt, neuron_cnt), -0.0001)
            self.W.append(ww)
            self.H.append(hh)
            left_neuron_cnt = neuron_cnt

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
        delta_H = [None for _ in range(len(self.h_layers))]
        # delta: start initially from last layer H[-1] (output)
        delta_H[-1] = (self.Y - self.H[-1]) * self.sigmoid_prime(self.H[-1])

        # then delta: reversed loop from semi-last element (last hidden layer) -> 1st hidden layer
        for idx in range(len(self.h_layers)-2, -1, -1):
            delta_H[idx] = delta_H[idx+1].dot(self.W[idx+1].T) * self.sigmoid_prime(self.H[idx])

        # update weights: from right most layer to one before 1st hidden layer
        for idx in range(len(self.h_layers)-1, 0, -1):
            #self.W[idx] += (1 / self.train_cnt) * self.eta * self.H[idx-1].T.dot(delta_H[idx])
            self.W[idx] += self.H[idx-1].T.dot(delta_H[idx])
        # update weights: at layer W0 back to input
        #self.W[0] += (1 / self.train_cnt) * self.eta * self.X.T.dot(delta_H[0])
        self.W[0] += self.X.T.dot(delta_H[0])


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

INP = np.array((x_all), dtype=float)
Y = np.array((y_all), dtype=float)
nn = NeuralNet(INP, Y, epoch=100)

print("-------------------------")
print("training ...")
tic = dt.now().microsecond
nn.do_train()
toc = dt.now().microsecond
print("-------------------------")
print("train loss = {:.2f}/100 (max score = 100)".format(nn.get_train_loss()))
print("Train taken {} micro-secs".format('{:,}'.format(toc - tic)))

