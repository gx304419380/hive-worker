package com.fly.spark.hiveworker.controller;

import com.fly.spark.hiveworker.entity.ResponseDto;
import com.fly.spark.hiveworker.service.SparkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author XXX
 * @since 2018-04-21
 */
@RestController
@RequestMapping("/setting")
@Api("设置 API")
public class SettingController {

    private static final Logger logger = Logger.getLogger(SettingController.class);
    @Autowired
    private SparkService sparkService;

    @ApiOperation("刷新spark session")
    @PostMapping("/refresh")
    public ResponseDto refreshSpark() {
        try {
            sparkService.refreshSparkSession();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return new ResponseDto("error", e.getMessage());
        }
        return new ResponseDto("success", null);
    }
}
