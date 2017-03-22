package main.nini.com.iread.my_util.event;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class RegisterEvent {
    public String user;
    public String password;

    public RegisterEvent(String user, String password) {
        this.password = password;
        this.user = user;
    }
}
