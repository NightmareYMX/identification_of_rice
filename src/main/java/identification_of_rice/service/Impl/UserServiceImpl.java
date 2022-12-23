package identification_of_rice.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.mapper.UserMapper;
import com.school.identification_of_rice.pojo.User;
import com.school.identification_of_rice.pojo.UserExample;
import com.school.identification_of_rice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public List<User> selectByExample(UserExample example) {
        return userMapper.selectByExample(example);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public PageInfo<User> selectAll(Integer start, Integer size) {
        PageHelper.startPage(start,size);
        List<User> list = userMapper.listAll();
        return new PageInfo<>(list);
    }

    @Override
    public int deleteByPrimaryKey(Integer userid) {
        return userMapper.deleteByPrimaryKey(userid);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<User> selectLike(Integer start, Integer size, String username) {
        PageHelper.startPage(start,size);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameLike("%" + username + "%");
        List<User> list = userMapper.selectByExample(userExample);
        return new PageInfo<>(list);
    }

    @Override
    public User selectByPrimaryKey(Integer userid) {
        return userMapper.selectByPrimaryKey(userid);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }
}
