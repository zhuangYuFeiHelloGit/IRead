package main.nini.com.iread.my_util.event;

import main.nini.com.iread.response.Evaluate;

/**
 * Created by zyf on 2017/2/25.
 */

public class DeleteEvaluateEvent {
    private int position;
    private Evaluate evaluate;

    public DeleteEvaluateEvent(int position, Evaluate evaluate) {
        this.position = position;
        this.evaluate = evaluate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Evaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }
}
