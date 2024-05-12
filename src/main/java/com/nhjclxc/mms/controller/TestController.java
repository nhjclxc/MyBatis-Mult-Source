package com.nhjclxc.mms.controller;

import com.nhjclxc.mms.annotation.CustomTransactional;
import com.nhjclxc.mms.mapper.source1.SysUserMapper;
import com.nhjclxc.mms.mapper.source2.BizOrderMapper;
import com.nhjclxc.mms.model.BizOrder;
import com.nhjclxc.mms.model.SysUser;
import com.nhjclxc.mms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 控制器
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private BizOrderMapper bizOrderMapper;

    @GetMapping("/user")
    public JsonResult<Object> selectAllSysUser() {
        List<SysUser> sysUsers = sysUserMapper.selectAll();
        for (SysUser sysUser : sysUsers) {
            System.out.println(sysUser);
        }
        return JsonResult.success(sysUsers);
    }

    /**
     * 测试事务回滚
     *
     * @author 罗贤超
     */
    @GetMapping("/userInsert")
    @CustomTransactional(rollBackSource = CustomTransactional.SOURCE_1)
    public JsonResult<Map<String, Object>> userInsert() {
        long l = System.currentTimeMillis();
        int count = sysUserMapper.insert(SysUser.builder().userName("name:" + l).password("pp").build());

        System.out.println(count);

        if (l % 2 == 0) {
            throw new RuntimeException("抛出了一个异常");
        }
        return JsonResult.success();
    }

    @GetMapping("/order")
    public JsonResult<Object> selectAll() {
        // java.sql.SQLSyntaxErrorException: Table 'source2.sys_user' doesn't exist
//        List<SysUser> sysUsers = bizOrderMapper.selectAllUser();


        List<BizOrder> bizOrders = bizOrderMapper.selectAll();
        for (BizOrder bizOrder : bizOrders) {
            System.out.println(bizOrder);
        }
        return JsonResult.success(bizOrders);
    }


}
