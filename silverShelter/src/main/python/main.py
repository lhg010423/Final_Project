import pandas as pd
from sklearn.cluster import KMeans
from sklearn.preprocessing import LabelEncoder, StandardScaler

# 요양사 데이터를 불러옵니다.
data = pd.read_csv("silverShelter\src\main\python\caregiver_data1.csv")

# 필요한 특징 열을 선택합니다.
features = data[['gender', 'age', 'experience', 'workTime', 'role']]

# 범주형 데이터를 레이블 인코딩으로 변환합니다.
label_encoder_gender = LabelEncoder()
label_encoder_age = LabelEncoder()
label_encoder_experience = LabelEncoder()
label_encoder_workTime = LabelEncoder()
label_encoder_role = LabelEncoder()

features['gender'] = label_encoder_gender.fit_transform(features['gender'])
features['age'] = label_encoder_age.fit_transform(features['age'])
features['experience'] = label_encoder_experience.fit_transform(features['experience'])
features['workTime'] = label_encoder_workTime.fit_transform(features['workTime'])
features['role'] = label_encoder_role.fit_transform(features['role'])

# 데이터를 표준화합니다.
scaler = StandardScaler()
scaled_features = scaler.fit_transform(features)

# K-means 클러스터링을 적용합니다.
kmeans = KMeans(n_clusters=40)  # 클러스터 개수는 적절히 조정할 수 있습니다.
kmeans.fit(scaled_features)

# 각 요양사의 클러스터 레이블을 확인합니다.
data['cluster_label'] = kmeans.labels_

# 사용자가 선택한 특징에 대해 가장 유사한 클러스터를 찾습니다.
user_features = {
    'gender': 'female',
    'age': '40-50',
    'experience': 'intermediate',
    'workTime': 'afternoon',
    'role': 'companionship'
}

# 사용자 입력에 대해 레이블 인코딩을 적용합니다.
user_features['gender'] = label_encoder_gender.transform([user_features['gender']])[0]
user_features['age'] = label_encoder_age.transform([user_features['age']])[0]
user_features['experience'] = label_encoder_experience.transform([user_features['experience']])[0]
user_features['workTime'] = label_encoder_workTime.transform([user_features['workTime']])[0]
user_features['role'] = label_encoder_role.transform([user_features['role']])[0]

# 사용자 입력을 DataFrame으로 변환합니다.
user_df = pd.DataFrame([user_features])

# 데이터를 표준화합니다.
user_features_df = scaler.transform(user_df)

user_cluster_label = kmeans.predict(user_features_df)

# 가장 유사한 클러스터에 속한 요양사들을 추천합니다.
similar_caregivers = data[data['cluster_label'] == user_cluster_label[0]]

print(similar_caregivers)
