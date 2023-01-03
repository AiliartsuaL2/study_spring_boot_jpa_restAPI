package com.example.test.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // User : post >>  1:(0,N),, Main : Sub - > Parent -> Child

    @JsonBackReference
    @ManyToOne // N:1 ,, Post입장이 Many, User가 One,, FetchType Lazy : 지연로딩방식,, PostEntity가 로딩되는 시점에 필요한 사용자 데이터를 가지고 오겠다는 뜻
    @JsonIgnore // json 데이터타입 할 경우 필터링됨,, 외부 공개 X
    private Users users;

}
