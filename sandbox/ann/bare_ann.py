import numpy as np
from sklearn.metrics import confusion_matrix, accuracy_score
import math


class NeuralNet_By_Numpy(object):

    eta = 0.5   # learning rate

    def __init__(self, X, Y, epoch: int, batch_size: int = None, hidden_layers: [int] = []):
        """
        Setup neural network

        Parameters
        ----------
        X : ndarray of shape (n_samples, n_features)

        Y : ndarray of shape (n_samples,) or (n_samples, n_features)
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

        self.epoch = epoch

        # len of this array = number of hidden layers; each num is # of neurons in each layer
        # to simplify algo, we consider output Y as last element of h_layers also
        Y_size = yy.shape[1]  # neuron count of Y
        h_layers = [neuron_cnt for neuron_cnt in hidden_layers] + [Y_size]

        self.W = list()  # list of weight matrix for each layer: hidden layers & last output layer
        self.H = list()  # list of matrix [#datapoint x neuron count] for each layer: hidden layers & last output layer

        X_size = X.shape[1]  # neuron count of X
        left_neuron_cnt = X_size
        for neuron_cnt in h_layers:
            ww = np.random.randn(left_neuron_cnt, neuron_cnt)
            hh = np.full((batch_size, neuron_cnt), -0.0001)
            self.W.append(ww)
            self.H.append(hh)
            left_neuron_cnt = neuron_cnt

        self.DEBUG = True

    @staticmethod
    def sigmoid(s):
        return 1 / (1 + np.exp(-s))

    @staticmethod
    def sigmoid_prime(sig):
        return sig * (1 - sig)

    def do_train(self):
        # for each training iteration
        for i in range(self.epoch):
            # for each batch
            for X_Y in self.batch_X_Y:
                X, Y = X_Y[0], X_Y[1]
                self.__forward(X)
                self.__backward(X, Y)
            if self.DEBUG:
                x_train = np.concatenate(([ite[0] for ite in self.batch_X_Y]), axis=0)
                y_true = np.concatenate(([ite[1] for ite in self.batch_X_Y]), axis=0)
                y_true = self.__squeeze_back_output(y_true)
                y_pred = self.predict(x_train)
                y_pred = np.where(y_pred > 0.5, 1, 0)
                print('Accuracy: {:.2f}%'.format(accuracy_score(y_true, y_pred) * 100))

    def predict(self, X):
        """
        Classify

        Parameters
        ----------
        X : ndarray of shape (n_samples, n_features)
        """
        res = self.__forward(X)
        return self.__squeeze_back_output(res)

    def __squeeze_back_output(self, output):
        if self.Y_ndim == 1:    # if output is just a scalar
            output = np.squeeze(output, axis=1)
        return output

    def __forward(self, X):
        left_mt = X
        for idx in range(len(self.H)):
            net_H_idx = np.dot(left_mt, self.W[idx])
            self.H[idx] = self.sigmoid(net_H_idx)
            left_mt = self.H[idx]

        return self.H[-1]

    def __backward(self, X, Y):
        delta_H = [None for _ in range(len(self.H))]
        # delta: start initially from last layer H[-1] (output)
        delta_H[-1] = (Y - self.H[-1]) * self.sigmoid_prime(self.H[-1])
        # then delta: reversed loop from semi-last element (last hidden layer) -> 1st hidden layer
        for idx in range(len(self.H)-2, -1, -1):
            delta_H[idx] = delta_H[idx+1].dot(self.W[idx+1].T) * self.sigmoid_prime(self.H[idx])

        # update weights: from right most layer to one before 1st hidden layer
        for idx in range(len(self.W)-1, 0, -1):
            #self.W[idx] += (1 / self.train_cnt) * self.eta * self.H[idx-1].T.dot(delta_H[idx])
            self.W[idx] += self.H[idx-1].T.dot(delta_H[idx])
        # update weights: at layer W0 back to input
        #self.W[0] += (1 / self.train_cnt) * self.eta * self.X.T.dot(delta_H[0])
        self.W[0] += X.T.dot(delta_H[0])

