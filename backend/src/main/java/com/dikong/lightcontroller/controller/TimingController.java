package com.dikong.lightcontroller.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.TimingService;
import com.dikong.lightcontroller.utils.ValidateLogUtil;
import com.dikong.lightcontroller.vo.TimeOrdinaryNodeAdd;
import com.dikong.lightcontroller.vo.TimeSpecifiedNodeAdd;
import com.dikong.lightcontroller.vo.TimingListSearch;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月23日下午4:08
 * @see
 *      </P>
 */
@RestController
public class TimingController {

    private static final Logger LOG = LoggerFactory.getLogger(TimingController.class);

    @Autowired
    private TimingService timingService;

    /**
     * 列出所有的时序节点内容
     * 
     * @param timingListSearch 节点类型，普通节点和指定节点
     * @return
     */
    @PostMapping(path = "/timing/list/nodetype")
    public ReturnInfo list(@RequestBody TimingListSearch timingListSearch) {
        if (null == timingListSearch.getNodeType()
                || (!Timing.ORDINARY_NODE.equals(timingListSearch.getNodeType())
                        && !Timing.SPECIFIED_NODE.equals(timingListSearch.getNodeType()))) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.listNodeType(timingListSearch);
    }

    /**
     * 添加普通 节点
     * @param ordinaryNodeAdd
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/timing/add/ordinary/node")
    public ReturnInfo addOrdinaryNode(@RequestBody @Valid TimeOrdinaryNodeAdd ordinaryNodeAdd,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.addOrdinaryNode(ordinaryNodeAdd);
    }

    /**
     * 添加指定节点
     * @param timeSpecifiedNodeAdd
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/timing/add/specified/node")
    public ReturnInfo addSpecifiedNode(
            @RequestBody @Valid TimeSpecifiedNodeAdd timeSpecifiedNodeAdd,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (null == bindingResult.getFieldError("weekList")) {
                return ValidateLogUtil.paramError(bindingResult, LOG);
            }
        }
        return timingService.addSpecifiedNode(timeSpecifiedNodeAdd);
    }

    /**
     * 添加节假日节点
     * @param holidayTimes
     * @return
     */
    @PostMapping(path = "/timing/add/holiday/node")
    public ReturnInfo addHolidayNode(@RequestBody String[] holidayTimes) {
        if (null == holidayTimes || holidayTimes.length == 0){
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.addHolidayNode(holidayTimes);
    }

    /**
     * 时序功能查看
     * @param viewTime
     * @return
     */
    @GetMapping(path = "/timing/node/view/{viewTime}")
    public ReturnInfo view(@PathVariable("viewTime") String viewTime) throws ParseException {
        if (null == viewTime){
            return  ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.timingView(viewTime);
    }


    /**
     * 删除指定节点和普通节点节点
     * 
     * @param id
     * @return
     */
    @DeleteMapping(path = "/timing/del/{id}")
    public ReturnInfo delNode(@PathVariable("id") Long id) {
        if (null == id || id == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.deleteNode(id);
    }
}
