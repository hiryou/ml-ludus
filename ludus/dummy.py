# Python code to demonstrate the
# use of numpy.cov
import numpy as np

x = np.array([
    [0, 3, 4],
    [1, 2, 4],
    [3, 4, 5],
    [1, 2, 3],
    [4, 5, 6]
])

print("Shape of array:\n", np.shape(x))

print("Covariance matrix of x_tranpose:\n", np.cov(x.T))

cov_mt = np.cov(x, rowvar=False)
print("Covariance matrix of x:\n", cov_mt)
print("Inverse of cov matrix:\n", np.linalg.inv(cov_mt))

print(np.dot([1, 2], [3, 4]) == int(11))   # = 1*3 + 2*4 = 11
