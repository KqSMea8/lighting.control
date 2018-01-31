package com.dikong.lightcontroller.dto;

import java.util.Set;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月29日下午9:56
 * @see
 *      </P>
 */
public class QuartzJobDto {


    private JobDo jobDO;

    private Set<TriggerDos> triggerDOs;

    public JobDo getJobDO() {
        return jobDO;
    }

    public void setJobDO(JobDo jobDO) {
        this.jobDO = jobDO;
    }

    public Set<TriggerDos> getTriggerDOs() {
        return triggerDOs;
    }

    public void setTriggerDOs(Set<TriggerDos> triggerDOs) {
        this.triggerDOs = triggerDOs;
    }

    public static class JobDo {
        private String description;

        private String group;

        private String name;

        private ExtInfo extInfo;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ExtInfo getExtInfo() {
            return extInfo;
        }

        public void setExtInfo(ExtInfo extInfo) {
            this.extInfo = extInfo;
        }
    }

    public static class TriggerDos {

        private String cronExpression;

        private String description;

        private String group;

        private String name;

        public String getCronExpression() {
            return cronExpression;
        }

        public void setCronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ExtInfo {

        private static final String DEFAULT_TYPE = "http_job";
        private static final String DEFAULT_METHOD = "post";

        private String type;

        private String method;


        private String url;

        private String jsonParams;

        public ExtInfo() {

        }

        public ExtInfo(String url, String jsonParams) {
            this.url = url;
            this.jsonParams = jsonParams;
            this.method = DEFAULT_METHOD;
            this.type = DEFAULT_TYPE;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getJsonParams() {
            return jsonParams;
        }

        public void setJsonParams(String jsonParams) {
            this.jsonParams = jsonParams;
        }
    }

}
