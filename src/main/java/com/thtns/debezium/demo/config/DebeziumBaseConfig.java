package com.thtns.debezium.demo.config;

import cn.hutool.core.lang.Assert;

import com.thtns.debezium.demo.helper.DebeziumHelper;
import com.thtns.debezium.demo.properties.DebeziumProperties;
import io.debezium.connector.mysql.MySqlConnector;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author thtns
 * @description
 * @date 2022/4/26
 */
@Configuration
@Slf4j
public class DebeziumBaseConfig {

    private DebeziumHelper debeziumHelper;
    private DebeziumProperties debeziumProperties;

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @Bean
    io.debezium.config.Configuration debeziumConfig() {

        return io.debezium.config.Configuration.create()
                //  连接器的Java类名称
                .with("connector.class", MySqlConnector.class.getName())
                // 偏移量持久化，用来容错 默认值
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                // 要存储偏移量的文件路径 默认/tmp/offsets.dat  如果路径配置不正确可能导致无法存储偏移量 可能会导致重复消费变更
                // 如果连接器重新启动，它将使用最后记录的偏移量来知道它应该恢复读取源信息中的哪个位置。
                .with("offset.storage.file.filename", debeziumProperties.getOffsetFileName())
                // 尝试提交偏移量的时间间隔。默认值为 1分钟
                .with("offset.flush.interval.ms", debeziumProperties.getOffsetTime())
                // 监听连接器实例的 唯一名称
                .with("name", debeziumProperties.getInstanceName())
                // MySQL 实例的地址
                .with("database.hostname", debeziumProperties.getIp())
                // MySQL 实例的端口号
                .with("database.port", debeziumProperties.getPort())
                // MySQL 用户的名称
                .with("database.user", username)
                // MySQL 用户的密码
                .with("database.password", password)
                // 要从中捕获更改的数据库的名称
                .with("database.include.list", debeziumProperties.getIncludeDb())
                // 是否包含数据库表结构层面的变更 默认值true
                .with("include.schema.changes", "false")
                // Debezium 应捕获其更改的所有表的列表
                .with("table.include.list", debeziumProperties.getIncludeTable())
                // MySQL 实例/集群的逻辑名称，形成命名空间，用于连接器写入的所有 Kafka 主题的名称、Kafka Connect 架构名称以及 Avro 转换器时对应的 Avro 架构的命名空间用来
                .with("database.server.name", debeziumProperties.getLogicName())
                // 负责数据库历史变更记录持久化Java 类的名称
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                // 历史变更记录存储位置，存储DDL
                .with("database.history.file.filename", debeziumProperties.getHistoryFileName()).build();
    }

    @Bean
    MySQLTimelyExecutor mysqlTimelyExecutor(io.debezium.config.Configuration configuration) {
        MySQLTimelyExecutor mySQLTimelyExecutor = new MySQLTimelyExecutor();
        DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine = DebeziumEngine.create(
                        ChangeEventFormat.of(Connect.class))
                .using(configuration.asProperties())
                .notifying(debeziumHelper::handlePayload)
                .build();
        mySQLTimelyExecutor.setDebeziumEngine(debeziumEngine);
        return mySQLTimelyExecutor;
    }

    @Autowired
    public void setDebeziumHelper(DebeziumHelper debeziumHelper) {
        this.debeziumHelper = debeziumHelper;
    }

    @Autowired
    public void setDebeziumProperties(DebeziumProperties debeziumProperties) {
        this.debeziumProperties = debeziumProperties;
    }

    @Data
    @Slf4j
    public static class MySQLTimelyExecutor implements InitializingBean, SmartLifecycle {
        private ThreadPoolTaskExecutor taskExecutor;
        private DebeziumEngine<?> debeziumEngine;

        @Override
        public void start() {
            log.info("线程池开始执行 debeziumEngine 实时监听任务!");
            taskExecutor.execute(debeziumEngine);
        }

        @SneakyThrows
        @Override
        public void stop() {
            log.info("debeziumEngine 监听实例关闭!");
            debeziumEngine.close();
            Thread.sleep(2000);
            log.info("线程池关闭!");
            taskExecutor.shutdown();
        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public void afterPropertiesSet() {
            Assert.notNull(debeziumEngine, "debeziumEngine 不能为空!");
        }

        @Autowired
        public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
            this.taskExecutor = taskExecutor;
        }

    }

}
