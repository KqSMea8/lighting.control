package com.dikong.lightcontroller.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import strman.Strman;

/**
 * <p>
 * Description http请求工具
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2017年10月23日下午5:38
 * @see
 *      </P>
 */
public class OkhttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(OkhttpUtils.class);

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType FORM = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();

    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            LOG.info("okhttp_response:", response.toString());
            return null;
        }
        return response.body().string();
    }

    public static String get(String url,Map<String,Object> hreads,Map<String,Object> params) throws IOException {
        final Request.Builder builder = new Request.Builder();

        if(Objects.nonNull(hreads)){
            //获取map集合中的所有键的Set集合
            Set<String> headKeySet = hreads.keySet();
            //有了Set集合就可以获取其迭代器，取值
            Iterator<String> headsIt = headKeySet.iterator();
            while (headsIt.hasNext()) {
                String key = headsIt.next();
                String value = hreads.get(key).toString();
                builder.header(key,value);
            }
        }

        if(Objects.nonNull(params)){
            //获取map集合中的所有键的Set集合
            Set<String> paramsKeySet = params.keySet();
            //有了Set集合就可以获取其迭代器，取值
            Iterator<String> paramsIt = paramsKeySet.iterator();

            url = Strman.append(url,"?");
            while (paramsIt.hasNext()) {
                String key = paramsIt.next();
                String value = params.get(key).toString();
                url = Strman.append(url,key,"=",value,"&");
            }
            url = url.substring(0,url.length() - 1);
            builder.url(url);
        }

        Response response = client.newCall(builder.build()).execute();
        if (response.code() != 200) {
            LOG.info("okhttp_response:", response.toString());
            return null;
        }
        return response.body().string();
    }

    public static String postJson(String url, String param) throws IOException {
        RequestBody body = RequestBody.create(JSON, param);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            return response.body().string();
        }
        return null;
    }

    public static String postFrom(String url, Map<String, String> params,
            Map<String, String> headers) throws IOException {
        FormBody.Builder formBody = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> m : params.entrySet()) {
                if (m.getKey() == null || m.getValue() == null) {
                    continue;
                }
                // System.out.println(m.getKey()+"\t"+m.getValue());
                if(StringUtils.isNotBlank(m.getKey()) && StringUtils.isNotBlank(m.getValue())){
                    formBody.add(m.getKey(),m.getValue());
                }
            }
        }
        RequestBody body = formBody.build();
        Request.Builder builder = new Request.Builder().url(url).post(body);
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> m : headers.entrySet())
                builder.addHeader(m.getKey(), m.getValue());
        }
        Response response = client.newCall(builder.build()).execute();
        if (response.code() == 200) {
            return response.body().string();
        }
        return null;
    }
}
