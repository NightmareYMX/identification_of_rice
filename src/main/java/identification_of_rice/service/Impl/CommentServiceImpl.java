package identification_of_rice.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.mapper.CommentMapper;
import com.school.identification_of_rice.pojo.Comment;
import com.school.identification_of_rice.pojo.CommentExample;
import com.school.identification_of_rice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> selectByExample(CommentExample commentExample) {
        return commentMapper.selectByExample(commentExample);
    }

    @Override
    public PageInfo<Comment> selectAll(Integer start, Integer size) {
        PageHelper.startPage(start,size);
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("cid DESC");
        List<Comment> list = commentMapper.selectByExample(commentExample);
        return new PageInfo<>(list);
    }

    @Override
    public int deleteByPrimaryKey(Integer cid) {
        return commentMapper.deleteByPrimaryKey(cid);
    }

    @Override
    public void updateReply(Comment record) {
        commentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<Comment> selectLike(Integer start, Integer size, String username) {
        PageHelper.startPage(start,size);
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andNameLike("%" + username + "%");
        List<Comment> list = commentMapper.selectByExample(commentExample);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Comment> selectByPrimaryKey(Integer start, Integer size, Integer uid) {
        PageHelper.startPage(start,size);
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andUseridEqualTo(uid);
        commentExample.setOrderByClause("cid DESC");
        List<Comment> list = commentMapper.selectByExample(commentExample);
        return new PageInfo<>(list);
    }

    @Override
    public int insertSelective(Comment comment) {
        return commentMapper.insertSelective(comment);
    }
}
