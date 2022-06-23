package com.whitemagic2014.util;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



/**
 * @Description: 无spring环境，非mybatis-config.xml配置文件 mybatis初始化
 * @author: magic chen
 * @date: 2022/6/23 15:08
 *
 * xml配置文件的方式需要指定链接url，但是sqlite的存储文件我又希望跟着jar走,这时候xml配置就没办法满足了
 * 所以按照config配置文件 来手动构建Configuration
 * 其中mapper解析方式经过尝试,package与class ，xml与interface始终无法绑定成功
 * resource方式解析可以成功，可惜resource解析方式仅仅在 xml配置时比较友善,Configuration中addMappers 并不支持直接进行resource配置
 * 阅读源码后 按照源码的方式手动进行了注册解析
 * 最后一个麻烦的点在于,运行环境的不同,取得resources下资源文件列表的方式不同
 * 在开发环境,算是文件系统,可以使用 .getContextClassLoader().getResourceAsStream 来获取资源文件
 * 但是打包成jar之后,资源文件不再属于文件系统，需要使用JarFile来扫描jar包中的文件
 **/
public class MyBatisUtil {

    private static SqlSessionFactory factory = null;

    public static SqlSession getSqlSession() {
        return factory.openSession();
    }

    static {
        try {
            // datasource
            SQLiteConnectionPoolDataSource pool = new SQLiteConnectionPoolDataSource();
            pool.setUrl("jdbc:sqlite:" + Path.getPath() + DBInitHelper.dbFileName);
            // environment
            Environment environment = new Environment.Builder("development")
                    .dataSource(pool)
                    .transactionFactory(new JdbcTransactionFactory())
                    .build();
            // config
            Configuration config = new Configuration();
            config.setEnvironment(environment);
            // 注册mapper
            magicMapperLoader(config);

            // build factory
            factory = new SqlSessionFactoryBuilder().build(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 注册xml mapper
    private static void magicMapperLoader(Configuration configuration) {
        List<String> xmlFiles;

        // 根据不同运行环境来扫描xml资源
        String protocol = MyBatisUtil.class.getResource("").getProtocol();
        if (Objects.equals(protocol, "jar")) {
            xmlFiles = getJarXmlRes();
        } else {
            xmlFiles = getFileXmlRes();
        }

        for (String xmlPath : xmlFiles) {
            ErrorContext.instance().resource(xmlPath);
            try (InputStream inputStream = Resources.getResourceAsStream(xmlPath)) {
                XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, xmlPath, configuration.getSqlFragments());
                mapperParser.parse();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 加载所有 resources 中 mapper下的文件
    // jar包运行时无法使用file来遍历 使用这个
    public static List<String> getJarXmlRes() {
        List<String> list = new ArrayList<>();
        String jar = MyBatisUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try (JarFile jf = new JarFile(jar);) {
            Enumeration<JarEntry> es = jf.entries();
            while (es.hasMoreElements()) {
                String resName = es.nextElement().getName();
                if (resName.startsWith("mapper") && resName.endsWith("xml")) {
                    list.add(resName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    // idea开发时使用 ,获取resources/mapper下所有xml文件
    public static List<String> getFileXmlRes() {
        String path = "mapper";
        List<String> filenames = new ArrayList<>();
        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
        ) {
            String resource;
            while ((resource = br.readLine()) != null) {
                if (resource.endsWith("xml")) {
                    filenames.add(path + "/" + resource);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }

    private static InputStream getResourceAsStream(String resource) {
        final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        return in == null ? MyBatisUtil.class.getResourceAsStream(resource) : in;
    }

}
