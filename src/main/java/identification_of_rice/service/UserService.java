package identification_of_rice.service;

import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.pojo.User;
import com.school.identification_of_rice.pojo.UserExample;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    List<User> selectByExample(UserExample example);

    int insertSelective(User record);

    PageInfo<User> selectAll(Integer start, Integer size);

    int deleteByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    PageInfo<User> selectLike(Integer start, Integer size, String username);

    User selectByPrimaryKey(Integer userid);

    int insert(User record);
}
