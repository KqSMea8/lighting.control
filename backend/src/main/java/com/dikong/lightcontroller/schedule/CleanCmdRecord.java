package com.dikong.lightcontroller.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;

import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.dao.CmdRecordDao;
import com.dikong.lightcontroller.entity.CmdRecord;

/**
 * @author huangwenjun
 * @Date 2018年3月14日
 */
@Component
public class CleanCmdRecord {

    private static final Logger LOG = LoggerFactory.getLogger(CleanCmdRecord.class);
    @Autowired
    private CmdRecordDao cmdRecordDao;

    // @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(fixedRate = 1000 * 5 * 1)
    public void cleanCmdRecord() {
        String cleandays =
                LocalDateTime.now().minusDays(Constant.CMD.CLEAN_DAYS)
                        .format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
        LOG.info("清理命令记录日志：" + cleandays);
        Example example = new Example(CmdRecord.class);
        example.createCriteria().andLessThanOrEqualTo("createTime", cleandays);
        cmdRecordDao.deleteByExample(example);
    }
}
