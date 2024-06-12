from flask import Flask, request, jsonify
import requests
import pandas as pd
from sklearn.cluster import KMeans
from sklearn.preprocessing import LabelEncoder, StandardScaler
import json

app = Flask(__name__)

@app.route('/medicalCenter/careGivers', methods=['POST'])
def recommend_caregivers():
    # JSON 문자열을 Python 객체로 변환
    python_obj = request.get_json()

    print(python_obj)  # {'gender': 'female', 'age': '40-50', 'experience': 'intermediate', 'workTime': 'afternoon', 'role': 'companionship'}
    print(type(python_obj))  # <class 'dict'>
    user_data = python_obj
    
    # Java에서 받은 데이터를 출력하여 확인합니다.
    print("Received data from Java:", user_data)
    
    # 데이터를 가지고 요양사 매칭 알고리즘을 수행합니다.
    # 이 부분에는 사용자의 요구에 따라 요양사를 추천하는 알고리즘이 들어갑니다.
    
    # 요양사 데이터를 불러옵니다.
    data = pd.read_csv("silverShelter/src/main/python/caregiver_data1.csv")

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


    # 사용자 입력에 대해 레이블 인코딩을 적용합니다.
    user_data['gender'] = label_encoder_gender.transform([user_data['gender']])[0]
    user_data['age'] = label_encoder_age.transform([user_data['age']])[0]
    user_data['experience'] = label_encoder_experience.transform([user_data['experience']])[0]
    user_data['workTime'] = label_encoder_workTime.transform([user_data['workTime']])[0]
    user_data['role'] = label_encoder_role.transform([user_data['role']])[0]

    # 사용자 입력을 DataFrame으로 변환합니다.
    user_df = pd.DataFrame([user_data])

    # 데이터를 표준화합니다.
    user_features_df = scaler.transform(user_df)

    user_cluster_label = kmeans.predict(user_features_df)

    # 가장 유사한 클러스터에 속한 요양사들을 추천합니다.
    similar_caregivers = data[data['cluster_label'] == user_cluster_label[0]]

    print(similar_caregivers)

    # 추천된 요양사 데이터를 Java로 다시 전송합니다.
    recommendation = similar_caregivers.to_json()  # DataFrame을 JSON 문자열로 변환
    return recommendation

if __name__ == '__main__':
    app.run(debug=True)
