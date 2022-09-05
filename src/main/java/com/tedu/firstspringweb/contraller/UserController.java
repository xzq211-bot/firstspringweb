package com.tedu.firstspringweb.contraller;

import com.tedu.firstspringweb.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


@Controller
public class UserController {

    private static File userDir;//用来表示存放所有用户信息的目录
    static {
        userDir = new File("./users");
        if(!userDir.exists()){
            userDir.mkdirs();
        }
    }


    //@RequestMapping注解用于标注处理某个具体业务的方法，参数传入的字符串与对应页面中表单的action地址一致
    @RequestMapping("/regUser")
    public void reg(HttpServletRequest request, HttpServletResponse response){
        /*
            处理注册的流程:
            1:获取注册页面上表单里用户输入的注册信息
            2:将注册信息保存在硬盘上
            3:回复浏览器一个页面，用来告知注册结果(成功或失败)
         */
        /*
            获取注册页面reg.html中表单提交的注册信息
            请求对象:
            HttpServletRequest
            它表示浏览器本次提交上来的所有内容
         */
        //通过request对象获取表单中4个输入框的内容
        String username = request.getParameter("username");//这里的username就是reg.html上用户名输入框的名字(name属性指定的)
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String ageStr = request.getParameter("age");
        System.out.println(username+","+password+","+nickname+","+ageStr);

        int age = Integer.parseInt(ageStr);
        //2
        User user = new User(username,password,nickname,age);
        //参数1:userDir表示父目录 参数2:userDir目录下的子项
        File file = new File(userDir,username+".obj");
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(user);
            //利用响应对象要求浏览器访问注册成功页面
            response.sendRedirect("/reg_success.html");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
