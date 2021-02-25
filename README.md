# blog-project
개인블로그 프로젝트

# gradle build 참고사항
## 1. querydls을 추가하고 build를해야 Qblog형태를 사용할 수 있다
- Gradle툴에서 querydls Tasks폴더가 생기고 여기서 cleanQuerydlsSourcesDir, initQuerydlsSourcesDir 빌드 작업이 가능하다.
- build가 되면 프로젝트 최상단에 build라는 폴더가 생성되고 querydls폴더에는 VO로 사용하는 객체 앞에 Q가 붙은 java파일이 생성된다.
- JPAQueryFactory에서 selectFrom타입을 넣게되는데 여기서 Qblog형태를 사용하게 된다.
- Qblog사용 시 static으로 import하여 사용 할 수 있다.
