import numpy as np

# X = (hours studying, hours sleeping), Y = score on test
X_orig = np.array(([2, 9], [1, 5], [3, 6], [5, 10]), dtype=float)  # input data
Y_orig = np.array(([92], [86], [89], [90]), dtype=float)  # output

# scale units
# xAll = xAll/np.amax(xAll, axis=0) # scaling input data
X = X_orig / 24  # scaling input data (total 24h a day)
Y = Y_orig / 100  # scaling output data (max test score is 100)


class NeuralNetwork(object):

    def __init__(self):
        # parameters
        inputSize = 2
        hiddenSize = 3
        outputSize = 1

        # weights
        self.W_ih = np.random.randn(inputSize, hiddenSize)  # (2x3) weight matrix from input to hidden layer
        self.W_ho = np.random.randn(hiddenSize, outputSize)  # (3x1) weight matrix from hidden to output layer

    def forward(self, X):
        # forward propagation through our network
        net_hidden = np.dot(X, self.W_ih)  # dot product of X (input) and first set of 2x3 weights
        self.h_layer = self.sigmoid(net_hidden)  # activation function

        net_out = np.dot(self.h_layer, self.W_ho)  # dot product of hidden layer (z2) and second set of 3x1 weights
        o_layer = self.sigmoid(net_out)  # final activation function

        return o_layer

    def sigmoid(self, s):
        # activation function
        return 1 / (1 + np.exp(-s))

    def sigmoid_prime(self, s):
        # derivative of sigmoid
        return (s) * (1 - (s))

    def backward(self, X, y, o):
        # backward propagate through the network
        o_error = y - o  # error in output
        o_delta = o_error * self.sigmoid_prime(o)  # applying derivative of sigmoid to o's error

        # h error: how much our hidden layer weights contributed to output error
        h_error = o_delta.dot(self.W_ho.T)
        h_delta = h_error * self.sigmoid_prime(self.h_layer)  # applying derivative of sigmoid to h's error

        # now adjust w's at each layer
        self.W_ho += self.h_layer.T.dot(o_delta)  # adjusting (hidden --> output) weights
        self.W_ih += X.T.dot(h_delta)  # adjusting (input --> hidden) weights

    def train(self, X, y):
        o = self.forward(X)
        self.backward(X, y, o)

    def save_weights(self):
        np.savetxt("w1.txt", self.W_ih, fmt="%s")
        np.savetxt("w2.txt", self.W_ho, fmt="%s")


NN = NeuralNetwork()
for i in range(100):  # trains the NN 1,00 times
    print("# " + str(i) + "\n")
    print("Input (scaled): \n" + str(X))
    print("Actual Output: \n" + str(Y))
    print("Predicted Output: \n" + str(NN.forward(X)))
    train_loss = np.mean(np.square(Y*100 - NN.forward(X)*100))
    print('Train loss: {:.2f}/100 (max score = 100)'.format(train_loss))  # mean sum squared loss of scaled back output
    print("\n")
    NN.train(X, Y)

# NN.save_weights()
# NN.predict()
