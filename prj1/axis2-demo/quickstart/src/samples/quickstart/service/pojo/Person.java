/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package samples.quickstart.service.pojo;

import java.util.HashMap;

public class Person{
    private  Long id;
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