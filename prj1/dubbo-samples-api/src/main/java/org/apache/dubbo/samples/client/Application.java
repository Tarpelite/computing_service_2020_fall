/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.samples.client;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.samples.api.MyRPCService;


public class Application {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1"); 

    public static void main(String[] args) {
        ReferenceConfig<MyRPCService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        // reference.setRegistry(new RegistryConfig("N/A"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(MyRPCService.class);
        MyRPCService service = reference.get();

        // do Test
        String message = service.setName("Tom");
        System.out.println(message);
        
        String name = service.getName();
        System.out.println(name);

        String age_message = service.setAge(22);
        System.out.println(age_message);

        int res = service.getAge();
        System.out.println("The age of " + name + " is " + Integer.toString(res));

        String gender_message = service.setGender(false);
        System.out.println(gender_message);

        boolean gender = service.getGender();
        System.out.println(name + "is a girl? " + String.valueOf(gender));

        String hello_message = service.sayHello();
        System.out.println(hello_message);
    }
}
