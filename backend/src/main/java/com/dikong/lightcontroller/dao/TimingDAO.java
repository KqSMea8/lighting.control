package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.Timing;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月23日下午4:09
 * @see
 *      </P>
 */
@Repository
public interface TimingDAO extends Mapper<Timing> {

    @Update({"update timing set is_delete=#{isDelete} where id=#{id}"})
    int updateDeleteById(@Param("id") Long id,@Param("isDelete")Byte isDelete);
}
