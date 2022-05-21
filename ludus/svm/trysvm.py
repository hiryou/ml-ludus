# tutorial      https://scikit-learn.org/stable/modules/svm.html
# more tutorial https://www.datacamp.com/community/tutorials/svm-classification-scikit-learn-python

from sklearn import svm

X = [[0, 0], [1, 1]]
y = [0, 1]

clf = svm.SVC()
clf.fit(X, y)

res = clf.predict([[.8, .7]])
print(res)
#print(clf.support_vectors_)
print(clf.support_)

