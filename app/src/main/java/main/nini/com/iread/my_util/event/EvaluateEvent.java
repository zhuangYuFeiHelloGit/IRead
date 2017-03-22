package main.nini.com.iread.my_util.event;

import main.nini.com.iread.response.Evaluate;

/**
 * Created by zyf on 2017/2/25.
 */

public class EvaluateEvent {
    private Evaluate evaluate;


    public EvaluateEvent(Evaluate evaluate) {
        this.evaluate = evaluate;
    }

    public Evaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }
}
