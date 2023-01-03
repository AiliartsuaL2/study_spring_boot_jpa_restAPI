package com.example.test.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired // 어노테이션을 통한 의존성 주입 같은 타입의 빈을 등록시켜줌
    private MessageSource messageSource;

    // GET 메서드
    // URI : /hello-world (엔드포인트)
    // 이전에는 @RequestMapping(method = RequestMethod.GET, path="/hello-world") 썼는데 최근에는 Get,Post 이런거 씀
    // RestAPI에서는 언더바 안쓰고 -씀,
    @GetMapping(path = "/hello-world")
    public String HelloWorld(){
       return "Hello World";
    }
    // alt+enter 쓰면 자동 import
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean HelloWorldBean(){ // 자바 빈 형태로 반환시킴.. 스프링 프레임워크에서는(RestController 어노테이션이) JSON 형태로 반환해줌
        return new HelloWorldBean("HelloWorld");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean HelloWorldBean(@PathVariable String name){ // 오버로딩
        return new HelloWorldBean(String.format("HelloWorld , %s",name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required=false) Locale locale){
        return messageSource.getMessage("greeting.message",null,locale);
    }


}
