package com.dikong.lightcontroller.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.TimingService;
import com.dikong.lightcontroller.utils.ValidateLogUtil;
import com.dikong.lightcontroller.vo.BoardList;
import com.dikong.lightcontroller.vo.TimeOrdinaryNodeAdd;
import com.dikong.lightcontroller.vo.TimeSpecifiedNodeAdd;
import com.dikong.lightcontroller.vo.TimingList;
import com.dikong.lightcontroller.vo.TimingListSearch;
import com.dikong.lightcontroller.vo.TimingView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
@Api(value = "TimingController", description = "时序管理")
@RestController
@RequestMapping("/light")
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
    public ReturnInfo<List<TimingList>> list(@RequestBody TimingListSearch timingListSearch) {
        if (null == timingListSearch.getNodeType()
                || (!Timing.ORDINARY_NODE.equals(timingListSearch.getNodeType())
                        && !Timing.SPECIFIED_NODE.equals(timingListSearch.getNodeType()))) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.listNodeType(timingListSearch);
    }

    /**
     * 添加普通 节点
     * 
     * @param ordinaryNodeAdd
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "添加普通 节点")
    @PostMapping(path = "/timing/add/ordinary/node")
    public ReturnInfo addOrdinaryNode(@RequestBody @Valid TimeOrdinaryNodeAdd ordinaryNodeAdd,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidateLogUtil.paramError(bindingResult, LOG);
        }
        return timingService.addOrdinaryNode(ordinaryNodeAdd);
    }

    @ApiOperation(value = "获取修改普通 节点")
    @GetMapping(path = "/timing/ordinary/{id}")
    public ReturnInfo<Timing> getOrdinary(@PathVariable("id") Long id) {
        if (id == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.getTiming(id);
    }

    @ApiOperation(value = "修改时序节点")
    @PutMapping(path = "/timing/update/{id}")
    public ReturnInfo<Boolean> updateTiming(@RequestBody TimeSpecifiedNodeAdd timeSpecifiedNodeAdd,@PathVariable("id")Long id){
        return timingService.updateTiming(timeSpecifiedNodeAdd, id);
    }

    /**
     * 添加指定节点
     * 
     * @param timeSpecifiedNodeAdd
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "添加指定节点")
    @PostMapping(path = "/timing/add/specified/node")
    public ReturnInfo addSpecifiedNode(
            @RequestBody @Valid TimeSpecifiedNodeAdd timeSpecifiedNodeAdd,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidateLogUtil.paramError(bindingResult, LOG);
        }
        return timingService.addSpecifiedNode(timeSpecifiedNodeAdd);
    }

    /**
     * 添加节假日节点
     * 
     * @param holidayTimes
     * @return
     */
    @ApiOperation(value = "添加节假日节点")
    @PostMapping(path = "/timing/add/holiday/node")
    public ReturnInfo addHolidayNode(@RequestBody String[] holidayTimes) {
        if (null == holidayTimes || holidayTimes.length == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.addHolidayNode(holidayTimes);
    }


    @ApiOperation(value = "删除指定节假日")
    @DeleteMapping(path = "/timing/del/holiday/{time}")
    public ReturnInfo delHoliday(@PathVariable("time") String time) {
        return timingService.delHolidayNode(time);
    }

    @ApiOperation(value = "获取指定年月的指定节假日")
    @ApiImplicitParams({@ApiImplicitParam(name = "time", value = "日期", required = true,
            dataType = "String", paramType = "path")})
    @GetMapping(path = "/timing/holiday/{time}")
    public ReturnInfo getHoliday(@PathVariable("time") String time) {

        return timingService.getHoliday(time);
    }

    /**
     * 时序功能查看
     * 
     * @param viewTime
     * @return
     */
    @ApiOperation(value = "时序功能查看")
    @GetMapping(path = "/timing/node/view/{viewTime}")
    public ReturnInfo<List<TimingList>> view(@PathVariable("viewTime") String viewTime)
            throws ParseException {
        if (null == viewTime) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.timingView(viewTime);
    }


    /**
     * 删除指定节点和普通节点节点
     * 
     * @param id
     * @return
     */
    @ApiOperation(value = "删除指定节点和普通节点节点")
    @DeleteMapping(path = "/timing/del/{id}")
    public ReturnInfo delNode(@PathVariable("id") Long id) {
        if (null == id || id == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return timingService.deleteNode(id);
    }


    @ApiOperation(value = "设定设备位置或群组")
    @GetMapping(path = "/timing/board/list")
    public ReturnInfo<List<BoardList>> getBoardList() {
        return timingService.boardList();
    }
}
