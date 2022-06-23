package com.whitemagic2014.util.task;

import com.whitemagic2014.WMagicBotRPKt;
import net.mamoe.mirai.utils.MiraiLogger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: 扩展的 TimerTask
 * @author: magic chen
 * @date: 2020/9/30 15:08
 **/
public abstract class MagicTask extends TimerTask {

    protected static final MiraiLogger logger = WMagicBotRPKt.INSTANCE.getLogger();

    protected static Timer magicTimer = new Timer("magicTimer");

    protected String taskKey;

    protected Task taskTk;


    /**
     * @Name: MagicTask
     * @Description: 在创建时候 在观察中心注册
     * @Param: key
     * @Param: tk
     * @Return: null
     * @Author: magic chen
     * @Date: 2021/1/7 16:10
     **/
    public MagicTask(String key, Task tk) {
        taskKey = key;
        taskTk = tk;
        logger.info("Task [" + key + "] Regist");
        MagicTaskObserver.addTask(key, this);
    }


    @Override
    public boolean cancel() {
        // 在取消任务的时候 从 注册中心 移除 task
        MagicTaskObserver.removeTask(taskKey);
        return super.cancel();
    }


}
