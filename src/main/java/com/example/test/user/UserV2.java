package com.example.test.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data// 게터세터 어노테이션
@AllArgsConstructor // 생성자 어노테이션
@NoArgsConstructor // 디폴트 생성자 만드는 어노테이션
// @JsonIgnoreProperties(value={"pass","ssn"}) // 필터링용 어노테이션 (전체 필드중 제외할 필드를 value의 값으로 써줌)
@JsonFilter("UserInfoV2")
public class UserV2 extends Users{ // 상속시 디폴트 생성자를 넣어야함.(NoArgsConstructor)
    private String grade;

}
