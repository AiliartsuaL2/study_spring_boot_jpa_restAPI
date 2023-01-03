package com.example.test.user;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.ctc.wstx.sr.ElemCallback;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/jpa") // 모든 메서드의 prefix
@RestController
public class JavaJPAController {
    @Autowired  // 의존성 주입(빈 생성)
    private UserRepository userRepository;
    private PostRepository postRepository;

    @GetMapping("/users")
    public List<Users> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<Users> retrieveAllUsers(@PathVariable int id){
        Optional<Users> user = userRepository.findById(id); // FindById 반환 타입은 Optional이기 때문에 변수타입을 Optional로 받아줘야함 (nullable)
        if(!user.isPresent()){//nullable이기 때문에 에러처리해줌
            throw new UserNotFoundException(String.format("ID[%s] not found", id)) ;
        }
        // Hateoas
        EntityModel<Users> model = EntityModel.of(user.get()); //올 유저 링크도 생성해줌
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));

        return model; // AllbyArgument ~~ 생성자
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")              //유효성 검사    // Json 타입으로 데이터 받기위함
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user){
        Users newUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        Optional<Users> user = userRepository.findById(id); // FindById 반환 타입은 Optional이기 때문에 변수타입을 Optional로 받아줘야함 (nullable)

        if (!user.isPresent()) {//nullable이기 때문에 에러처리해줌
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")              //유효성 검사    // Json 타입으로 데이터 받기위함
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){

        Optional<Users> user = userRepository.findById(id); // FindById 반환 타입은 Optional이기 때문에 변수타입을 Optional로 받아줘야함 (nullable)

        if (!user.isPresent()) {//nullable이기 때문에 에러처리해줌
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        post.setUsers(user.get()); // 포스트에 사용자 정보 지정
        Post newPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
