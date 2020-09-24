from builtins import classmethod

import numpy as np
import time

"""
@Credit https://repl.it/repls/OrganicVainDoom#main.py
"""


class NeuralNet(object):
    train_cnt = 0
    epoch = 0
    eta = 0.5

    X = None
    Y = None
    X_size = 0  # neural count

    # 1st hidden layer
    W0 = None
    Z0_size = 3  # neural count
    H0 = None
    # 2nd hidden layer
    W1 = None
    Z1_size = 2  # neural count
    H1 = None
    # output later
    W2 = None
    Z2_size = 0  # neural count
    H2 = None

    def __init__(self, X, Y, epoch):
        self.X, self.Y = self.__scaled(X, Y)
        self.train_cnt = len(self.X)
        self.X_size = len(self.X[0])
        self.epoch = epoch
        self.Z2_size = len(self.Y[0])
        # init weights
        self.W0 = np.random.randn(self.X_size, self.Z0_size)
        self.W1 = np.random.randn(self.Z0_size, self.Z1_size)
        self.W2 = np.random.randn(self.Z1_size, self.Z2_size)
        # init output at each later
        self.H0 = np.full((self.train_cnt, self.Z0_size), -0.0001)
        self.H1 = np.full((self.train_cnt, self.Z1_size), -0.0001)
        self.H2 = np.full((self.train_cnt, self.Z2_size), -0.0001)

    @staticmethod
    def sigmoid(s):
        return 1 / (1 + np.exp(-s))

    @staticmethod
    def sigmoid_prime(sig):
        return sig * (1 - sig)

    def get_train_loss(self):
        Y = self.__scaled_back(self.Y)
        H2 = self.__scaled_back(self.H2)
        return np.mean(
            np.square(Y - H2)
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
        # to layer H0
        net_H0 = np.dot(X, self.W0)
        self.H0 = self.sigmoid(net_H0)

        # to layer H1
        net_H1 = np.dot(self.H0, self.W1)
        self.H1 = self.sigmoid(net_H1)

        # to layer H2 (output)
        net_H2 = np.dot(self.H1, self.W2)
        self.H2 = self.sigmoid(net_H2)

        return self.H2

    def __backward(self):
        # delta: at layer H2 (output)
        delta_H2 = (self.Y - self.H2) * self.sigmoid_prime(self.H2)
        # delta: at layer H1
        delta_H1 = delta_H2.dot(self.W2.T) * self.sigmoid_prime(self.H1)
        # delta: at layer H0
        delta_H0 = delta_H1.dot(self.W1.T) * self.sigmoid_prime(self.H0)

        # update weights: at layer W2
        self.W2 += (1 / self.train_cnt) * self.eta * self.H1.T.dot(delta_H2)
        # update weights: at layer W1
        self.W1 += (1 / self.train_cnt) * self.eta * self.H0.T.dot(delta_H1)
        # update weights: at layer W0
        self.W0 += (1 / self.train_cnt) * self.eta * self.X.T.dot(delta_H0)


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
tic = time.perf_counter_ns()
nn.do_train()
toc = time.perf_counter_ns()
print("-------------------------")
print("train loss = {}".format( str(nn.get_train_loss()) ))
print("Train taken {} nano-secs".format('{:,}'.format(toc - tic)))

