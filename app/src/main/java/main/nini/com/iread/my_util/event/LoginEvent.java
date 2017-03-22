package main.nini.com.iread.my_util.event;

import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class LoginEvent {
    private _User user;

    public LoginEvent(_User user) {
        this.user = user;
    }

    public LoginEvent() {
    }

    public _User getUser() {
        return user;
    }

    public void setUser(_User user) {
        this.user = user;
    }
}
