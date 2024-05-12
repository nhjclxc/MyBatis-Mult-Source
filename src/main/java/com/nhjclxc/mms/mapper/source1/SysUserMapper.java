package com.nhjclxc.mms.mapper.source1;


import com.nhjclxc.mms.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysUserMapper {
    List<SysUser> selectAll();

    int insert(SysUser sysUser);
}
