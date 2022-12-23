package identification_of_rice.service;

import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.pojo.Pest;
import com.school.identification_of_rice.pojo.PestExample;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PestService {
    List<Pest> selectByExample(PestExample example);

    PageInfo<Pest> selectAll(Integer start, Integer size);

    PageInfo<Pest> selectLike(Integer start, Integer size, String pestname);

    void deleteByPrimaryKey(Integer pestid);

    int insertSelective(Pest record);

    int updateByPrimaryKeySelective(Pest record);

    Pest selectByPrimaryKey(Integer pestid);

    PageInfo<Pest> selectByKind(Integer start, Integer count, int kind);
}
