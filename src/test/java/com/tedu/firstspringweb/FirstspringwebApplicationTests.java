package com.tedu.firstspringweb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileFilter;

@SpringBootTest
class FirstspringwebApplicationTests {

    @Test
    void contextLoads() {
        File dir = new File("./users");
        String uname="";
        FileFilter filter=new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name= file.getName();
                return name.contains(uname+".obj");
            }
        };
        File[] lists=dir.listFiles(filter);
        for(File list:lists){
            System.out.println(list);
        }
    }

}
