import sys
import joblib
import numpy as np
import warnings

warnings.filterwarnings('ignore')
modele_81 = joblib.load('modele_forest.sav')

n = len(sys.argv)



i = 1
X_test = []

while i != n:
	X_test += [int(sys.argv[i])]
	i+=1
X_test = np.array(X_test).reshape(1,-1)

print(int(modele_81.predict(X_test)))
