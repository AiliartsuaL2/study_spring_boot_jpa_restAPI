package com.example.test.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok Dependency : 빈 클래스를 만들 때, 게터 세터, 생성자 등을 자동 생성해줌,, >> 개발시간 단축
// 빈클래스는 VO..?
@Data //lombok의 Data 어노테이션을 통해 게터 세터 메서드, 생성자등을 자동 생성
@AllArgsConstructor // 생성자 자동 생성해주는 메서
@NoArgsConstructor // 디폴트 생성자(매개변수가 없는걸 만들고싶으면 )를 같이 만들 수 있음
public class HelloWorldBean {
    private String message;

}
