## 1. 컨테이너 기술이란 무엇입니까?

[What is a Container? | Docker](https://www.docker.com/resources/what-container/)
>  어플리케이션이 다양한 인프라 환경에서 빠르고 안정적으로 실행 될 수 있도록 코드와 모든 종속성을 패키지화하는 소프트웨어 표준단위이다.
- 컨테이너는 각 컨테이너들은 공통된 OS커널을 서로 공유하고 프로세스 격리를 통해 가상환경을 만들어낸다.
- 이 컨테이너화된 어플리케이션은 linux, window, 등 어떤 인프라환경에서든지 동일하게 실행되는 것을 보장한다.
스프링 어플리케이션 서버가 실행 되기 위해서는 코드와 각종 종속성들(자바 런타임, 각종 라이브러리, jdbc 등)이 필요하다. 

이러한 종속성과 코드를 패키지화 한것이 **도커 이미지**이다.</br>
이 이미지 도커 이미지를 docker엔진을 통해 실행시키면 **컨테이너**가 된다.

이 컨테이너화된 어플리케이션은 linux, window, 등 도커 엔진이 있는 어떤 인프라 환경에서든지 동일하게 실행되는 것을 보장한다. 
![](https://i.imgur.com/GvgPdam.png)

### 컨테이너와 가상 머신 작동 방식 비교

[컨테이너와 가상 머신 비교 | Microsoft Learn](https://learn.microsoft.com/ko-kr/virtualization/windowscontainers/about/containers-vs-vm)
#### 컨테이너
- 각 컨테이너는 동일한 커널을 이용하되, 각 프로세스들을 격리함으로 가상환경을 만들어낸다.
- namespace와 cgroup이라는 리눅스 기능을 이용하여 컨테이너를 만들어낸다.


![](https://i.imgur.com/aqYYaOe.png)

[what-are-namespaces-cgroups-how-do-they-work](https://www.nginx.com/blog/what-are-namespaces-cgroups-how-do-they-work/)
#### 어떻게 namespace와 cgroup을 이용해서 컨테이너를 만들어내는가?
- namespace:  프로세스들을 격리시킨다. 여러 프로세스를 하나의 네임스페이스라는 프로세스 그룹으로 만든다. 이 네임스페이스 안에는 자체 네트워크와 PID, IPC 등 독립된 자원 리소스를 가지게 된다.
- cgroup:  프로세스 그룹에 자원(CPU, 메모리, 디스크, I/O, 네트워크) 사용량을 제한하고 각 자원할당 을 모니터링한다.
-  컨테이너를 만들면 Dokcer는 해당 컨테이너에 namespace들을 생성하고,  cgroup을 통해 자원을 할당함으로 격리된 가상환경을 만들어낸다.

![](https://i.imgur.com/Rym1rcs.png)


#### **가상머신**
가상 머신은 각 가상환경들이 커널 위에 자체 커널을 올리고  그 위에 서로 다른 운영체제를 실행하는 방식으로 동작한다.
운영체제를 실행하기 위해서 컨테이너를 실행하는 것보다 리소스들(CPU, 메모리, 네트워크, 등)을 필요로 하고 오버헤드가 걸리게 된다. 
![](https://i.imgur.com/lbLR2OH.png)

## 2. 도커란 무엇입니까?
[Docker overview | Docker Docs](https://docs.docker.com/get-started/overview/)

도커는 애플리케이션을 신속하게 구축, 테스트 및 배포할 수 있는 소프트웨어 플랫폼이다.

도커는 어플리케이션을 패키징화 하는 도구들과 해당 패키징된 이미지를 실행하는 도커 엔진을 제공한다.

도커 이미지들이 있는 도커 허브, 이미지를 컨테이너화 하는 도커 엔진, 도커 cli, 도커 gui등 각종 도커 개발도구 등을 포힘된다.
## 3. 도커 파일, 도커 이미지, 도커 컨테이너의 개념은 무엇이고, 서로 어떤 관계입니까?
[\[Docker\] Docker 이해하기 -1 : 기초 이론(아키텍처, 흐름, 주요 용어) — Contributor9](https://adjh54.tistory.com/352)

**도커 이미지**는 도커 컨테이너를 실행하는데 필요한 설정, 종속성, 각종 설정들을 포함하는 패키지다. 이 도커 이미지 파일을 도커 엔진이 실행하면 **도커 컨테이너**가 된다.
**도커 파일**은 도커 이미지를 구성하는 명령어와 설정이 저장된 텍스트 파일이다. 도커 이미지를 빌드하기 위한 텍스트 파일이다.

도커 컨테이너를 객체라고 생각할 수 있다. 생성된 컨테이너는 인프라 환경에 맞게 변형함으로서 다형성이 가능하다.
![](https://i.imgur.com/TaiOp81.jpeg)

## 도커 실행 결과
![](https://i.imgur.com/bUk3lFM.png)

![](https://i.imgur.com/JRY74Px.png)