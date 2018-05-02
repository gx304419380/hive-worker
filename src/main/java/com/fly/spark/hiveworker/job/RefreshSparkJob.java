package com.fly.spark.hiveworker.job;

import com.fly.spark.hiveworker.service.SparkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author XXX
 * @since 2018-05-02
 */
@Component
public class RefreshSparkJob {
    private static final Logger logger = Logger.getLogger(RefreshSparkJob.class);

    @Autowired
    private SparkService sparkService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void doJob() {
        try {
            sparkService.refreshSparkSession();
        } catch (IOException e) {
            logger.error("***********************************更新spark失败！***********************************");
            logger.error(e.getMessage());
        }
    }

}
