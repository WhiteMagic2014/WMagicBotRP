package com.whitemagic2014.command

import com.whitemagic2014.WMagicBotRPKt
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.Group

object TestMiraiCommand : SimpleCommand(
    WMagicBotRPKt, "tell", "私聊",
    description = "Tell somebody privately"
) {

    @Handler
    suspend fun CommandSender.handle(target: Group, message: String) {
        target.sendMessage(message)
    }

}
