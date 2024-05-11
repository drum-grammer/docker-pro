Docker 파일 문법 및 사용법에 대한 설명과 예제를 준비했습니다. Docker의 기본 구성 요소인 `RUN`, `CMD`, `ENTRYPOINT` 등을 이해하기 위한 간단하고 명확한 설명과 함께 예제 Dockerfile을 만들어 보겠습니다. 그리고 이를 테스트할 수 있는 환경을 구성하는 방법도 제공하겠습니다.

### 1. `RUN`, `CMD`, `ENTRYPOINT` 설명 및 차이점

- **`RUN`**: Docker 이미지를 빌드하는 과정에서 사용되는 명령어입니다. 이 명령어는 이미지 빌드 시에 실행되며 결과물은 이미지에 영구적으로 반영됩니다. 예를 들어, 패키지를 설치하거나 파일을 수정하는 데 사용됩니다.
- **`CMD`**: Docker 컨테이너가 시작될 때 실행할 기본 명령을 지정합니다. Dockerfile 내에 한 번만 사용할 수 있으며, 여러 개의 `CMD`가 있을 경우 마지막 `CMD`만 적용됩니다.
- **`ENTRYPOINT`**: 컨테이너가 실행될 때 항상 실행되어야 하는 명령을 정의합니다. `CMD`와 함께 사용될 경우, `ENTRYPOINT`는 실행 파일 또는 스크립트를 지정하고 `CMD`는 그에 대한 매개변수를 제공하는 형태로 사용됩니다.

### 2. 예제 Dockerfile 작성

이 예제에서는 간단한 Python 애플리케이션을 컨테이너화하는 과정을 보여 드리겠습니다.

**Dockerfile:**
```dockerfile
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
```

**requirements.txt:**
```
flask
```

**app.py:**
```python
from flask import Flask
app = Flask(__name__)

@app.route('/')
def hello_world():
    return '수강생들의 빠른 취업, 연봉 상승 기원합니다!'

if __name__ == '__main__':
    app.run(host='0.0.0.0')
```

### 3. 추가적인 Docker 파일 문법

- **`COPY`**: 파일이나 디렉토리를 로컬 파일 시스템에서 이미지로 복사합니다.
- **`ADD`**: `COPY`와 비슷하지만, 네트워크에서 파일을 가져오거나 로컬의 tar 파일을 자동으로 압축 해제할 수 있습니다.
- **`ENV`**: 환경 변수를 설정합니다. 예: `ENV PATH /usr/local/bin:$PATH`
- **`EXPOSE`**: 컨테이너가 리스닝할 포트를 지정합니다. 예: `EXPOSE 80`
- **`VOLUME`**: 데이터 볼륨을 마운트할 위치를 지정합니다. 예: `VOLUME /data`

### 4. 이미지 빌드 및 컨테이너 실행 명령

1. **이미지 빌드:**
   ```bash
   docker build -t my-python-app .
   ```
2. **컨테이너 실행:**
   ```bash
   docker run -p 8080:5000 my-python-app
   ```

위 명령을 실행하면, `8080` 포트를 통해 애플리케이션에 접근할 수 있습니다. 이렇게 설정하면 Docker의 기본적인 사용법과 문법을 이해하고 직접 테스트해 볼 수 있는 환경이 준비됩니다. 🚀
