import pandas as pd
import random

# 데이터를 생성하기 위한 함수 정의
def generate_caregiver_data(num_samples):
    genders = ['male', 'female']
    ages = ['20-40', '40-50', '50+']
    experiences = ['novice', 'intermediate', 'experienced']
    work_times = ['morning', 'afternoon', 'evening']
    roles = ['companionship', 'personalCare', 'housekeeping']
    
    caregiver_data = []
    
    for _ in range(num_samples):
        gender = random.choice(genders)
        age = random.choice(ages)
        experience = random.choice(experiences)
        work_time = random.choice(work_times)
        role = random.choice(roles)
        
        caregiver_data.append([gender, age, experience, work_time, role])
    
    return pd.DataFrame(caregiver_data, columns=['gender', 'age', 'experience', 'workTime', 'role'])

# 200개의 요양사 데이터 생성
num_samples = 200
caregiver_data = generate_caregiver_data(num_samples)

# 생성된 데이터를 CSV 파일로 저장
caregiver_data.to_csv('silverShelter\src\main\python\caregiver_data1.csv', index=False)
