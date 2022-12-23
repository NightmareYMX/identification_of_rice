package identification_of_rice.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.identification_of_rice.mapper.PestMapper;
import com.school.identification_of_rice.pojo.Pest;
import com.school.identification_of_rice.pojo.PestExample;
import com.school.identification_of_rice.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PestServiceImpl implements PestService {
    @Autowired
    PestMapper pestMapper;
    @Override

    public List<Pest> selectByExample(PestExample example) {
        return pestMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Pest> selectAll(Integer start, Integer size) {
        PageHelper.startPage(start,size);
        List<Pest> list = pestMapper.listAll();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Pest> selectLike(Integer start, Integer size, String pestname) {
        PageHelper.startPage(start,size);
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andNameLike("%" + pestname + "%");
        List<Pest> pestList = pestMapper.selectByExample(pestExample);
        return new PageInfo<>(pestList);
    }

    @Override
    public void deleteByPrimaryKey(Integer pestid) {
        pestMapper.deleteByPrimaryKey(pestid);
    }

    @Override
    public int insertSelective(Pest record) {
        return pestMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Pest record) {
        return pestMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Pest selectByPrimaryKey(Integer pestid) {
        return pestMapper.selectByPrimaryKey(pestid);
    }

    @Override
    public PageInfo<Pest> selectByKind(Integer start, Integer count, int kind) {
        PageHelper.startPage(start, count);
        PestExample pestExample = new PestExample();
        PestExample.Criteria criteria = pestExample.createCriteria();
        criteria.andKindEqualTo(kind);
        List<Pest> pestList = pestMapper.selectByExample(pestExample);
        return new PageInfo<>(pestList);
    }
}
