package identification_of_rice.pojo;
public class Admin {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.mid
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    private Integer mid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.username
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.password
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.truename
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    private String truename;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.phone
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    private String phone;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.mid
     *
     * @return the value of admin.mid
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.mid
     *
     * @param mid the value for admin.mid
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.username
     *
     * @return the value of admin.username
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.username
     *
     * @param username the value for admin.username
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.password
     *
     * @return the value of admin.password
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.password
     *
     * @param password the value for admin.password
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.truename
     *
     * @return the value of admin.truename
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public String getTruename() {
        return truename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.truename
     *
     * @param truename the value for admin.truename
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.phone
     *
     * @return the value of admin.phone
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.phone
     *
     * @param phone the value for admin.phone
     *
     * @mbggenerated Sat Nov 05 20:30:53 CST 2022
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Admin(Integer mid, String username, String password, String truename, String phone) {
        this.mid = mid;
        this.username = username;
        this.password = password;
        this.truename = truename;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "mid=" + mid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", truename='" + truename + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}