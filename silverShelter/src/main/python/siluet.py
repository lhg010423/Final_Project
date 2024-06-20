import pandas as pd
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score
import matplotlib.pyplot as plt

# 데이터 불러오기
data = pd.read_csv("silverShelter\src\main\python\caregiver_data1.csv")

# 필요한 특징 열 선택
features = data[['gender', 'age', 'experience', 'workTime', 'role']]

# 범주형 데이터를 원-핫 인코딩
features = pd.get_dummies(features)

# 클러스터 개수 범위 설정
cluster_range = range(2, 100)
silhouette_scores = []

# 각 클러스터 개수에 대해 KMeans 모델을 훈련하고 실루엣 점수 계산
for num_clusters in cluster_range:
    kmeans = KMeans(n_clusters=num_clusters, random_state=42)
    cluster_labels = kmeans.fit_predict(features)
    silhouette_avg = silhouette_score(features, cluster_labels)
    silhouette_scores.append(silhouette_avg)

# 결과 시각화
plt.plot(cluster_range, silhouette_scores, marker='o')
plt.xlabel('Number of Clusters')
plt.ylabel('Silhouette Score')
plt.title('Silhouette Analysis')
plt.show()
