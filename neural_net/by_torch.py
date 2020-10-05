from datetime import datetime as dt

import torch
import torch.nn as nn

"""
Inspired by https://medium.com/dair-ai/a-simple-neural-network-from-scratch-with-pytorch-and-google-colab-c7f3830618e0
"""


class NeuralNet(nn.Module):
    GPU_AVAIL = torch.cuda.is_available()
    DEVICE = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

    train_cnt = 0
    epoch = 0
    eta = 0.5

    # TODO make constructor-only param
    #h_layers = [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000]
    h_layers = [3]

    X = None
    Y = None
    X_size = 0  # neural count
    Y_size = 0  # neural count

    # hidden layers & last output layers
    W = list()
    W_bias = list()
    __vec_one = None
    H = list()

    def __all_to_cuda(self):
        self.X = self.X.to(self.DEVICE)
        self.Y = self.Y.to(self.DEVICE)
        for i in range(len(self.W)):
            self.W[i] = self.W[i].to(self.DEVICE)
            self.W_bias[i] = self.W_bias[i].to(self.DEVICE)
            pass
        self.__vec_one = self.__vec_one.to(self.DEVICE)
        self.to(self.DEVICE)
        pass

    def __init__(self, X, Y, epoch):
        super(NeuralNet, self).__init__()

        self.X, self.Y = self.__scaled(X, Y)
        self.train_cnt = len(self.X)
        self.X_size = len(self.X[0])
        self.Y_size = len(self.Y[0])
        self.epoch = epoch

        self.h_layers.append(self.Y_size)
        left_neuron_cnt = self.X_size
        for neuron_cnt in self.h_layers:
            ww = torch.randn(left_neuron_cnt, neuron_cnt)
            hh = torch.full((self.train_cnt, neuron_cnt), -0.0001)
            self.W.append(ww)
            self.W_bias.append(torch.randn(neuron_cnt))
            self.H.append(hh)
            left_neuron_cnt = neuron_cnt
        self.__vec_one = torch.ones(self.train_cnt)

        #device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
        # Assuming that we are on a CUDA machine, this should print a CUDA device:
        #print(device)
        print("DEVICE = GPU" if self.GPU_AVAIL else "device = cpu")
        if self.GPU_AVAIL:
            self.__all_to_cuda()
        pass

    @staticmethod
    def sigmoid(s):
        return 1 / (1 + torch.exp(-s))

    @staticmethod
    def sigmoid_prime(sig):
        return sig * (1 - sig)

    def get_train_loss(self):
        Y = self.__scaled_back(self.Y)
        H_last = self.__scaled_back(self.H[-1])
        return torch.mean((Y - H_last)**2).detach().item()

    def do_train(self):
        for i in range(self.epoch):
            self(self.X)
            self.backward()
            #print("epoch = {}: loss = {}".format( i, str(self.get_train_loss()) ))

    def __scaled(self, X, Y):
        # normalize
        # max 24h a day
        # max score = 100
        return X/24, Y/100

    def __scaled_back(self, Y):
        # max score = 100
        return Y*100

    def forward(self, X):
        left_mt = X
        for idx in range(len(self.h_layers)):
            net_H_idx = torch.matmul(left_mt, self.W[idx]) + self.W_bias[idx]
            self.H[idx] = self.sigmoid(net_H_idx)
            left_mt = self.H[idx]

        return self.H[-1]

    def backward(self):
        # delta: start initially from layer H2 (output)
        delta_H = [torch.empty(0, 0) for idx in range(len(self.h_layers))]
        delta_H[-1] = (self.Y - self.H[-1]) * self.sigmoid_prime(self.H[-1])
        # then delta: reversed loop from semi-last element -> beginning
        for idx in range(len(self.h_layers)-2, -1, -1):
            delta_H[idx] = torch.matmul(delta_H[idx + 1], torch.t(self.W[idx + 1])) * self.sigmoid_prime(self.H[idx])
            pass

        # vector of 1 with size = training size
        vec_one = self.__vec_one
        # update weights: start from right most layer
        for idx in range(len(self.h_layers) - 1, 0, -1):
            self.W[idx]      += (1 / self.train_cnt) * self.eta * torch.matmul(torch.t(self.H[idx - 1]), delta_H[idx])
            self.W_bias[idx] += (1 / self.train_cnt) * self.eta * torch.matmul(vec_one, delta_H[idx])
            pass
        # update weights: at layer W0 back to input
        self.W[0]      += (1 / self.train_cnt) * self.eta * torch.matmul(torch.t(self.X), delta_H[0])
        self.W_bias[0] += (1 / self.train_cnt) * self.eta * torch.matmul(vec_one, delta_H[0])


f = open('study-sleep-grade.txt')
lines = f.readlines()
f.close()
# print(lines)

x_all = []
y_all = []
for line in lines:
    p = line.strip().split(", ")
    y = [float(yj) for yj in p[0].strip().split(' ')]
    x = [float(xi) for xi in p[1].strip().split(' ')]
    x_all.append(x)
    y_all.append(y)

INP = torch.tensor((x_all[:-1]), dtype=torch.float)
Y = torch.tensor((y_all[:-1]), dtype=torch.float)
neu_net = NeuralNet(INP, Y, epoch=1000)

if torch.cuda.device_count() > 1:
  #print("Let's use", torch.cuda.device_count(), "GPUs!")
  #neu_net = nn.DataParallel(neu_net)
  pass

print("-------------------------")
print("training ...")
tic = dt.now().microsecond
neu_net.do_train()
toc = dt.now().microsecond
print("-------------------------")
print("train loss = {}".format( str(neu_net.get_train_loss()) ))
print("Train taken {} micro-secs".format('{:,}'.format(toc - tic)))

