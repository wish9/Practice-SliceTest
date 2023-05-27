[Testing 블로그 포스팅 주소](https://velog.io/@wish17/%EC%BD%94%EB%93%9C%EC%8A%A4%ED%85%8C%EC%9D%B4%EC%B8%A0-%EB%B0%B1%EC%97%94%EB%93%9C-%EB%B6%80%ED%8A%B8%EC%BA%A0%ED%94%84-54%EC%9D%BC%EC%B0%A8-Spring-MVC-%ED%85%8C%EC%8A%A4%ED%8C%85Testing)

# 슬라이스 테스트(Slice Test)
## API 계층 테스트

> 슬라이스 테스트
- 개발자가 각 계층에 구현해 놓은 기능들이 잘 동작하는지 특정 계층만 잘라서(Slice) 테스트하는 것


``@SpringBootTest``
- Spring Boot 기반의 애플리케이션을 테스트 하기 위한 Application Context를 생성

``@AutoConfigureMockMvc``
- Controller 테스트를 위한 애플리케이션의 자동 구성 작업을 해준다.

> Gson 라이브러리
- JSON 데이터를 Java 객체로 변환하거나 Java 객체를 JSON 데이터로 변환하는 기능을 제공해주는 라이브러리
- Gson 라이브러리를 사용하기 위해서는 build.gradle의 dependencies {...}에 ``implementation 'com.google.code.gson:gson'`` 를 추가해야 한다.


### Controller 테스트

``MockMvc`` 클래스
- 일종의 Spring MVC 테스트 프레임워크
- Tomcat 같은 서버를 실행하지 않고 Spring 기반 애플리케이션의 Controller를 테스트 할 수 있게 해줌
- MockMvc로 테스트 대상 Controller의 핸들러 메서드에 요청을 전송하기 위해서는 기본적으로 perform() 메서드를 먼저 호출해야 한다.

``mockMvc.perform()``
- Spring MVC Test 프레임워크에서 제공하는 가상의 HTTP 요청을 생성, 처리하는 가상의 서버를 실행해주는 메서드
- 컨트롤러와의 상호작용을 시뮬레이션 해주는 것
- ``ResultActions`` 타입의 객체를 리턴, ``ResultActions`` 객체를 이용해서 전송한 request에 대한 검증을 수행 가능

``MockMvcRequestBuilders`` 클래스
- 빌더 패턴을 통해 request 정보를 채워 넣는데 사용하는 클래스
- ``post("/v11/members")``
    -  HTTP POST METHOD와 request URL을 설정
- ``accept(MediaType.APPLICATION_JSON)``
    - 리턴 받을 응답 타입을 JSON 타입으로 설정
- ``contentType(MediaType.APPLICATION_JSON)``
    -  Content Type을 JSON 타입으로 설정
- content()
    -  request body 데이터를 설정하는데 사용
 
 
[연습내용 풀코드 GitHub 주소](https://github.com/wish9/Practice-Testing/commit/d1ddd32f449096b418f9389a39b7a520ed478ecb)

***

## 데이터 액세스 계층 테스트

데이터 액세스 계층 테스트 시에는 **DB의 상태를 테스트 케이스 실행 이전으로 되돌려서 깨끗하게 만드는 것을 지켜야 한다.**


``@DataJpaTest``
- 인메모리 데이터베이스를 사용하여 테스트 데이터베이스 환경을 구성한다.
- EntityManager를 사용하여 JPA 엔티티를 테스트한다.
- 리포지토리의 CRUD 기능을 테스트한다.
- 스프링부트 애플리케이션 컨텍스트를 제공
- ``@Transactional`` 애너테이션을 포함하고 있음
(하나의 테스트 케이스 실행이 종료되는 시점에 데이터베이스에 저장된 데이터는 rollback 처리)

### @SpringBootTest와 @DataJpaTest의 차이점

``@SpringBootTest``

- 스프링 부트 애플리케이션을 테스트하기 위한 애너테이션이다.
- 실제 애플리케이션과 유사한 환경을 구성하기 때문에, 통합 테스트(Integration Test)를 수행할 때 사용된다.
- 모든 빈(Bean)들을 로드하기 때문에 느리고, 실제 데이터베이스와 연동된다.
- 테스트할 때 사용하는 환경에 따라 달라진다.

``@DataJpaTest``

- JPA 기능만을 테스트하기 위한 애너테이션이다.
- 실제 데이터베이스 대신 메모리 내 데이터베이스(H2)를 사용한다.
- JPA와 관련된 빈들만 로드하기 때문에 빠르고, 테스트 시간을 단축할 수 있다.
- 테스트할 때 사용하는 환경에 따라 달라진다.

***

## MockMvc를 사용한 Controller 슬라이스 테스트 실습

[풀코드 GitHub 주소](https://github.com/wish9/Practice-SliceTest/commit/5bfbf77c381ce9d2a973a853b9d46d3d1f6e2aac)

```java
import com.codestates.member.dto.MemberDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerHomeworkTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        String content = gson.toJson(post);


        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/v11/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/v11/members/"))));
    }

    @Test
    void patchMemberTest() throws Exception {
        // TODO MemberController의 patchMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com","홍길동","010-1111-1111");
        String postContent = gson.toJson(post);

//        ResultActions postActions =
                mockMvc.perform(
                        post("/v11/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postContent)
                );

        MemberDto.Patch patch = new MemberDto.Patch("홍길동","010-1111-1111");
        String patchContent = gson.toJson(patch);

        mockMvc.perform(
                patch("/v11/members/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchContent)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.phone").value(patch.getPhone()));
    }

    @Test
    void getMemberTest() throws Exception {
        // given: MemberController의 getMember()를 테스트하기 위해서 postMember()를 이용해 테스트 데이터를 생성 후, DB에 저장
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com","홍길동","010-1111-1111");
        String postContent = gson.toJson(post);

        ResultActions postActions =
                mockMvc.perform(
                        post("/v11/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(postContent)
                );
        long memberId;
        String location = postActions.andReturn().getResponse().getHeader("Location"); // "/v11/members/1"
        memberId = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));

        // when / then
        mockMvc.perform(
                        get("/v11/members/" + memberId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()));
    }

    @Test
    void getMembersTest() throws Exception {
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com","홍길동","010-1111-1111");
        String postContent = gson.toJson(post);

        MemberDto.Post post2 = new MemberDto.Post("hgd2@gmail.com","둘길동","010-2222-2222");
        String postContent2 = gson.toJson(post2);

        mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent)
        );

        mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent2)
        );


        mockMvc.perform(get("/v11/members?page=1&size=10").accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data[0].email").value(post2.getEmail()), // 페이지네이션 역순정렬 때문에 반대로 해야 함
                        jsonPath("$.data[1].email").value(post.getEmail())
                );

//        MvcResult result =
//                mockMvc.perform(
//                        get("/v11/members?page=1&size=10")
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.data[0].email").value(post2.getEmail()))
//                        .andExpect(jsonPath("$.data[1].email").value(post.getEmail()));
    }

    @Test
    void deleteMemberTest() throws Exception {
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com","홍길동","010-1111-1111");
        String postContent = gson.toJson(post);

        ResultActions postActions =
            mockMvc.perform(
                    post("/v11/members")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postContent)
            );

        long memberId;
        String location = postActions.andReturn().getResponse().getHeader("Location");
        memberId = Long.parseLong(location.substring(location.lastIndexOf("/")+1)); // Id 가져오기

        mockMvc.perform(
                delete("/v11/members/" + memberId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());


//        mockMvc.perform(
//                delete("/v11/members/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isNoContent());
    }
}

```

- import 자동추가 안되는 메서드들(get,post 등)은 하드코딩해서 import해야한다.
- delete test만드는 과정에서 처음에 memberId를 따로 안뽑아오고 주석처럼 하드코딩으로 했더니 class단위로 테스트를 실행시킬 때와 deleteTest메서드 하나만 실행할 때 결과가 다르게 나왔다.
이전 테스트에서 post한 내용이 뒷 테스트에 영향을 줘서 그런 것!!
- [Dto 기본생성자 존재 여부에 따른 mapper 자동생성 오류](https://velog.io/@wish17/%EC%98%A4%EB%A5%98-%EC%A0%95%EB%A6%AC#dto-%EA%B8%B0%EB%B3%B8%EC%83%9D%EC%84%B1%EC%9E%90-%EC%A1%B4%EC%9E%AC-%EC%97%AC%EB%B6%80%EC%97%90-%EB%94%B0%EB%A5%B8-mapper-%EC%9E%90%EB%8F%99%EC%83%9D%EC%84%B1-%EC%98%A4%EB%A5%98) 조심하자
