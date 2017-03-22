package main.nini.com.iread.my_util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zyf on 2017/3/12.
 */

public class ExecutorSingle {
    private static ExecutorSingle instance;
    private static ExecutorService pool;

    private ExecutorSingle() {
        pool = Executors.newCachedThreadPool();
    }

    public static ExecutorSingle getInstance() {
        if (instance == null) {

            synchronized (ExecutorSingle.class) {
                if(instance == null){
                    instance = new ExecutorSingle();
                }
            }
        }

        return instance;
    }

    public void execute(Runnable runnable){
        pool.execute(runnable);
    }

}
