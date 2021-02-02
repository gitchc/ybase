package com.yonyou.mde.web.service.DataService;

import com.yonyou.mde.Mde;
import com.yonyou.mde.config.KafkaConfig;
import com.yonyou.mde.config.MdeConfiguration;
import com.yonyou.mde.config.ZkConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Description: 初始化MDE配置信息
 * @Author chenghch
 * @Date 2021/1/28 14:23
 */
@Service
public class MDEManager {
    @Value("${single:true}")
    private String type;

    public void initMde() {
        MdeConfiguration configuration = new MdeConfiguration();
        if ("true".equals(type)) {
            configuration.setDistributed(false);//不启用分布式
        } else {
            configuration.setDistributed(true);//启用分布式
        }
        configuration.setWriteBackByBiz(true);//回写库
        configuration.setProcessor(new WriteBackProcesser());//自定义回写数据库方法
        if (configuration.isDistributed()) {
            KafkaConfig kafkaConfig = new KafkaConfig();
            kafkaConfig.setBrokers("10.167.51.26:9092");
            ZkConfig zkConfig = new ZkConfig();
            zkConfig.setHost("10.167.51.26:2181");
            configuration.setKafkaConfig(kafkaConfig);
            configuration.setZkConfig(zkConfig);
            configuration.setModelReplicaNum(1);
            configuration.setServerPort(200);
            configuration.setServerAddress("127.0.0.1");
        }
        Mde.init(configuration);
    }
}
