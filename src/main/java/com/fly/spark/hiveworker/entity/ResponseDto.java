package com.fly.spark.hiveworker.entity;

/**
 * @author XXX
 * @since 2018-04-20
 */
public class ResponseDto {

    private String status;
    private Object data;

    public ResponseDto(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ResponseDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
