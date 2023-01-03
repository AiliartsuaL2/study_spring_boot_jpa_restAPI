package com.example.test.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // DB관련 Bean
public interface UserRepository extends JpaRepository<Users,Integer> { // 기본적으로 제공하는 findAll, findById 등 사용 가능하고, 추가적으로 Override해서 수정해서 사용가능

}
