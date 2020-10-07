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


public class PersonService {

    private Person p = new Person();
   
    public String setName(String name){
        this.p.setName(name);
        return this.p.getName();
    }

    public String getName(){
        return this.p.getName();
    }

    public int setAge(int age){
        this.p.setAge(age);
        return this.p.getAge();
    }

    public int getAge(){
        return this.p.getAge();
    }

    public boolean setGender(boolean gender){
        this.p.setGender(gender);
        return this.p.getGender();
    }

    public boolean getGender(){
        return this.p.getGender();
    }

    public String sayHello(){
        return "Hello, world! " + this.p.getName();
    }
}
