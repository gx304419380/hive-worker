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

import java.util.Arrays;
import java.util.List;

/**
 * @author XXX
 * @since 2018-04-18
 */
@RestController
@RequestMapping("/sql")
@Api("SQL API")
public class SQLController {

    private static final Logger logger = Logger.getLogger(SQLController.class);

    @Autowired
    private SparkService sparkService;

    @ApiOperation("执行sql语句")
    @PostMapping
    public ResponseDto sql(String sql) {
        logger.info("SQL语句为：" + sql);
        List<String> jsons;
        try {
            jsons = sparkService.executeSQL(sql);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseDto("error", Arrays.asList(e.getMessage()));
        }
        return new ResponseDto("success", jsons);
    }

}
