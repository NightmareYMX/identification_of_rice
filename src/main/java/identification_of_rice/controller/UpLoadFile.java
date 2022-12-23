package identification_of_rice.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.school.identification_of_rice.mapper.CommentMapper;
import com.school.identification_of_rice.pojo.Comment;
import com.school.identification_of_rice.pojo.CommentExample;
import com.school.identification_of_rice.pojo.Pest;
import com.school.identification_of_rice.pojo.User;
import com.school.identification_of_rice.service.CommentService;
import com.school.identification_of_rice.service.PestService;
import com.school.identification_of_rice.service.UserService;
import com.school.identification_of_rice.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/upLoadFile")
public class UpLoadFile {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    PestService pestService;

    @RequestMapping(value = "/upLoadProfile")
    @ResponseBody
    public String upLoadProfile(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        if (file != null) {
            try {
                //获取上传的文件的文件名
                String fileName = file.getOriginalFilename();
                //处理文件重名问题
                String hzName = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID().toString() + hzName;
                //获取服务器中profile目录的路径
                ServletContext servletContext = request.getSession().getServletContext();
                String profilePath = servletContext.getRealPath("profile");
                File profile = new File(profilePath);
                if(!profile.exists()){
                    profile.mkdir();
                }
                String finalPath = profilePath + File.separator + fileName;
                System.out.println(finalPath);
                //实现上传功能
                file.transferTo(new File(finalPath));
                String imgUrl =  "/profile/" + fileName;
                if (request.getSession().getAttribute("sessionuser") != null) {
                    User user = (User) request.getSession().getAttribute("sessionuser");
                    user.setUserimg(imgUrl);
                    request.getSession().setAttribute("sessionuser", user);
                    userService.updateByPrimaryKeySelective(user);
                    CommentExample commentExample = new CommentExample();
                    CommentExample.Criteria criteria = commentExample.createCriteria();
                    criteria.andUseridEqualTo(user.getUserid());
                    commentMapper.updateByExampleSelective(new Comment(null, null, imgUrl, null, null, null, null), commentExample);
                } else {
                    String requestURI = (String) request.getSession().getAttribute("id");
                    String[] split = requestURI.split("/");
                    Integer userid = Integer.valueOf(split[split.length - 1]);
                    User user = userService.selectByPrimaryKey(userid);
                    user.setUserimg(imgUrl);
                    userService.updateByPrimaryKeySelective(user);
                    CommentExample commentExample = new CommentExample();
                    CommentExample.Criteria criteria = commentExample.createCriteria();
                    criteria.andUseridEqualTo(user.getUserid());
                    commentMapper.updateByExampleSelective(new Comment(null, null, imgUrl, null, null, null, null), commentExample);
                }
                result.put("code", 200);
                result.put("msg", "success");
            } catch (Exception e) {
                result.put("code", -1);
                result.put("msg", "文件上传出错，请重新上传！");
                e.printStackTrace();
            }
        } else {
            result.put("code", -1);
            result.put("msg", "未获取到有效的文件信息，请重新上传!");
        }
        return JSONUtils.toJSONString(result);
    }

    @RequestMapping(value = "/upLoadPest")
    @ResponseBody
    public String upLoadPest(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        if (file != null) {
            try {
                //获取上传的文件的文件名
                String fileName = file.getOriginalFilename();
                //处理文件重名问题
                String hzName = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID().toString() + hzName;
                //获取服务器中profile目录的路径
                ServletContext servletContext = request.getSession().getServletContext();
                String profilePath = servletContext.getRealPath("pest");
                File profile = new File(profilePath);
                if(!profile.exists()){
                    profile.mkdir();
                }
                String finalPath = profilePath + File.separator + fileName;
                //实现上传功能
                file.transferTo(new File(finalPath));
                String imgUrl = "/pest/" + fileName;
                String requestURI = (String) request.getSession().getAttribute("id");
                String[] split = requestURI.split("/");
                Integer pestid = Integer.valueOf(split[split.length - 1]);
                Pest pest = pestService.selectByPrimaryKey(pestid);
                pest.setInfourl(imgUrl);
                pestService.updateByPrimaryKeySelective(pest);
                result.put("code", 200);
                result.put("msg", "success");
            } catch (Exception e) {
                result.put("code", -1);
                result.put("msg", "文件上传出错，请重新上传！");
                e.printStackTrace();
            }
        } else {
            result.put("code", -1);
            result.put("msg", "未获取到有效的文件信息，请重新上传!");
        }
        return JSONUtils.toJSONString(result);
    }

    @RequestMapping(value = "/uploadRecognition")
    @ResponseBody
    public String uploadRecognition(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        if (file != null) {
            try {
                //获取上传的文件的文件名
                String fileName = file.getOriginalFilename();
                //处理文件重名问题
                String hzName = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID().toString() + hzName;
                //获取服务器中profile目录的路径
                ServletContext servletContext = request.getSession().getServletContext();
                String profilePath = servletContext.getRealPath("deep_learning");
                File profile = new File(profilePath);
                if(!profile.exists()){
                    profile.mkdir();
                }
                String finalPath = profilePath + File.separator + fileName;
                //实现上传功能
                file.transferTo(new File(finalPath));
                String imgUrl = "/deep_learning/" + fileName;
                request.getSession().setAttribute("preUrl", imgUrl);
                String pestResult = HttpUtils.getResult(fileName);
                result.put("pestResult", pestResult);
                result.put("code", 200);
                result.put("msg", "success");
            } catch (Exception e) {
                result.put("code", -1);
                result.put("msg", "文件上传出错，请重新上传！");
                e.printStackTrace();
            }
        } else {
            result.put("code", -1);
            result.put("msg", "未获取到有效的文件信息，请重新上传!");
        }
        return JSONUtils.toJSONString(result);
    }
}
