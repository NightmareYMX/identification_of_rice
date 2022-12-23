package identification_of_rice.service.Impl;

import com.school.identification_of_rice.mapper.AdminMapper;
import com.school.identification_of_rice.pojo.Admin;
import com.school.identification_of_rice.pojo.AdminExample;
import com.school.identification_of_rice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public List<Admin> selectByExample(AdminExample example) {
        return adminMapper.selectByExample(example);
    }

    @Override
    public int updateByPrimaryKeySelective(Admin record) {
        return adminMapper.updateByPrimaryKeySelective(record);
    }
}
