# study_spring_boot_jpa_restAPI
인프런 DownLee 'Spring Boot를 이용한 RESTful Web Services 개발' 라이브 코딩 및 필기

<p align="center">
  <img src="https://github.com/AiliartsuaL2/study_spring_boot_jpa_restAPI/files/10334511/RestAPI.pdf">
</p>

SOAP : 메세지의 기본적인 전송수단,, xml을 사용,,
http 통신 프로토콜에서 사용하는 xml 프로토콜 
header와 body로 감싸야함(복잡, 오버헤드가 심함, 무거움)
<firstName>이주호</firstName>
 이런식으로,,


그래서 RESTful이 나옴,,
서버와 클라 사이 통신방식,
상태(자원의 상태)를 전달하는 프로토콜,,
자원 : 컴퓨터의 파일,, DB,,등,,

Restful - RestAPI를 제공하는 웹 서비스
http의 메서드를 활용해서 사용하는것
http메서드 - (GET, PUT , POST , DELETE) ,,
응답코드 - Status Codes,,(200, 404 등 )

URI - 인터넷 자원을 나타내는 유일한 주소,,


SpringBoot 
- 어플리케이션 실행 방법 : @SpringBootApplication 이라는 어노테이션이 있는 클래스(메인 클래스)에서 실행
- 컴포넌트 스캔이라는 작업을 통해서 컴포넌트를 읽어옴
- 개발자가 프로그래밍에 의해서 클래스 인스턴스를 생성하는게 아니라, 스프링 컨테이너에 의해 자동으로 인스턴스가 생성되는것을 제어의 역전(IOC)라고 한다.

Spring.io
- Artifact : 프로젝트의 이름,,??
- Dependencies : 스프링 부트에서 라이브러리 설정,, (pom.xml을 설정해주는거임 수동 설정도 가능)

REST API
User -> Posts 1:N

REST API는 동일해도 Methods에 따라 성질이 달라짐
설명 		/ 			REST API  	/  	HTTP Method
유저조회			/users				GET
유저 생성			/users				POST
상세 조회			/users/{id}			GET
유저 삭제			/users/{id}			DELETE
사용자 포스트 조회    /users/{id}/posts		GET
유저의 포스트 생성	/users/{id}/posts		POST
유저의 포스트 상세조회 /users/{id}/posts 	GET


application.properties > 설정이름 = 값 (text 파일) >> 기본값
application.yml > 설정이름 : 값 (마크업 파일,,) >> 더 직관적

dispatcherServelet > ‘/‘ : 사용자의 요청을 처리해주는 ,, 게이트?
- 클라이언트의 모든 요청을 한 곳으로 받아서 처리함,
- 요청에 맞는 Handler로 요청을 전달..
- Handler의 실행 결과를 Http Response 형태로 만들어 반환
httpMessageConverterAutoConfiguration : JSON포맷으로 바꿔서 클라이언트에게 전송,

@RestController : Controller+ResponseBody,,
- View를 갖지않는 RestData(JSON/XML)를 반환

Path Variable 
URI를 두고 변수를 URI에 할당해서 변수가 바뀌면 그에 맞는 URI가 호출되는
/books/  < 본 URI
/books/1
/books/123 << 가변 변수 URI

@GetMapping(path=“/hello-world-bean/path-variable/{name}”) 
public HelloWroldBean helloWorldBean(@PathVariable String name){
	return new HelloWorldBean(String.format(“”));
}

@RequestBody > Json, XML과같이 Obejct형태의 매개변수를 받기 위해서는 매개변수에 @RequestBody 메서드를 추가해야함

Http Status
- Status Code : 200번대 코드등의 응답코드에다가 상태를 반환을 해주는것이 좋은 RestAPI이다

Restful Service 기능 확장
- Validation(검증,, 유효성 체크),, VO파일에 제약조건을 설정해주고 Exception 처리를 통해 유효성체크 진행
    - hibernate라이브러리의 validation을 통한 체크,, 
    - hibernate : java의 객체와 DB의 엔티티를 매핑하기위한 프레임워크 제공
- 다국어처리 @SpringBootApplication 어노테이션 위치에 Bean을 등록해서 서버가 메모리에 올라 갈 때 먼저 확인해서 진행
- XML format으로 반환하기
- Filtering // 보여주고싶은 정보와 숨기고싶은 정보를 선택 할 수 있음
- Version 관리

