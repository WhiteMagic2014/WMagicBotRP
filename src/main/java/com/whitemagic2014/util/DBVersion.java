package com.whitemagic2014.util;

import com.whitemagic2014.WMagicBotRPKt;
import com.whitemagic2014.mapper.BotDBDao;
import com.whitemagic2014.pojo.BotDB;
import com.whitemagic2014.pojo.DBVersionTable;
import com.whitemagic2014.pojo.Version;
import net.mamoe.mirai.utils.MiraiLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 数据库更新
 * @author: magic chen
 * @date: 2020/8/20 17:34
 **/
public class DBVersion {

    private static final MiraiLogger logger = WMagicBotRPKt.INSTANCE.getLogger();

    // 内部静态类构造单例
    private DBVersion() {
    }
    private static class Lazy {
        private static final DBVersion instsance = new DBVersion();
    }
    public static final DBVersion getInstance() {
        return DBVersion.Lazy.instsance;
    }
    // end



    public void checkUpdateDB() {
        List<DBVersionTable> dbVersionTable = tempDbVersion();
        logger.info("开始校验数据版本!");
        if (dbVersionTable.isEmpty()) {
            System.out.println("获取最新版本失败!");
        } else {
            BotDBDao dbDao = MyBatisUtil.getSqlSession().getMapper(BotDBDao.class);

            BotDB db = dbDao.DBVersion();
            Version ver = new Version(db.getVersion());
            Version latestVer = dbVersionTable.get(dbVersionTable.size() - 1).getVer();
            logger.info("db当前版本:" + ver + ",最新版本:" + latestVer);
            if (ver.compareTo(latestVer) < 0) {
                logger.info("开始更新!");
                for (DBVersionTable dbver : dbVersionTable) {
                    if (ver.compareTo(dbver.getVer()) < 0) {
                        for (String sql : dbver.getSqls()) {
                            dbDao.runDDLSql(sql);
                        }
                    }
                }
                db.setVersion(latestVer.toString());
                dbDao.updateDBVersion(db);
                logger.info("更新完毕!");
            }
        }
    }

    /**
     * 理论上应该有一个数据服务/数据文件 获取版本 现在就简陋一点吧。。
     * @return
     */
    private List<DBVersionTable> tempDbVersion(){
        List<DBVersionTable> result = new ArrayList<>();


        DBVersionTable v0_0_2 = new DBVersionTable();
        v0_0_2.setVer(new Version("0.0.2"));
        List<String> sql_0_0_2 = new ArrayList<>();

        sql_0_0_2.add("DROP TABLE IF EXISTS user_plan");
        sql_0_0_2.add("CREATE TABLE \"user_plan\" (\n" +
                "  \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                "  \"uid\" text(20),\n" +
                "  \"itemId\" integer,\n" +
                "  \"itemName\" text(64),\n" +
                "  \"nowNum\" integer,\n" +
                "  \"planNum\" integer\n" +
                ");");
        v0_0_2.setSqls(sql_0_0_2);
        result.add(v0_0_2);

        DBVersionTable v0_0_3 = new DBVersionTable();
        v0_0_3.setVer(new Version("0.0.3"));
        List<String> sql_0_0_3 = new ArrayList<>();
        sql_0_0_3.add("DROP TABLE IF EXISTS can_eat");
        sql_0_0_3.add("CREATE TABLE \"can_eat\" (\n" +
                "  \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                "  \"itemName\" text(64),\n" +
                "  \"can\" integer\n" +
                ");");
        v0_0_3.setSqls(sql_0_0_3);
        result.add(v0_0_3);


        DBVersionTable v0_0_4 = new DBVersionTable();
        v0_0_4.setVer(new Version("0.0.4"));
        List<String> sql_0_0_4 = new ArrayList<>();
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_guild");
        sql_0_0_4.add("CREATE TABLE \"pcr_guild\" (\n" +
                " \"gid\" INTEGER PRIMARY KEY,\n" +
                " \"gname\" TEXT(64)\n" +
                ");");
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_user");
        sql_0_0_4.add("CREATE TABLE \"pcr_user\" (\n" +
                " \"uid\" INTEGER,\n" +
                " \"uname\" TEXT(64),\n" +
                " \"role\" TEXT(8),\n" +
                " \"gid\" INTEGER,\n" +
                " \"sl\" INTEGER\n"+
                ");");
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_boss");
        sql_0_0_4.add("CREATE TABLE \"pcr_boss\" (\n" +
                " \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                " \"gid\" INTEGER,\n" +
                " \"cycle\" INTEGER,\n" +
                " \"num\" INTEGER,\n" +
                " \"hp\" INTEGER,\n" +
                " \"hpnow\" INTEGER,\n" +
                " \"active\" INTEGER\n" +
                ");");
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_battle");
        sql_0_0_4.add("CREATE TABLE \"pcr_battle\" (\n" +
                " \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                " \"bossid\" INTEGER,\n" +
                " \"uid\" INTEGER,\n" +
                " \"gid\" INTEGER,\n" +
                " \"uname\" TEXT(64),\n" +
                " \"damage\" INTEGER,\n" +
                " \"killboss\" INTEGER,\n" +
                " \"type\" TEXT(8),\n" +
                " \"time\" TEXT(32)\n" +
                ");");

        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_notice");
        sql_0_0_4.add("CREATE TABLE \"pcr_notice\" (\n" +
                " \"gid\" INTEGER,\n" +
                " \"uid\" INTEGER,\n" +
                " \"bossNum\" INTEGER,\n" +
                " \"type\" TEXT(16)\n" +
                ");");

        v0_0_4.setSqls(sql_0_0_4);
        result.add(v0_0_4);


        DBVersionTable v1_0_0 = new DBVersionTable();
        v1_0_0.setVer(new Version("1.0.0"));
        v1_0_0.setSqls(new ArrayList<>());
        result.add(v1_0_0);


        // 货币系统
        DBVersionTable v1_1_0 = new DBVersionTable();
        v1_1_0.setVer(new Version("1.1.0"));
        List<String> sql_1_1_0 = new ArrayList<>();
        // 账户表
        sql_1_1_0.add("DROP TABLE IF EXISTS user_coin");
        sql_1_1_0.add("CREATE TABLE \"user_coin\" (\n" +
                " \"uid\" INTEGER PRIMARY KEY,\n" +
                " \"magicCoin\" INTEGER,\n" +
                " \"time\" TEXT(32),\n" +
                " \"available\" INTEGER\n" +
                ");");
        // 货币变更记录表
        sql_1_1_0.add("DROP TABLE IF EXISTS coin_log");
        sql_1_1_0.add("CREATE TABLE \"coin_log\" (\n" +
                " \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                " \"uid\" INTEGER,\n" +
                " \"gid\" INTEGER,\n" +
                " \"type\" TEXT(16),\n" +
                " \"amount\" INTEGER,\n" +
                " \"before\" INTEGER,\n" +
                " \"after\" INTEGER,\n" +
                " \"remark\" TEXT(511),\n" +
                " \"time\" TEXT(32),\n" +
                " \"field1\" TEXT(255),\n" +
                " \"field2\" TEXT(255),\n" +
                " \"field3\" TEXT(255)\n" +
                ");");
        v1_1_0.setSqls(sql_1_1_0);
        result.add(v1_1_0);

        return result;
    }



}
