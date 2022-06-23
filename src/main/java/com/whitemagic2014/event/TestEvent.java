package com.whitemagic2014.event;

import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

public class TestEvent extends SimpleListenerHost {

    @NotNull
    @EventHandler(priority = EventPriority.NORMAL)
    public ListeningStatus t1(@NotNull GroupMessageEvent event) throws Exception {
        Group group = event.getSubject();
        MiraiLogger miraiLogger = group.getBot().getLogger();
        if (group.getId() == 362656777L) {
            event.getMessage().forEach(
                    it -> {
                        if (it instanceof Image) {
                            //如果消息这一部分是图片
                            miraiLogger.info(Mirai.getInstance().queryImageUrl(group.getBot(), (Image) it));
                        }
                        if (it instanceof PlainText) {
                            //如果消息这一部分是纯文本
                            String content = ((PlainText) it).getContent();
                            miraiLogger.info(content);
                        }
                    }
            );
        }
        return ListeningStatus.LISTENING;
    }

}
