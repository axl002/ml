# ml
A collection of tests using ml.

#KNN.java
Uses k nearest neighbors (kuh nu nu) algorithm on the wiscoson breast cancer data set. Data at: https://archive.ics.uci.edu/ml/machine-learning-databases/breast-cancer-wisconsin/breast-cancer-wisconsin.data
Guesses if a cell has cancer based on whether its k nearest neighbors have cancer through simple majority voting. Nearsest neighbor defined as euclidian distance between two cells based on their 9 features values. KNN is inherently using leave one out crossvalidation.
KNN is biased to say no cancer if there is a tie in the voting.
Prints out classification accuracy, precision, and recall.
