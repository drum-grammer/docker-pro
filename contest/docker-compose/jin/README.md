## LLM chatbot
### 소개
chat-gpt와 비슷하게 local LLM(Llama3) 과 대화를 할 수 있는 간단한 웹 애플리케이션입니다. 
- LLM(Large Language Models)은 대규모 언어 모델이라는 뜻으로, 사용자 질문을 입력하면 질문에 대한 답변을 생성하는 언어 AI 모델입니다.
  - OpenAI의 chat-gpt, Meta의 Llama, Google의 Gemma 등이 대표적인 예입니다.
- 구현된 앱에는 비교적 작은 Meta의 Llama3-8B 모델을 사용했기 때문에 한국어에 대한 성능은 좋지 않습니다.
<img width="724" alt="스크린샷 2024-05-15 오후 10 34 17" src="https://github.com/jinkimmy/docker-pro/assets/145090267/c71b69da-48ae-4fd5-9ade-e9042388fc05">


### 실행 방법
`docker-compose.yml`파일이 있는 폴더로 이동하여 Docker Compose를 사용하여 실행합니다.
~~~sh
docker-compose up
~~~
- `streamlit` 컨테이너가 실행되고 있는 모습
<img width="934" alt="스크린샷 2024-05-15 오후 10 18 05" src="https://github.com/jinkimmy/docker-pro/assets/145090267/84a602e9-f60e-4fdf-90b8-acb1cbf6c474">

터미널에서 streamlit 컨테이너 실행되고 있으면 아래의 명령어를 통해 Llama3를 ollama 컨테이너에 실행합니다.
~~~sh
docker-compose exec ollama ollama run llama3
~~~

Llama3-8B 모델은 용량이 크기 때문에 실행되는데 약간의 시간이 소요됩니다.
- llama3가 ollama 컨테이너에서 안정적으로 서빙되고 있다면 다음과 같은 문구가 나타납니다.
~~~sh
>>> Send a message (/? for help)
~~~

이제 웹브라우저에서 `localhost:8502`로 접속하여 채팅을 시작합니다.

### 참고
- 이 프로젝트는 streamlit 프레임워크를 사용하여 개발되었습니다.
- 챗봇은 사용자가 질문을 하면 llm이 대답하는 간단한 기능이 포함됐습니다.
- Ollama는 다양한 LLM을 로컬PC 환경에서 쉽고 빠르게 배포할 수 있게 도와주는 오픈소스입니다.
  - 따라서, 개인 PC 리소스가 매우 제한적이라면 모델이 실행되지 않거나 답변이 느립니다.
- 이 프로젝트에서 사용되는 llama3-8B는 최소 8GB의 RAM을 필요로 합니다.
