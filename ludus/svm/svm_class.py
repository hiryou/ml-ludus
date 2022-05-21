# tutorial https://www.datacamp.com/community/tutorials/svm-classification-scikit-learn-python

from sklearn import svm, datasets as ds, metrics
from sklearn.model_selection import train_test_split as splitter

#Load dataset
cancer = ds.load_breast_cancer()

print('sample # = {}'.format(len(cancer.data)))
print('target # = {}'.format(len(cancer.target)))
print('shape = {}'.format(cancer.data.shape))

#print("Features: {}, feature dim # = {}".format(cancer.feature_names, len(cancer.feature_names)))
#print('some data ex = {}, feature dim # = {}'.format(cancer.data[0:5], len(cancer.data[0])))

#print("Labels: ", cancer.target_names)

# 70% training and 30% tes
X_train, X_test, y_train, y_test = splitter(cancer.data, cancer.target, test_size=0.4, random_state=6265456)
#print(len(X_train))
#print(len(Y_train))
#print(len(X_test))
#print(len(Y_test))

# linear kernel
clf = svm.SVC(kernel='linear')

# train
print('training...')
clf.fit(X_train, y_train)

# test
y_pred = clf.predict(X_test)

# evaluation
print("Accuracy:", metrics.accuracy_score(y_test, y_pred))
# Precision
print("Precision:", metrics.precision_score(y_test, y_pred))
# Recall
print("Recall:", metrics.recall_score(y_test, y_pred))
