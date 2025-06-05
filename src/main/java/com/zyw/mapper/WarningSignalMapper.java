package com.zyw.mapper;

import com.zyw.entity.WarningSignal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface WarningSignalMapper {

    @Insert("insert into bms.warning_signal(vin, warn_name, warn_level) " +
            "values (#{vin},#{warnName},#{warnLevel})")
    boolean insert(WarningSignal warningSignal);

}
