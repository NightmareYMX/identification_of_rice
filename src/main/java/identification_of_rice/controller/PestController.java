package identification_of_rice.controller;

import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.pojo.Pest;
import com.school.identification_of_rice.pojo.PestExample;
import com.school.identification_of_rice.service.PestService;
import com.school.identification_of_rice.utils.UrlUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@SessionAttributes({"pestadd", "pestedit"})
@Controller
public class PestController {
    @Autowired
    PestService pestService;

    //病虫百科界面跳转
    @RequestMapping("/pest_dict")
    public String Startpest_dict(Model model, Integer start) {
        PestExample pestExample = new PestExample();
        int count = 7;
        if (start == null) {
            start = 1;
        }
        int total = pestService.selectByExample(pestExample).size();
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
        PageInfo<Pest> pagelist = pestService.selectAll(start, count);
        List<Pest> list = pagelist.getList();
        model.addAttribute("pre", pre);
        model.addAttribute("next", next);
        model.addAttribute("last", last);
        model.addAttribute("pestList", list);
        return "pest_dict";
    }

    //病虫种类界面
    @RequestMapping("/pest_kind")
    public String pest_select(Model model, @Param("kind") int kind, Integer start){
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andKindEqualTo(kind);
        int count = 5;
        if (start == null) {
            start = 1;
        }
        int total = pestService.selectByExample(pestExample).size();
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
        PageInfo<Pest> pagelist = pestService.selectByKind(start, count, kind);
        List<Pest> list = pagelist.getList();
        model.addAttribute("kind", kind);
        model.addAttribute("pre", pre);
        model.addAttribute("next", next);
        model.addAttribute("last", last);
        model.addAttribute("list", list);
        return "pest_kind";
    }

    @RequestMapping("/pest_result")
    public String pestResult(@Param("name") String name, HttpSession session) {
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Pest> pestList = pestService.selectByExample(pestExample);
        if (pestList.isEmpty()) {
            session.setAttribute("pestfindmsg", "未找到您所需要的信息！请将信息输入完整或重新上传图片。");
            return "redirect:pest_recognition";
        } else {
            session.setAttribute("resultpest", pestList.get(0));
            session.removeAttribute("pestfindmsg");
            session.setAttribute("preurl", pestList.get(0).getInfourl());
            return "result";
        }
    }

    @RequestMapping("/pest_find")
    public String pestFind(@Param("name") String name, Model model) {
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Pest> pestList = pestService.selectByExample(pestExample);
        model.addAttribute("pest", pestList.get(0));
        return "pest_detail";
    }

    @RequestMapping("pest_detail")
    public String pestDetail(@Param("pid") Integer pestid, Model model) {
        Pest pest = pestService.selectByPrimaryKey(pestid);
        model.addAttribute("pest", pest);
        return "pest_detail";
    }

    //后台病虫列表界面跳转
    @RequestMapping("/pest-list")
    public String list(Model model, Integer start, HttpServletRequest request) {
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("requestURI", requestURI);
        PestExample pestExample = new PestExample();
        int count = 5;
        if (start == null) {
            start = 1;
        }
        int total = pestService.selectByExample(pestExample).size();
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
        PageInfo<Pest> pagelist = pestService.selectAll(start, count);
        List<Pest> list = pagelist.getList();
        model.addAttribute("pre", pre);
        model.addAttribute("next", next);
        model.addAttribute("last", last);
        model.addAttribute("pestList", list);
        return "pest-list";
    }

    //管理员后台搜索病虫害
    @RequestMapping(value = "/pest-search")
    public String searchPest(@Param("pestname") String pestname, Model model, Integer start, HttpServletRequest request) {
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("requestURI", requestURI);
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andNameLike("%" + pestname + "%");
        int count = 5;
        if (start == null) {
            start = 1;
        }
        int total = pestService.selectByExample(pestExample).size();
        int last;
        if (0 == total % count) {
            last = total / count;
        } else {
            last = total / count + 1;
        }
        int pre = start - 1;
        int next = start + 1;
        pre = pre <= 0 ? 1 : pre;
        pre = pre <= 0 ? 1 : pre;
        next = next > last ? last : next;
        PageInfo<Pest> pagelist = pestService.selectLike(start, count, pestname);
        List<Pest> list = pagelist.getList();
        model.addAttribute("pestname", pestname);
        model.addAttribute("pre", pre);
        model.addAttribute("next", next);
        model.addAttribute("last", last);
        model.addAttribute("pestList", list);
        return "pest-search";
    }

    //管理员后台添加病虫害
    @RequestMapping(value = "/pestAdd")
    public String pestAdd(Pest pest, HttpSession session) {
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andNameEqualTo(pest.getName());
        List<Pest> pestList = pestService.selectByExample(pestExample);
        if (pestList.isEmpty()) {
            pestService.insertSelective(pest);
            session.removeAttribute("pestadd");
            return "redirect:pest-list";
        } else {
            session.setAttribute("pestadd", "添加失败，病虫害已存在");
            return "redirect:pest-add";
        }
    }

    //管理员后台删除病虫害
    @RequestMapping(value = "/deletePest")
    public String deleteSearch(Integer pestid, HttpSession session) {
        pestService.deleteByPrimaryKey(pestid);
        return String.format("redirect:%s", session.getAttribute("requestURI"));
    }

    //管理员后台跳转修改病虫害
    @RequestMapping(value = "/toChangePest/{pestid}")
    public String toChangePest(@PathVariable("pestid") Integer pestid, Model model, HttpServletRequest request) {
        String requestURI = UrlUtils.getWholeUrl(request);
        request.getSession().setAttribute("id", requestURI);
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andPestidEqualTo(pestid);
        List<Pest> pestList = pestService.selectByExample(pestExample);
        model.addAttribute("pest", pestList.get(0));
        return "pest-edit";
    }

    //管理员后台修改病虫害
    @RequestMapping(value = "editPest")
    public String editPest(Pest pest, HttpSession session) {
        Integer pestid = pest.getPestid();
        String name = pest.getName();
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andPestidNotEqualTo(pestid);
        List<Pest> pestList = pestService.selectByExample(pestExample);
        if (pestList.isEmpty()) {
            session.removeAttribute("pestedit");
            pestService.updateByPrimaryKeySelective(pest);
            return String.format("redirect:%s", session.getAttribute("requestURI"));
        } else {
            session.setAttribute("pestedit", "修改失败，病虫害已存在");
            return String.format("redirect:toChange/%s", pestid);
        }
    }
}
