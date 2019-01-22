package com.smartcold.manage.cold.config;

import okhttp3.OkHttpClient.Builder;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/**
 * Copyright (C) SmartCold 版权所有
 *
 * @author MaQiang34
 * @Description:动态配置influxdb 数据源
 * 支持多数据源配置
 * @createDate:2018-11-09 16:20
 * @version:V1.0
 */
@Configuration
public class InfluxDBConfig implements Serializable {
    @Value("${influxdb.username}")
    private String username;
    @Value("${influxdb.password}")
    private String password;
    @Value("${influxdb.database}")
    public String database;
    public static String DATABASE;
    @Value("${influxdb.blocksize}")
    public Integer blocksize;
    @Value("${influxdb.url}")
    private String url ;
    @Value("${influxdb.connect-timeout}")
    private Long connecttimeout ;
    @Value("${influxdb.read-timeout}")
    private Long readtimeout ;
    @Value("${influxdb.write-timeout}")
    private Long writetimeout ;

    protected final Logger logger = LoggerFactory.getLogger(InfluxDBConfig.class);

    @Bean
   public Builder getBuilder(){
      return new Builder().connectTimeout(this.connecttimeout, TimeUnit.SECONDS).readTimeout(this.readtimeout, TimeUnit.SECONDS).writeTimeout(this.readtimeout,TimeUnit.SECONDS);
   }

    @Bean
    public InfluxDB getConnection() {
            DATABASE=this.database;
            InfluxDB influxDB = InfluxDBFactory.connect(this.url, this.username, this.password,this.getBuilder()).setDatabase(this.database).enableBatch().enableGzip();
            logger.info("influxdeb  启用批量插入:{},启用压缩：{}",influxDB.isBatchEnabled(),influxDB.isGzipEnabled());
            return influxDB;
    }

}
