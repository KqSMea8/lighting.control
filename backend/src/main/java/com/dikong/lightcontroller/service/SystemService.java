package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.SystemConfig;
import com.dikong.lightcontroller.vo.SystemSearch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月24日上午10:58
 * @see </P>
 */
public interface SystemService {

    ReturnInfo addTypeValue(String typeValue);

    ReturnInfo<List<SystemConfig>> listType();

    ReturnInfo add(SystemConfig systemConfig,MultipartFile uploadfile) throws IOException;

    ReturnInfo<SystemConfig> search(Integer valueId);

    ReturnInfo<List<SystemConfig>> list(SystemSearch systemSearch);

    ReturnInfo del(Long id);
}
