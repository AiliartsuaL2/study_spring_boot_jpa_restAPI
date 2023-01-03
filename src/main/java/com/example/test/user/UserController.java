package com.example.test.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private UserDaoService service;

    //생성자를 통한 의존성 주입(DI)
    public UserController(UserDaoService service){
        this.service = service;
    }
    @GetMapping("/users") // 사용자 조회 API
    public List<Users> retrieveAllusers(){
        return service.findAll();
    }
    // Get메서드 /users/1 or /users/10 - > 서버측 전달시에는 문자형태로 전달이 됨(JSON 이니까)

    @GetMapping("/users/{id}")
    public EntityModel<Users> retrieveUser(@PathVariable int id){ // 근데 매개변수에서 int형으로 지정해주면 자동 형변환이 됨
        Users user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        //Hateoas
        EntityModel<Users> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllusers());
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) { // 유효성 검사를 위해 Valid 어노테이션 추가
        Users savedUser = service.save(user);        // Json 형태의 데이터를 받기위해 RequestBody 어노테이션 추가
        // 상태코드에 Created를 반환해서 보내준다.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()// 요청값을 변환하는 메서드 ,현재 요청된 Request값
                .path("/{id}")// 반환값
                .buildAndExpand(savedUser.getId()) // 가변변수에 새롭게 만들어진 아이디값
                .toUri();//uri형태로 변경

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        Users user = service.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }
    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody Users user){
        Users updatedUser = service.updateById(id,user);
        if(updatedUser==null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }
}
