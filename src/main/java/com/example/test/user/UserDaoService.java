package com.example.test.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service // 스테레오타입 ,, 어떤 용도로 쓸건지 작성하는 어노테이션
public class UserDaoService {
    private static List<Users> users = new ArrayList<>();
    private static int userscount = 3;

    static {
        users.add(new Users(1,"Ailiartsua",new Date(),"text1","970312-1234567"));
        users.add(new Users(2,"luvsole",new Date(),"text2","981211-1212000"));
        users.add(new Users(3,"Alice",new Date(),"text3","001212-2411521"));
    }

    public List<Users> findAll(){ // 전체 사용자 조회
        return users;
    }
    public Users save(Users user){
        if(user.getId() == null){
            user.setId(++userscount);
        }
        users.add(user);
        return user;
    }

    public Users findOne(int id){ // 사용자 1명 조회
        for(Users user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public Users deleteById(int id){
        Iterator<Users> iterator = users.iterator();

        while(iterator.hasNext()){
            Users user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    public Users updateById(int id, Users newUser){
        for(int i=0 ;i<users.size();i++){
            if(users.get(i).getId() == id){
                users.set(i,newUser);
                return users.get(i);
            }
        }
        return null;


    }

}
