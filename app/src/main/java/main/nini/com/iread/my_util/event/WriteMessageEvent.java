package main.nini.com.iread.my_util.event;

import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class WriteMessageEvent {
    private _User user;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WriteMessageEvent(_User user) {
        this.user = user;
    }

    public _User getUser() {
        return user;
    }

    public void setUser(_User user) {
        this.user = user;
    }
}
