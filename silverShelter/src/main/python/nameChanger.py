import pandas as pd

# CSV 파일 경로
file_path = 'silverShelter\\src\\main\\resources\\caregiver_data1.csv'

# CSV 파일을 DataFrame으로 읽어오기
df = pd.read_csv(file_path)

# 모든 문자열 열에서 띄어쓰기 제거
df = df.applymap(lambda x: x.strip() if isinstance(x, str) else x)

# 이름이 같은 데이터 중 마지막 데이터만 남기기
df_cleaned = df.drop_duplicates(subset='caregiversName', keep='last')

# 번호를 순서대로 만들기
df_cleaned['caregiversNo'] = range(1, len(df_cleaned) + 1)

# CSV 파일로 저장하기
df_cleaned.to_csv('caregivers_cleaned_numbered.csv', index=False)

print("처리가 완료되었습니다. 클린업된 데이터는 caregivers_cleaned_numbered.csv 파일에 저장되었습니다.")
