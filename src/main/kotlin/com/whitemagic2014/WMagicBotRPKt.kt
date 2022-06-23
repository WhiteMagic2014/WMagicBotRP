package com.whitemagic2014;

import com.whitemagic2014.command.CheckDb
import com.whitemagic2014.command.TestMiraiCommand
import com.whitemagic2014.event.TestEvent
import com.whitemagic2014.util.DBInitHelper
import com.whitemagic2014.util.DBVersion
import com.whitemagic2014.util.MyBatisUtil
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.extension.PluginComponentStorage
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.utils.info


object WMagicBotRPKt : KotlinPlugin(
    JvmPluginDescription.loadFromResource()
) {

    // 初始化配置
    override fun PluginComponentStorage.onLoad() {
        DBInitHelper.getInstance().initDBIfNotExist()
        DBVersion.getInstance().checkUpdateDB()
//        MiraiLogger.setDefaultLoggerCreator { identity -> LoggerFactory.getLogger(identity).asMiraiLogger() }
        logger.info { "Plugin onLoad" }
    }

    // 注册指令
    override fun onEnable() {
        logger.info { "Plugin onEnable" }

        TestMiraiCommand.register()
        CheckDb.register()


        //配置文件目录 "${dataFolder.absolutePath}/"
        val eventChannel = GlobalEventChannel.parentScope(this)

        val testEvent = TestEvent()
        eventChannel.registerListenerHost(testEvent)

//        eventChannel.subscribeAlways<GroupMessageEvent> {
//            val handler = GroupMessageEventHandler()
//            handler.handle(this)
//            //群消息
//            // 仅在测试群有效
//            if (group.id != 1106295044L) {
//                return@subscribeAlways
//            }
//            //复读示例
//            if (message.contentToString().startsWith("复读")) {
//                group.sendMessage(message.contentToString().replace("复读", ""))
//            }
//            if (message.contentToString() == "hi") {
//                //群内发送
//                group.sendMessage("hi")
//                //向发送者私聊发送消息
//                // sender.sendMessage("hi")
//                //不继续处理
//                return@subscribeAlways
//            }
//            //分类示例
//            message.forEach {
//                //循环每个元素在消息里
//                if (it is Image) {
//                    //如果消息这一部分是图片
//                    val url = it.queryUrl()
//                    group.sendMessage("图片，下载地址$url")
//                }
//                if (it is PlainText) {
//                    //如果消息这一部分是纯文本
//                    group.sendMessage("纯文本，内容:${it.content}")
//                }
//            }
//        }
//        eventChannel.subscribeAlways<FriendMessageEvent> {
//            //好友信息
//            sender.sendMessage("hi")
//        }
//        eventChannel.subscribeAlways<NewFriendRequestEvent> {
//            //自动同意好友申请
//            accept()
//        }
//        eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent> {
//            //自动同意加群申请
//            accept()
//        }
    }

    // 注销所有指令
    override fun onDisable() {
        logger.info { "Plugin onDisable" }
    }

}