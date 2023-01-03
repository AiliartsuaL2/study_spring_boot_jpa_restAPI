package com.example.test.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin") // 전체 클래스 앞쪽에 RequestMappin >> 아래의 URI 앞에 prefix를 /admin을 추가하는거임
public class AdminUserController {
    private UserDaoService service;

    //생성자를 통한 의존성 주입(DI)
    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users") // 전체 사용자 조회 API
    public MappingJacksonValue retrieveAllusers(){
        List<Users> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password"); // 데이터 필터링을 해주고

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter); // VO단에서 선언했던 필터의 어노테이션 이름을 넣어 추가해줌.

        MappingJacksonValue mapping = new MappingJacksonValue(users); // 잭슨 형태로 VO값을 매핑하고
        mapping.setFilters(filters); // 필터적용

        return mapping;
    }
    // Get메서드 admin/v1/users/1 or admin/v1/users/10 - > 서버측 전달시에는 문자형태로 전달이 됨(JSON 이니까)

//  @GetMapping(value="/users/{id}",headers = "X-API-VERSION=1") // 헤더를 이용한 버전관리
//  @GetMapping(value="/users/{id}/", params = "version=1") //  파라미터를 이용한 버전관리
    //@GetMapping("v1/users/{id}") // URI를 이용한 버전관리
    @GetMapping(value ="/users/{id}", produces ="application/vnd.company.appv1+json") // 마임 타입을 이용한 버전관리
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){ // 근데 매개변수에서 int형으로 지정해주면 자동 형변환이 됨
        Users user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn"); // 데이터 필터링을 해주고
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter); // VO단에서 선언했던 필터의 어노테이션 이름을 넣어 추가해줌.
        MappingJacksonValue mapping = new MappingJacksonValue(user); // 잭슨 형태로 VO값을 매핑하고
        mapping.setFilters(filters); // 필터적용

        return mapping;
    }

//    @GetMapping(value="/users/{id}",headers = "X-API-VERSION=2")// 헤더를 이용한 버전관리
//    @GetMapping(value="/users/{id}/", params = "version=2")
    //@GetMapping("v2/users/{id}")
    @GetMapping(value ="/users/{id}", produces ="application/vnd.company.appv2+json") // 마임 타입을 이용한 버전관리
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){ // 근데 매개변수에서 int형으로 지정해주면 자동 형변환이 됨
        Users user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2); // 스프링에서 제공, 빈들간 관련의 작업을 도와주는 클래스, 중 copyProperties는 공통적인 필드를 카피해주는 기능이 있음.
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade"); // 데이터 필터링을 해주고

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter); // VO단에서 선언했던 필터의 어노테이션 이름을 넣어 추가해줌.

        MappingJacksonValue mapping = new MappingJacksonValue(userV2); // 잭슨 형태로 VO값을 매핑하고
        mapping.setFilters(filters); // 필터적용

        return mapping;
    }

}
