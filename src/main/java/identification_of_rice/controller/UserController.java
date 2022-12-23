package identification_of_rice.controller;

import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.mapper.CommentMapper;
import com.school.identification_of_rice.pojo.Comment;
import com.school.identification_of_rice.pojo.CommentExample;
import com.school.identification_of_rice.pojo.User;
import com.school.identification_of_rice.pojo.UserExample;
import com.school.identification_of_rice.service.UserService;
import com.school.identification_of_rice.utils.UrlUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
@SessionAttributes({"userlist", "user", "msglogin", "msgregister", "memberadd", "memberedit", "useredit"})
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    CommentMapper commentMapper;
    //用户登录
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public String login(@Param("username") String username, @Param("password") String password, ModelMap map, HttpSession session) {
        //已登录用户不需要再次登录
        if (session.getAttribute("sessionuser") != null) {
            return "forward:main";
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<User> userList = userService.selectByExample(userExample);
        //查询并判断是否为空
        if (!userList.isEmpty()) {
            //session的创建,在会话变量中保存user
            session.setAttribute("sessionuser", userList.get(0));
            session.removeAttribute("msglogin");
            session.removeAttribute("loginIsNull");
            return "main";
        } else {
            String msg = "用户名或密码错误，请重新输入";
            map.addAttribute("msglogin", msg);
            return "redirect:user_login";
        }
    }

    //用户注册
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public String register(User user, HttpSession session) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> userList = userService.selectByExample(userExample);
        if (!userList.isEmpty()) {
            session.setAttribute("msgregister", "注册失败，用户名已存在!");
            return "redirect:user_register";
        } else {
            user.setUserimg("/profile/default.png");
            userService.insert(user);
            session.removeAttribute("msgregister");
            return "user_login";
        }
    }

    //用户在用户中心修改个人信息
    @RequestMapping("/updateUser")
    public String update_user_own(User user, HttpSession session) {
        User loginuser = (User) session.getAttribute("sessionuser");
        user.setUserimg(loginuser.getUserimg());
        user.setUserid(loginuser.getUserid());
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUseridNotEqualTo(user.getUserid());
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> userList = userService.selectByExample(userExample);
        if (userList.isEmpty()) {
            userService.updateByPrimaryKeySelective(user);
            CommentExample commentExample = new CommentExample();
            CommentExample.Criteria criteria1 = commentExample.createCriteria();
            criteria1.andUseridEqualTo(user.getUserid());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            if (!comments.isEmpty()){
                commentMapper.updateByExampleSelective(new Comment(null, user.getNickname(), user.getUserimg(), null, null, null, null), commentExample);
            }
            session.setAttribute("sessionuser", user);
            session.removeAttribute("useredit");
            return "redirect:user_center";
        } else {
            session.setAttribute("useredit", "用户添加失败，用户名已存在！");
            return "redirect:user_edit";
        }
    }

    //后台显示用户列表
    @RequestMapping("/member-list")
    public String list(Model model, Integer start, HttpServletRequest request) {
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("requestURI", requestURI);
        UserExample userExample = new UserExample();
        int count = 5;
        if (start == null) {
            start = 1;
        }
        int total = userService.selectByExample(userExample).size();
        int last;
        if (0 == total % count) {
            last = total / count;
        } else {
            last = total / count + 1;
        }
        int pre = start - 1;
        int next = start + 1;
        pre = pre <= 0 ? 1 : pre;
        next = next > last ? last : next;
        PageInfo<User> pagelist = userService.selectAll(start, count);
        List<User> list = pagelist.getList();
        model.addAttribute("pre", pre);
        model.addAttribute("next", next);
        model.addAttribute("last", last);
        model.addAttribute("userlist", list);
        return "member-list";
    }

    //管理员后台搜索用户
    @RequestMapping(value = "/member-search")
    public String searchUser(@Param("username") String username, Integer start, Model model, HttpServletRequest request) {
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("requestURI", requestURI);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameLike("%" +username + "%");
        int count = 5;
        if (start == null) {
            start = 1;
        }
        int total = userService.selectByExample(userExample).size();
        int last;
        if (0 == total % count) {
            last = total / count;
        } else {
            last = total / count + 1;
        }
        int pre = start - 1;
        int next = start + 1;
        pre = pre <= 0 ? 1 : pre;
        next = next > last ? last : next;
        PageInfo<User> pageList = userService.selectLike(start, count, username);
        List<User> list = pageList.getList();
        model.addAttribute("username", username);
        model.addAttribute("pre", pre);
        model.addAttribute("next", next);
        model.addAttribute("last", last);
        model.addAttribute("userlist", list);
        return "member-search";
    }

    //管理员后台添加新用户
    @RequestMapping(value = "/memberAdd", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String addUser(User user, HttpSession session) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> userList = userService.selectByExample(userExample);
        if (!userList.isEmpty()) {
            session.setAttribute("memberadd", "用户添加失败，用户名已存在！");
            return "redirect:member-add";
        } else {
            userService.insertSelective(user);
            session.removeAttribute("memberadd");
            return "redirect:member-list";
        }
    }

    //管理员后台注销用户
    @RequestMapping(value = "/deleteUser/{userid}")
    public String deleteUser(@PathVariable("userid") Integer userid, HttpSession session) {
        userService.deleteByPrimaryKey(userid);
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andUseridEqualTo(userid);
        commentMapper.deleteByExample(commentExample);
        return String.format("redirect:%s", session.getAttribute("requestURI"));
    }

    //管理员后台跳转修改用户
    @RequestMapping("/toChangeUser/{userid}")
    public String toChange(@PathVariable("userid") Integer userid, Model model, HttpServletRequest request) {
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("id", requestURI);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUseridEqualTo(userid);
        List<User> userList = userService.selectByExample(userExample);
        model.addAttribute("user", userList.get(0));
        return "member-edit";
    }

    //管理员后台修改用户
    @RequestMapping(value = "/editUser", method = RequestMethod.PUT)
    public String changeUser(User user, HttpSession session) {
        Integer userid = user.getUserid();
        String username = user.getUsername();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUseridNotEqualTo(userid);
        criteria.andUsernameEqualTo(username);
        List<User> userList = userService.selectByExample(userExample);
        if (userList.isEmpty()) {
            session.removeAttribute("memberedit");
            userService.updateByPrimaryKeySelective(user);
            return String.format("redirect:%s", session.getAttribute("requestURI"));
        } else {
            session.setAttribute("memberedit", "修改失败，用户名已存在");
            return String.format("redirect:toChange/%s", userid);
        }
    }

    //用户退出登陆
    @RequestMapping("/user_logout")
    public String user_logout(HttpSession session) {
        session.removeAttribute("sessionuser");
        session.setAttribute("loginIsNull", "您还未登录，请先登录");
        return "main";
    }
}