필터링 ,,클라이언트에게 전달해주는 값을 제어하는 방법
- 개별 필드에 필터링을 하는경우 VO 필드에다가 각각 @JsonIgnore 어노테이션을 추가한다.
- VO단에서 확인해서 필터링 하는경우(한번에), VO클래스 최상단에 @JsonIgnoreProperties 어노테이션 추가 후 value값에 이름을 설정해준다.
>> 위와같은 방식은 admin, user처럼 적용을 원하는 컨트롤러가 달라도 동일하게 VO가 적용되기때문에 확장에 유연하지 못함.
- 따라서 VO최상단에 @JsonFilter(“필터 이름”) 설정 후 Controller에서 적용시킴 >> 컨트롤러에 필터링 처리 안하는 값은 아예 404 리턴

버전관리
- 너무 많은 URI는 지양
- 잘못된 Header값 사용 지양
- 웹 브라우저의 Cache 기능에 의해서 이전 데이터가 그대로 사용될 수 있음,, 새로운 반영이 안될 수 있음
    - 요청하는 API가 적절히 안되면 캐시삭제 후 재실행

HATEOAS : 현재 리소스와 연관된  Restful에 사용 가능한 상태 정보 제공
- 스프링부트 버전에 따라 코딩이 달라짐,,
- 요청하는 사용자에게 (로직상,, 개발자가 정의)비슷한 메서드의 링크를 전달해주는것.

SWAGGER : 처음에는 단순한 json 표현을 목적으로 했으나, 나중엔 전체 문서 API~ 등을 하며 발전함
	- REST API에는 메서드별 설명이 중요한데, 이걸 홈페이지에 자동으로 만들어주는 역할을 함, 스프링 버전이랑 안맞아서 자꾸 안됨 ㅠㅠ
	- > springDoc을 사용하면 된다.
	- 최신 버전은 Swagger2 말고 Swagger3로 사용 https://oingdaddy.tistory.com/272   (커스터마이징 할 수 있음)
- Swagger 3버전은 @EnableWebMvc사용,


actuator : 서버의 정보와 현재 서버가 가동중인지 등의 정보를 확인 할 수 있음.. (서버의 모니터링 도구,, 커스터마이징 가능)

HAL Browser : 하이퍼 텍스트를 어플리케이션에 부가적인 정보를 추가하는 기능
- API 간 쉽게 검색이 가능해짐.
- Response Message에 적용하게되면, 메세지가 JSON이건 ,XML이건 메타정보를 하이퍼 링크에 포함 할 수 있게됨

Spring Security : 보안을위함..
-  디펜던시 설정 후 서버를 실행하면 password가 발급됨,
-  인증 비밀번호 미입력시 401 Unauthorized Response됨, 
-  Authorization의 Basic Auth에서 id : user, password : 아까 발급된 비밀번호 입력
-  기본값은 위와 같고, yml파일을 수정해서 유저와 비밀번호를 설정 할 수 있다. >> 이렇게하면 아이디 비밀번호 변경시 서버 재기동 해야함,, 따라서 db이용해야함
- WebSecurityConfigurerAdapter > 3.0.0버전부터는 지원 하지않기때문에 바꿔줘야함…

단축키 
-  ctrl + alt + v 변수 할당
-  shift 2번,, 문서 찾기

Java Persistence API (JPA) : ORM 기술에 대한 API  표준 명세, 인터페이스임 // 메서드 선언만했고, 구현체를 만들어야함
- 구현체 : Hibernate ORM(객체와 SQL엔티티 매핑해주는거, 객체지향적이고 비즈니스적인 로직에 집중 가능)
- SpringDataJPA ,, JPA를 더 쉽게 쓰기 위해 도와주는 라이브러리, JPA를 추상화시킨 Repository라는 인터페이스를 제공함

좋은 RestAPI란
- 제공하려는 모든 데이터의 타입은 복수형태로 표현
- http의 장점(헤더,메소드)를 최대한 많이 사용
- 응답코드를 명확히 정해서 반환을 정해야함
- 동사형태보다는 명사형태로 표현
- 일괄된 접근 URI 사용 (http method만 변경하는등) 

