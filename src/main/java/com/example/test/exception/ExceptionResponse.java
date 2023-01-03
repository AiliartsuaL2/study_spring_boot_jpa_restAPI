package com.example.test.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // 게터세터
@AllArgsConstructor // 모든 필드를 매개변수로 갖고있는 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
public class ExceptionResponse {
    private Date timeStamp;
    private String message;
    private String details;
}
