/*
package com.yonyou.mde.web.utils;

import io.micrometer.core.instrument.Metrics;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PrometheusUtil {

    private static final String HTTP_REQUEST_COUNTER_ALL = "http_request_counter_all";
    private static final String HTTP_REQUEST_COUNTER_ERROR = "http_request_counter_error";
    private static final String HTTP_RESPONSE_RT_MS = "http_response_rt_ms";
    private static final String API = "api";
    private static final String RC = "rc";

    private PrometheusUtil() {

    }

    */
/**
     * 收集api total 埋点数据
     *
     * @param op api名
     *//*

    public static void total(String op) {
        try {
            Metrics.counter(HTTP_REQUEST_COUNTER_ALL, API, op)
                    .increment();
        } catch (Exception e) {
            log.info("PrometheusMetrics API QPS ERROR ,caused by {}", e.getMessage());
        }
    }

    */
/**
     * 收集api error 埋点数据
     *
     * @param op api名
     *//*

    public static void error(String op) {
        try {

            Metrics.counter(HTTP_REQUEST_COUNTER_ERROR, API, op)
                    .increment();
        } catch (Exception e) {
            log.info("PrometheusMetrics API ERROR COUNTER ,caused by {}", e.getMessage());
        }
    }

    */
/**
     * 收集接口响应时间
     *
     * @param op 接口标识
     * @param rc 返回码
     * @param rt 返回时间
     *//*

    public static void report(String op, String rc, double rt) {
        Metrics.summary(HTTP_RESPONSE_RT_MS, API, op, RC, rc).record(rt);
    }
}
*/
