package com.example.mvc_thymeleaf.chap01.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString //toString을 내부 멤버값으로 변경
@Setter
@Getter // lombok 설치 시 사용 가능, getter, setter를 자동생성
public class Human { // 사람이 가져야 하는 정보

    public String name;
    public int age;
    public String address;
}
