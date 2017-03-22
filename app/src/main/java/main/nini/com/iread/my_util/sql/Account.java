package main.nini.com.iread.my_util.sql;

/**
 * Created by zyf on 2017/3/12.
 */


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName ="accounts")//设置表名
public class Account {

    @DatabaseField(id = true)//设置name字段为主键
    private String name;
    @DatabaseField
    private String password;
    @DatabaseField
    private Integer age;

    public Account(){
        // ORMLite需要一个无参数的构造函数
    }

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
