import plotly.graph_objects as go
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
import pandas as pd

# 데이터 불러오기
data = pd.read_csv("silverShelter\src\main\python\caregiver_data1.csv")

# 필요한 특징 열을 선택합니다.
features = data[['gender', 'age', 'experience', 'workTime', 'role']]

# 범주형 데이터를 원-핫 인코딩합니다.
features = pd.get_dummies(features)

# 데이터를 표준화합니다.
scaler = StandardScaler()
scaled_features = scaler.fit_transform(features)

# 클러스터 개수 범위 설정
cluster_range = list(range(1, 11))  # Convert range object to a list
inertia = []

# 클러스터링 및 관성 측정
for k in cluster_range:
    kmeans = KMeans(n_clusters=k)
    kmeans.fit(scaled_features)
    inertia.append(kmeans.inertia_)

# Elbow Point 찾기
elbow_point = None
for i in range(1, len(inertia)-1):
    if (inertia[i] - inertia[i-1]) / (inertia[i+1] - inertia[i]) > 1:
        elbow_point = i + 1
        break

# 그래프 그리기
fig = go.Figure()
fig.add_trace(go.Scatter(x=cluster_range, y=inertia, mode='lines+markers', name='Inertia'))
fig.add_trace(go.Scatter(x=[elbow_point], y=[inertia[elbow_point-1]], mode='markers', marker=dict(color='red', size=10), name='Elbow Point'))
fig.update_layout(title='Elbow Method for Optimal Cluster Number', xaxis_title='Number of Clusters', yaxis_title='Inertia')
fig.show()
