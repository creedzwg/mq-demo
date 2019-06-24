package cn.keking.project.cancel.controller;

import cn.keking.project.cancel.service.CancelRecordInfoService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangwengang
 * @Date: 2019/6/20
 * @Version: V1.0
 * @Description: 取消controller
 */
@Slf4j
@RestController
@RequestMapping("/cancel")
public class CancelRecordInfoController {

    private final CancelRecordInfoService cancelDataInfoService;

    public CancelRecordInfoController(CancelRecordInfoService cancelDataInfoService) {
        this.cancelDataInfoService = cancelDataInfoService;
    }

    /**
     * 根据id取消某次操作
     * @param cancelId 取消操作id
     * @return
     */
    @GetMapping("/confirmCancel")
    public JSONObject confirmCancel(@RequestParam String cancelId) throws Exception {
        Assert.hasLength(cancelId, "取消id不能为空");

        return cancelDataInfoService.confirmCancel(cancelId);
    }


}
