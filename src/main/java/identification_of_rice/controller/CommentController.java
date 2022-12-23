package identification_of_rice.controller;

import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.pojo.Comment;
import com.school.identification_of_rice.pojo.CommentExample;
import com.school.identification_of_rice.pojo.User;
import com.school.identification_of_rice.service.CommentService;
import com.school.identification_of_rice.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping("user_comments")
    public String userComments(Model model, HttpServletRequest request, Integer start){
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("requestURI", requestURI);
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("sessionuser");
        Integer uid = loginUser.getUserid();
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andUseridEqualTo(uid);
        int count = 5;
        if (start == null) {
            start = 1;
        }
        int total = commentService.selectByExample(commentExample).size();
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
        PageInfo<Comment> pagelist = commentService.selectByPrimaryKey(start, count, uid);
        List<Comment> comments = pagelist.getList();
        model.addAttribute("commentSize", total);
        model.addAttribute("pre", pre);
        model.addAttribute("next", next);
        model.addAttribute("last", last);
        model.addAttribute("comments",comments);
        return "user_comments";
    }

    @RequestMapping("/userComment")
    public String send_comment(Comment comment, HttpSession session){
        if (comment.getContent() == null || comment.getContent().equals("")){
            session.setAttribute("msgEmpty","评论信息不能为空");
        }else {
            session.removeAttribute("msgEmpty");
            User loginUser = (User) session.getAttribute("sessionuser");
            int uid = loginUser.getUserid();
            String headimg = loginUser.getUserimg();
            String name = loginUser.getNickname();
            Date date = new Date(System.currentTimeMillis());
            comment.setCtime(date);
            comment.setUserid(uid);
            comment.setHeadimg(headimg);
            comment.setName(name);
            commentService.insertSelective(comment);
        }
        return "redirect:user_comments";
    }

    @RequestMapping("/deleteComment/{cid}")
    public String deleteComment(@PathVariable("cid") Integer cid, HttpServletRequest request){
        commentService.deleteByPrimaryKey(cid);
        return String.format("redirect:%s", request.getSession().getAttribute("requestURI"));
    }

    //管理员后台查看所有评论
    @RequestMapping("/comment-list")
    public String list(Model model, Integer start, HttpServletRequest request){
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("requestURI", requestURI);
        CommentExample commentExample = new CommentExample();
        int count = 5;
        if(start == null){
            start = 1;
        }
        int total = commentService.selectByExample(commentExample).size();
        int last;
        if(0 == total % count){
            last = total / count;
        }else{
            last = total / count + 1;
        }
        int pre = start - 1;
        int next = start + 1;
        pre = pre <= 0 ? 1 : pre;
        next = next > last ? last : next;
        PageInfo<Comment> pagelist = commentService.selectAll(start,count);
        List<Comment> list = pagelist.getList();
        model.addAttribute("pre",pre);
        model.addAttribute("next",next);
        model.addAttribute("last",last);
        model.addAttribute("list",list);
        return "comment-list";
    }

    //管理员后台删除用户评论
    @RequestMapping("/deleteComment")
    public String deleteComment(Integer cid, HttpSession session){
        commentService.deleteByPrimaryKey(cid);
        return String.format("redirect:%s", session.getAttribute("requestURI"));
    }

    //管理员后台回复用户评论
    @RequestMapping("/replyComment")
    public String replyComment(Comment comment, Integer cid, HttpSession session){
        comment.setCid(cid);
        commentService.updateReply(comment);
        return String.format("redirect:%s", session.getAttribute("requestURI"));
    }

    //管理员后台搜索用户和评论
    @RequestMapping(value = "/comment-search")
    public String searchComment(String username, Model model, Integer start, HttpServletRequest request){
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("requestURI", requestURI);
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andNameLike("%" + username + "%");
        int count = 5;
        if(start == null){
            start = 1;
        }
        int total = commentService.selectByExample(commentExample).size();
        int last;
        if(0 == total % count){
            last = total / count;
        }else{
            last = total / count + 1;
        }
        int pre = start - 1;
        int next = start + 1;
        pre = pre <= 0 ? 1 : pre;
        next = next > last ? last : next;
        PageInfo<Comment> pageList = commentService.selectLike(start, count, username);
        List<Comment> list = pageList.getList();
        model.addAttribute("username", username);
        model.addAttribute("pre",pre);
        model.addAttribute("next",next);
        model.addAttribute("last",last);
        model.addAttribute("list",list);
        return "comment-search";
    }
}
