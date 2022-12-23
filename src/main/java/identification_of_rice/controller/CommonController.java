package identification_of_rice.controller;

import com.school.identification_of_rice.pojo.Admin;
import com.school.identification_of_rice.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommonController {
    @RequestMapping("/user_login")
    public String jumpToLogin(HttpSession session) {
        User user = (User) session.getAttribute("sessionuser");
        if (user != null) {
            return "main";
        } else {
            return "user_login";
        }
    }

    @RequestMapping("/admin_login")
    public String jumpToAdminLogin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("sessionadmin");
        if (admin != null) {
            session.removeAttribute("sessionuser");
            return "management-index";
        } else {
            return "admin_login";
        }
    }

    @RequestMapping("/user_register")
    public String jumpToRegister() {
        return "user_register";
    }

    @RequestMapping("/index")
    public String jumpToIndex() {
        return "index";
    }

    @RequestMapping("/main")
    public String jumpToMain(HttpSession httpSession) {
        if (httpSession.getAttribute("sessionuser") == null)
            httpSession.setAttribute("loginIsNull", "您还未登录，请先登录");
        return "main";
    }

    @RequestMapping("/user_center")
    public String jumpToUserCenter() {
        return "user_center";
    }

    @RequestMapping("/management-index")
    public String jumpToManagementIndex() {
        return "management-index";
    }

    @RequestMapping("/member-add")
    public String jumpMemberAdd() {
        return "member-add";
    }

    @RequestMapping("/admin_center")
    public String jumpToAdminCenter() {
        return "admin_center";
    }

    @RequestMapping(value = "/pest-add")
    public String jumpToPestAdd() {
        return "pest-add";
    }

    @RequestMapping(value = "/user_edit")
    public String jumpToUserEdit(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("sessionuser"));
        return "user_edit";
    }

    @RequestMapping(value = "/pest_recognition")
    public String jumpToPestRecognition() {
        return "pest_recognition";
    }
}
