package com.example.springboot;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Person{
    private @Id @GeneratedValue Long id;
    private  String name;
    private  int age;
    private boolean gender;
    
    Person(){};
    Person(String name, int age, boolean gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public String setName(String name){
        this.name = name;
        return this.name;
    }
    public String getName(){
        return this.name;
    }
    public int setAge(int age){
        this.age = age;
        return this.age;
    }
    public int getAge(){
        return this.age;
    }
    public boolean setGender(boolean gender){
        this.gender = gender;
        return this.gender;
    }

    public boolean getGender(){
        return this.gender;
    }
    public String sayHello(){
        return "Hello, world!" + this.name;
    }
    
}
