package com.fly.spark.hiveworker;

import org.junit.Test;

import java.io.File;

/**
 * @author XXX
 * @since 2018-04-19
 */
public class KerberosTest {

    @Test
    public void testKeytabPath() {
        File file = new File("file:///D:/hdfs.keytab");
        System.out.println(file.exists());
    }

}
