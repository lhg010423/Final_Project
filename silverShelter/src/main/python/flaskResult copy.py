from flask import Flask, request, jsonify
import pandas as pd
from sklearn.cluster import KMeans
from sklearn.preprocessing import LabelEncoder, StandardScaler
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # CORS 설정

@app.route('/medicalCenter/careGivers', methods=['GET', 'POST'])
def recommend_caregivers():
    # JSON 문자열을 Python 객체로 변환
    python_obj = request.get_json()
    print("Received data from Java:", python_obj)
    
    user_data = python_obj
    
    # 요양사 데이터를 불러옵니다.
    data = pd.read_csv("silverShelter/src/main/python/caregiver_data1.csv")

    # 필요한 특징 열을 선택합니다.
    features = data[['gender', 'age', 'experience', 'workTime', 'role']]

    # 범주형 데이터를 레이블 인코딩으로 변환합니다.
    label_encoders = {}
    for column in features.columns:
        le = LabelEncoder()
        features[column] = le.fit_transform(features[column])
        label_encoders[column] = le

    # 데이터를 표준화합니다.
    scaler = StandardScaler()
    scaled_features = scaler.fit_transform(features)

    # K-평균 클러스터링을 적용합니다.
    kmeans = KMeans(n_clusters=40, random_state=0)
    kmeans.fit(scaled_features)

    # 각 요양사의 클러스터 레이블을 확인합니다.
    data['cluster_label'] = kmeans.labels_

    # 사용자 입력에 대해 레이블 인코딩을 적용합니다.
    user_data_encoded = {col: label_encoders[col].transform([user_data[col]])[0] for col in user_data}

    # 사용자 입력을 DataFrame으로 변환합니다.
    user_df = pd.DataFrame([user_data_encoded])

    # 데이터를 표준화합니다.
    user_features_df = scaler.transform(user_df)

    # 클러스터 레이블을 예측합니다.
    user_cluster_label = kmeans.predict(user_features_df)[0]

    # 가장 유사한 클러스터에 속한 요양사들을 추천합니다.
    similar_caregivers = data[data['cluster_label'] == user_cluster_label]

    # 필요한 열만 포함하도록 결과를 제한
    output_columns = ['name', 'age', 'gender', 'experience', 'workTime', 'role']
    recommendations = similar_caregivers[output_columns].to_dict(orient='records')

    return jsonify(recommendations)

if __name__ == '__main__':
    app.run(debug=True, port=5000)  # 포트 명시
