package com.nhjclxc.mms.mapper.source2;


import com.nhjclxc.mms.model.BizOrder;
import com.nhjclxc.mms.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BizOrderMapper {
    List<BizOrder> selectAll();
    List<SysUser> selectAllUser();
}
