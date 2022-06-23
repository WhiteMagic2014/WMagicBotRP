package com.whitemagic2014.command

import com.whitemagic2014.WMagicBotRPKt
import com.whitemagic2014.mapper.BotDBMapper
import com.whitemagic2014.util.MyBatisUtil
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.message.data.MessageChain

object CheckDb : RawCommand(
    WMagicBotRPKt, "db",
    description = "check db"
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) {


        val dbDao = MyBatisUtil.getSqlSession().getMapper(BotDBMapper::class.java)
        println(dbDao.DBVersion())

    }
}