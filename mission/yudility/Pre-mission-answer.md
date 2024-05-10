1. 컨테이너 기술이란?
응용프로그램과 그에 필요한 라이브러리, 종속성, 실행 환경등을 하나로 묶어서 독립적인 환경에서 실행할 수 있도록 하는 기술이다. 호스트 시스템의 자원을 효율적으로 공유하면서도 서로 격리되어있어 다른 컨테이너와의 간섭을 최소화한다. 

2. 도커(Docker)란?
컨테이너 기술을 기반으로하는 오픈소스 플랫폼. 경량의 컨테이너를 사용하여 응용 프로그램을 환경에 독립적으로 배포하고 실행할 수 있도록 하며, 개발부터 배포까지 일관된 환경을 제공한다.

3.도커 파일, 도커 이미지, 도커 컨테이너의 개념과 관계?
- 도커 파일(Docker file): 도커 이미지를 생성하기 위한 스크립트 파일. 참조할 베이스 이미지, 필요한 패키지나 의존성, 어떤 애플리케이션을 빌드 및 실행할지 등의 지침이 포함된다.
- 도커 이미지(Docker image): 도커 파일을 기반으로 만들어진 템플릿. 컨테이너를 실행하는데 사용된다.
- 도커 컨테이너(Docker Container): 도커 이미지를 실행한 인스턴스. 각 컨테이너는 고유한 격리된 환경에서 가볍고 빠르게 실행되고, 필요한 모든 리소스를 이미지에서 가져온다.

- 관계: Docker file --(build)--> Docker Image --(run)--> Docker Container