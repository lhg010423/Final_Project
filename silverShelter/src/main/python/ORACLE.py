import csv

def read_caregivers_csv(file_path):
    caregivers = []
    with open(file_path, mode='r', newline='', encoding='utf-8-sig') as file:
        reader = csv.DictReader(file)
        for row in reader:
            caregivers.append(row)
    return caregivers

def generate_insert_queries(caregivers_data):
    insert_queries = []
    for caregiver in caregivers_data:
        insert_query = """
        INSERT INTO CAREGIVERS (CAREGIVERS_NO, CAREGIVERS_NAME, CAREGIVERS_AGE, CAREGIVERS_GENDER,
                                CAREGIVERS_TEL, CAREGIVERS_EXPERIENCE, CAREGIVERS_WORK_HOURS, CAREGIVERS_ROLE)
        VALUES ({}, '{}', '{}', '{}', '{}', '{}', '{}', '{}');
        """.format(
            caregiver['caregiversNo'],
            caregiver['caregiversName'],
            caregiver['caregiversAge'],
            caregiver['caregiversGender'],
            caregiver['caregiversTel'],
            caregiver['caregiversExperience'],
            caregiver['caregiversWorkHours'],
            caregiver['caregiversRole']
        )
        insert_queries.append(insert_query)
    return insert_queries

# CSV 파일 경로 설정 (수정 필요)
file_path = 'silverShelter\\src\\main\\resources\\caregiver_data1.csv'

# CSV 파일 읽기
caregivers_data = read_caregivers_csv(file_path)

# INSERT 쿼리 생성
insert_queries = generate_insert_queries(caregivers_data)

# 생성된 쿼리 출력
for query in insert_queries:
    print(query)
