package identification_of_rice.service;

import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.pojo.Comment;
import com.school.identification_of_rice.pojo.CommentExample;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentService {

    List<Comment> selectByExample(CommentExample commentExample);

    PageInfo<Comment> selectAll(Integer start, Integer size);

    int deleteByPrimaryKey(Integer cid);

    void updateReply(Comment record);

    PageInfo<Comment> selectLike(Integer start, Integer size, String username);

    int insertSelective(Comment comment);

    PageInfo<Comment> selectByPrimaryKey(Integer start, Integer size, Integer id);

}
