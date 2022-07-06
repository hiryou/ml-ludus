import math

import numpy as np


class NeuralNet_By_Numpy(object):

    eta = 0.5   # learning rate

    def __init__(self, X, Y, batch_size: int = None, hidden_layers: [int] = []):
        """
        Setup neural network

        Parameters
        ----------
        X : ndarray of shape (n_samples, n_features)

        Y : ndarray of shape (n_samples,) or (n_samples, n_features)

        batch_size: number of training samples to work through before updating weights

        hidden_layers: array of number of neurons in each hidden layers, default is [] as no hidden layer
        """

        assert X.ndim <= 2
        assert Y.ndim <= 2
        assert X.shape[0] == Y.shape[0]
        assert batch_size is None or batch_size > 0
        self.Y_ndim = Y.ndim

        # batch -vs- mini-batch -vs- Stochastic gradient descent
        # https://machinelearningmastery.com/difference-between-a-batch-and-an-epoch/
        # default behavior is batch gradient descent i.e. looping through whole training set before updating weights
        batch_cnt = 1
        # but if a batch size is configured, split X and Y accordingly
        if batch_size:
            batch_cnt = math.ceil(Y.shape[0]/batch_size)  # cnt of datapoints / batch size, last batch has smallest size
        else:
            batch_size = X.shape[0] # the whole train set
        yy = np.copy(Y) if Y.ndim > 1 else np.expand_dims(Y, axis=1)
        batch_X: [] = np.array_split(X, batch_cnt)
        batch_Y: [] = np.array_split(yy, batch_cnt)
        self.batch_X_Y: [] = list(zip(batch_X, batch_Y))    # each element is a (X, Y) used as 1 batch gradient descent

        # len of this array = number of hidden layers; each num is # of neurons in each layer
        # to simplify algo, we consider output Y as last element of h_layers also
        Y_size = yy.shape[1]  # neuron count of Y
        h_layers = [neuron_cnt for neuron_cnt in hidden_layers] + [Y_size]

        self.W = list()  # list of weight matrix for each layer: hidden layers & last output layer
        self.W_bias = list()  # list of bias weight vector for each layer: hidden layers & last output layer

        X_size = X.shape[1]  # neuron count of X
        left_neuron_cnt = X_size
        for neuron_cnt in h_layers:
            ww = np.random.randn(left_neuron_cnt, neuron_cnt)
            ww_bias = np.random.randn(neuron_cnt)
            self.W.append(ww)
            self.W_bias.append(ww_bias)
            left_neuron_cnt = neuron_cnt

    @property
    def h_layer_cnt(self):
        """
        Including hidden layers & last output layer
        :return:
        """
        return len(self.W)

    @staticmethod
    def sigmoid(x):
        return 1 / (1 + np.exp(-x))

    @staticmethod
    def sigmoid_prime(sig):
        return sig * (1 - sig)

    def iteration_train(self):
        # for each batch
        for X_Y in self.batch_X_Y:
            X, Y = X_Y[0], X_Y[1]
            H_out = self.__forward(X)
            self.__backward(X, Y, H_out)

    def iteration_predict(self):
        x_train = np.concatenate(([ite[0] for ite in self.batch_X_Y]), axis=0)
        return self.predict(x_train)

    def predict(self, X):
        """
        Classify

        Parameters
        ----------
        X : ndarray of shape (n_samples, n_features)
        """
        res = self.__forward(X)[-1]
        return self.__squeeze_back_output(res)

    def __squeeze_back_output(self, output):
        if self.Y_ndim == 1:    # if output is just a scalar
            output = np.squeeze(output, axis=1)
        return output

    def __forward(self, X):
        """
        :param X:
        :return: H_out: list of matrix [#datapoint x neuron count] for each layer: hidden layers & last output layer
        """
        left_mt = X
        H_out = [None for _ in range(self.h_layer_cnt)]
        for idx in range(self.h_layer_cnt):
            net_H_idx = left_mt @ self.W[idx] + self.W_bias[idx]    # A @ B means A dot-product B
            H_out[idx] = self.sigmoid(net_H_idx)
            left_mt = H_out[idx]

        return H_out

    def __backward(self, X, Y, H_out):
        """
        Back propagation
        :param X: ndarray of shape (n_samples, n_features)
        :param Y: ndarray of shape (n_samples, n_features)
        :param H_out: list of matrix [#datapoint x neuron count] for each layer: hidden layers & last output layer
        :return:
        """
        delta_H = [None for _ in range(self.h_layer_cnt)]
        # delta: start initially from last layer H_out[-1] (output)
        delta_H[-1] = (Y - H_out[-1]) * self.sigmoid_prime(H_out[-1])
        # then delta: reversed loop from semi-last element (last hidden layer) -> 1st hidden layer
        for idx in range(self.h_layer_cnt-2, -1, -1):
            delta_H[idx] = delta_H[idx+1] @ self.W[idx+1].T * self.sigmoid_prime(H_out[idx])

        # use a vector of 1 with size = training size to assist update bias weights
        vec_one = np.ones(X.shape[0])
        # what is the dot-product of a vector with a matrix you ask? well..
        # a vector shape (3, ) dot a matrix (3, 5) -> a new vector (5, )
        # so basically, we take the vector and dot with each col-vector of the matrix on the right. The result is a new
        # vector of length == the number of rows in the matrix

        # update weights: from right most layer to one before 1st hidden layer
        for idx in range(self.h_layer_cnt-1, 0, -1):
            #self.W[idx] += (1 / self.train_cnt) * self.eta * H_out[idx-1].T.dot(delta_H[idx])
            self.W[idx] += H_out[idx-1].T @ delta_H[idx]
            self.W_bias[idx] += vec_one @ delta_H[idx]
        # update weights: at layer W0 back to input
        #self.W[0] += (1 / self.train_cnt) * self.eta * self.X.T.dot(delta_H[0])
        self.W[0] += X.T @ delta_H[0]
        self.W_bias[0] += vec_one @ delta_H[0]

