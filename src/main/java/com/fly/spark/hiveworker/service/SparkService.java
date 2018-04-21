package com.fly.spark.hiveworker.service;

import org.apache.carbondata.core.constants.CarbonCommonConstants;
import org.apache.carbondata.core.util.CarbonProperties;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.spark.sql.CarbonSession;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author XXX
 * @since 2018-04-18
 */
@Service
public class SparkService {

    private SparkSession spark;

    @Value("${kerberos.conf.path}")
    private String kerberosConf;
    @Value("${kerberos.principal}")
    private String principal;
    @Value("${kerberos.keytab.path}")
    private String keytab;
    @Value("${hadoop.hdfs.url}")
    private String hdfsURL;

    @PostConstruct
    public void init() throws IOException {
        System.setProperty("java.security.krb5.conf", kerberosConf);
        System.setProperty("user.name", "hdfs");

        UserGroupInformation.loginUserFromKeytab(principal, keytab);
        System.out.println(UserGroupInformation.getLoginUser());

        CarbonProperties.getInstance().addProperty(CarbonCommonConstants.LOCK_TYPE, "HDFSLOCK");
        spark = CarbonSession.CarbonBuilder(
                SparkSession.builder()
                        .master("yarn")
                        .appName("CarbonData")
                        //如果要长期运行，需要添加以下参数
//                                .config("spark.yarn.principal", principal)
//                                .config("spark.yarn.keytab", keytab)
//                                .config("spark.yarn.kerberos.relogin.period", "1h")
                        .config("spark.yarn.archive", hdfsURL + "/user/spark/jars")
                        .config("spark.executor.memory", "2g")
                        .config("spark.executor.cores", 2)
        ).getOrCreateCarbonSession(hdfsURL + "/opt");

    }

    public List<String> executeSQL(String sql) {
        List<String> jsons = spark.sql(sql)
                .toJSON()
                .collectAsList();
        return jsons;
    }

    public void refreshSparkSession() throws IOException {
        if (Objects.isNull(spark)) {
            init();
        } else {
            spark.close();
            init();
        }
    }

}
