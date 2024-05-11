# 기본 이미지로 파이썬 3.8을 사용
FROM python:3.8-slim

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사 및 설치
COPY requirements.txt .
RUN pip install -r requirements.txt

# 애플리케이션 코드 복사
COPY . .

# `ENTRYPOINT` 설정
ENTRYPOINT ["python"]

# `CMD`는 ENTRYPOINT의 기본 매개변수 역할
CMD ["app.py"]


# `ENTRYPOINT`에 모두 설정
# ENTRYPOINT ["python", "app.py"]
