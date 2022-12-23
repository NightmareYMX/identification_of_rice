package identification_of_rice.controller;

import com.school.identification_of_rice.pojo.Admin;
import com.school.identification_of_rice.pojo.AdminExample;
import com.school.identification_of_rice.service.AdminService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
@SessionAttributes({"admlogin", "adminedit"})
@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    //管理员登录
    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    public String adminLogin(@Param("username") String username, @Param("password") String password, ModelMap map, HttpSession session) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<Admin> admins = adminService.selectByExample(adminExample);
        if (!admins.isEmpty()) {
            session.setAttribute("sessionadmin", admins.get(0));
            session.removeAttribute("admlogin");
            session.removeAttribute("sessionuser");
            return "management-index";
        } else {
            String msg = "用户名或密码错误，请重新输入";
            map.addAttribute("admlogin", msg);
            return "redirect:admin_login";
        }
    }

    //管理员重新登陆
    @RequestMapping("/admin_re_login")
    public String user_logout(HttpSession session) {
        session.removeAttribute("sessionadmin");
        return "redirect:admin_login";
    }

    //管理员修改自己信息
    @RequestMapping(value = "/editAdmin", method = RequestMethod.PUT)
    public String editAdmin(Admin admin, HttpSession session) {
        Integer mid = admin.getMid();
        String username = admin.getUsername();
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andMidNotEqualTo(mid);
        criteria.andUsernameEqualTo(username);
        List<Admin> adminList = adminService.selectByExample(adminExample);
        if (adminList.isEmpty()) {
            session.removeAttribute("adminedit");
            session.setAttribute("sessionadmin", admin);
            adminService.updateByPrimaryKeySelective(admin);
        } else {
            session.setAttribute("adminedit", "修改失败，用户名已存在！");
        }
        return "redirect:admin_center";
    }

    //管理员退出登陆
    @RequestMapping("/admin_logout")
    public String admin_logout(HttpSession session) {
        session.removeAttribute("sessionadmin");
        return "index";
    }

}
