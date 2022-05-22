import os
from datetime import datetime as dt

import numpy as np

"""
Inspired by https://repl.it/repls/OrganicVainDoom#main.py
"""


class NeuralNet(object):
    r"""
    This is the bare NN impl to help remind you of how forward/back propagation work in terms of code data structure

    Example: we want to map 2 neuron input -> 1 neuron output (input is a 2-d vector, output is a scalar). Let say we
    have 2 hidden neural layers, having 3 & 4 neurons in that order. Also, assume the number of training datapoint = 99.
    This gives us the following NN:

    [2 neurons] -> [3 neurons] -> [4 neurons] -> [1 neuron], or

    X      W0    H0   W1   H1   W2  Y
    (2)    ->   (3)   ->  (4)   -> (1)
    where:
        X is a [99x2] matrix: 99 datapoints, each is a 2-d vector
        W0 is a  [2x3] matrix, W1 is a  [3x4] matrix, etc
        H0 is a [99x3] matrix, H1 is a [99x4] matrix, etc
        Y is a [99x1] matrix: 99 datapoints, each is a 1-d vector

    To simplify the later for-loop computation, think of Y as H2 (to be added to the list of hidden layers). The idea is
    that weight matrix Wi goes with hidden layer Hi:

    X[99x2]  | W0[2x3]  H0[99x3]  | W1[3x4]   H1[99x4]  | W2[4x1]  H2[99x1]  |
        (2)  |   ->          (3)  |   ->           (4)  |   ->          (1)  |

    Following is the data structure we would have:

    * h_layers = [3, 4, 1]  # count of neurons in each Hi layer
    * W = [  W0[2x3],  W1[3x4],  W2[4x1] ]    # list of weight matrix toward each Hi layer
    * H = [ H0[99x3], H1[99x4], H2[99x1] ]    # 99 datapoints, each produces an activation vector Hi at each Hi layer

    Algo: for each training epoch (training iteration):
    * Forward:
        X[99x2]  . W0[2x3] ~sigmoid -> H0[99x3]
        H0[99x3] . W1[3x4] ~sigmoid -> H1[99x4]
        H1[99x4] . W2[4x1] ~sigmoid -> H2[99x1]
    * Back propagation:
        * Hi -> delta_Hi
            delta_H = [ delta_H0[99x3], delta_H1[99x4], delta_H2[99x1] ]    # track the big Delta at each H layer
            ---------
            (Y[99x1] - H2[99x1])               * sigmoid_prime(H2[99x1]) -> delta_H2[99x1]
            ---------
            delta_H2[99x1] . W2_transpose[1x4] * sigmoid_prime(H1[99x4]) -> delta_H1[99x4]
            delta_H1[99x4] . W1_transpose[4x3] * sigmoid_prime(H0[99x3]) -> delta_H0[99x3]
        * delta_Hi -> smoothing/updating Wi
            H1_transpose[4x99] . delta_H2[99x1] ~> W2[4x1]
            H0_transpose[3x99] . delta_H1[99x4] ~> W1[3x4]
            ---------
            X_transpose[2x99]  . delta_H0[99x3] ~> W0[2x3]
    """

    eta = 0.5

    def __init__(self, X, Y, epoch):
        self.X, self.Y = self.__scaled(X, Y)
        self.epoch = epoch

        # TODO make constructor-only param
        # len of this array = number of hidden layers; each num is # of neurons in each layer
        # to simplify algo, we consider output Y as last element of h_layers also
        Y_size = len(self.Y[0])  # neuron count of Y
        h_layers = [3, Y_size]

        self.W = list()  # weight matrix for each layer: hidden layers & last output layer
        self.H = list()  # matrix [#datapoint x neuron count] for each layer: hidden layers & last output layer

        X_size = len(self.X[0])  # neuron count of X
        left_neuron_cnt = X_size
        for neuron_cnt in h_layers:
            ww = np.random.randn(left_neuron_cnt, neuron_cnt)
            hh = np.full((len(self.X), neuron_cnt), -0.0001)
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
            print("--epoch={}, loss = {}".format(i, self.get_train_loss()))

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
        for idx in range(len(self.H)):
            net_H_idx = np.dot(left_mt, self.W[idx])
            self.H[idx] = self.sigmoid(net_H_idx)
            left_mt = self.H[idx]

        return self.H[-1]

    def __backward(self):
        delta_H = [None for _ in range(len(self.H))]
        # delta: start initially from last layer H[-1] (output)
        delta_H[-1] = (self.Y - self.H[-1]) * self.sigmoid_prime(self.H[-1])
        # then delta: reversed loop from semi-last element (last hidden layer) -> 1st hidden layer
        for idx in range(len(self.H)-2, -1, -1):
            delta_H[idx] = delta_H[idx+1].dot(self.W[idx+1].T) * self.sigmoid_prime(self.H[idx])

        # update weights: from right most layer to one before 1st hidden layer
        for idx in range(len(self.W)-1, 0, -1):
            #self.W[idx] += (1 / self.train_cnt) * self.eta * self.H[idx-1].T.dot(delta_H[idx])
            self.W[idx] += self.H[idx-1].T.dot(delta_H[idx])
        # update weights: at layer W0 back to input
        #self.W[0] += (1 / self.train_cnt) * self.eta * self.X.T.dot(delta_H[0])
        self.W[0] += self.X.T.dot(delta_H[0])


f = open(os.getcwd()+'/hello_world/study-sleep-grade.txt')
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

