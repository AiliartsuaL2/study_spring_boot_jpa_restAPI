package com.example.test.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data// 게터세터 어노테이션
@AllArgsConstructor // 생성자 어노테이션
// @JsonIgnoreProperties(value={"pass","ssn"}) // 필터링용 어노테이션 (전체 필드중 제외할 필드를 value의 값으로 써줌)
@NoArgsConstructor
//@JsonFilter("UserInfo")
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity // JPA와 VO 매핑시켜주는 어노테이션
public class Users {

    @Id //JPA,, DB와 매핑시 자동으로 ID 지정
    @GeneratedValue // DB와 매핑시 아이디값 자동생성(시퀀스로)
    private Integer id;

    @Size(min=2, message="Name은 2글자 이상 입력해 주세요.")
    @Schema(description = "사용자 이름을 입력해 주세요")
    private String name;

    @Past
    @Schema(description = "사용자의 등록일을 입력해 주세요")
    private Date joinDate;

//    @JsonIgnore //개별 필터링용 어노테이션
    @Schema(description = "사용자의 비밀번호를 입력해 주세요")
    private String password;

//    @JsonIgnore //개별 필터링용 어노테이션
    @Schema(description = "사용자의 주민번호를 입력해 주세요")
    private String ssn;

    @OneToMany(mappedBy = "users") //users 테이블과 매핑시키고, 1:N
    private List<Post> posts;

    public Users(int id, String name, Date joinDate, String password, String ssn) {
        this.id= id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
