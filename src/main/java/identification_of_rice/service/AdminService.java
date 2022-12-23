package identification_of_rice.service;

import com.school.identification_of_rice.pojo.Admin;
import com.school.identification_of_rice.pojo.AdminExample;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminService {
    /**
     *
     * @param example
     * @return
     */
    public List<Admin> selectByExample(AdminExample example);

    public int updateByPrimaryKeySelective(Admin record);
}
