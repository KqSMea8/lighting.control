package com.dikong.lightcontroller.utils;

import static feign.Util.decodeOrDefault;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.Request;
import feign.Response;
import feign.Util;
import strman.Strman;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月01日下午4:54
 * @see </P>
 */
public class Slf4jLogCollection extends feign.Logger{
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    private final Logger logger;
    private boolean enable;

    public Slf4jLogCollection() {
        this(Slf4jLogCollection.class);
    }

    public Slf4jLogCollection(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz));
    }

    public Slf4jLogCollection(String name) {
        this(LoggerFactory.getLogger(name));
    }

    Slf4jLogCollection(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {

    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @SuppressWarnings("all")
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (enable) {
            String uniqueId = UUID.randomUUID().toString();
            String[] strings = uniqueId.split("-");
            uniqueId = "";
            for (String string : strings) {
                if (null == uniqueId || uniqueId.length() == 0) {
                    uniqueId = string;
                    continue;
                }
                uniqueId = Strman.append(uniqueId, "_", string);
            }
            threadLocal.set(uniqueId);
            String prt = String.format(methodTag(configKey) + "---> %s %s HTTP/1.1",
                    request.method(), request.url());
            logger.info(Strman.append("[", uniqueId, "]", prt));
            if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {
                int bodyLength = 0;
                if (request.body() != null) {
                    bodyLength = request.body().length;
                    if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                        String bodyText = request.charset() != null
                                                  ? new String(request.body(), request.charset())
                                                  : null;
                        prt = String.format(methodTag(configKey) + "%s",
                                bodyText != null ? bodyText : "Binary data");
                        logger.info(Strman.append("[", uniqueId, "]", prt));
                    }
                }
            }
        }
    }

    @SuppressWarnings("all")
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
                                                     long elapsedTime) throws IOException {
        if (enable) {
            String uniqueId = threadLocal.get();
            String reason = response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ?
                                    " " + response.reason() :
                                    "";
            int status = response.status();
            String prt = "";
            if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

                int bodyLength = 0;
                if (response.body() != null && !(status == 204 || status == 205)) {
                    byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                    bodyLength = bodyData.length;
                    if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                        prt = String.format(methodTag(configKey) + "%s",
                                decodeOrDefault(bodyData, UTF_8, "Binary data"));
                        logger.info(Strman.append("[", uniqueId, "]", prt));
                    }
                    return response.toBuilder().body(bodyData).build();
                } else {
                    prt = String.format(methodTag(configKey) + "<--- END HTTP (%s-byte body)",
                            bodyLength);
                    logger.info(Strman.append("[", uniqueId, "]", prt));
                }
            }
        }
        return response;
    }
}
