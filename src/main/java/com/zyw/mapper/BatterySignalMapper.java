package com.zyw.mapper;

import com.zyw.entity.BatterySignal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BatterySignalMapper {

    @Select("select * from battery_signal where vin = #{vin} limit 1")
    BatterySignal getBatterySignalByVin(Long vin);

    @Insert("insert into battery_signal(vin,Mx,Mi,Ix,Ii) " +
            "value(#{vin},#{Mx},#{Mi},#{Ix},#{Ii})")
    boolean insert(BatterySignal batterySignal);

}
