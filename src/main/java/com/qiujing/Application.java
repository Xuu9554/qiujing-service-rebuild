package com.qiujing;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.system.SystemUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, initLoggingPathArgs(args));
    }

    /**
     * 初始化日志路径参数
     *
     * @param args 运行参数
     * @return {@link String[]} 运行参数
     */
    private static String[] initLoggingPathArgs(String[] args) {

        String exportPath = SystemUtil.getOsInfo().isLinux() ? "." + File.separator + "logs"
                // 本地的日志文件夹和你当前的项目同级
                : new File(System.getProperty("user.dir")).getParent() + File.separator + "logs" + File.separator + "qiujing-service-rebuild";

        String loggingCommand = "--logging.dir=" + exportPath;

        if (!ArrayUtil.contains(args, loggingCommand)) {
            return ArrayUtil.append(args, loggingCommand);
        }
        return args;
    }

}
