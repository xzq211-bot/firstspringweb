package com.tedu.firstspringweb.contraller;

import com.tedu.firstspringweb.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;


@Controller
public class UserController {

    private static File userDir;//用来表示存放所有用户信息的目录

    static {
        userDir = new File("./users");
        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }


    //@RequestMapping注解用于标注处理某个具体业务的方法，参数传入的字符串与对应页面中表单的action地址一致
    @RequestMapping("/regUser")
    public void reg(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");//这里的username就是reg.html上用户名输入框的名字(name属性指定的)
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String ageStr = request.getParameter("age");
        System.out.println(username + "," + password + "," + nickname + "," + ageStr);

        int age = Integer.parseInt(ageStr);
        //2
        User user = new User(username, password, nickname, age);
        //参数1:userDir表示父目录 参数2:userDir目录下的子项
        File file = new File(userDir, username + ".obj");
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(user);
            //利用响应对象要求浏览器访问注册成功页面
            response.sendRedirect("/reg_success.html");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/logUser", method = RequestMethod.GET)
    public void check(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        String username = request.getParameter("username");//这里的username就是reg.html上用户名输入框的名字(name属性指定的)
        String password = request.getParameter("pwd");
        File dir = new File("./users");
        if (dir.isDirectory()) {
            FileFilter filter = new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String name = file.getName();
                    return name.contains(username);
                }
            };
            File[] list = dir.listFiles(filter);
            if (list.length > 0) {
                FileInputStream fis = new FileInputStream(list[0]);
                ObjectInputStream ois = new ObjectInputStream(fis);
                User user = (User) ois.readObject();
                ois.close();
                System.out.println(user);
                if (user.getPassword().equals(password)) {
                    response.setStatus(200);
                    System.out.println(response.getStatus());
//                response.sendRedirect("/reg_success.html");
                }else {
                    //密码错误
                }
            }else{
                //用户不存在
            }

        }
    }

}
