package org.apache.dubbo.samples.provider;

import org.apache.dubbo.samples.api.MyRPCService;


public class MyRPCServiceImpl implements MyRPCService{
    String name;
    int age;
    boolean gender;

    @Override
    public String setName(String name){
        this.name = name;
        return "Set new name:" + this.name;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String setAge(int age){
        this.age = age;
        return "Set new age: " + Integer.toString(this.age);
    }

    @Override
    public int getAge(){
        return this.age;
    }

    @Override
    public String setGender(boolean gender){
        this.gender = gender;
        return "Set new gender:" + Boolean.toString(this.gender);
    }

    @Override
    public boolean getGender(){
        return this.gender;

    }

    @Override
    public String sayHello(){
        return "Hello, world!" + this.name;
    }



}