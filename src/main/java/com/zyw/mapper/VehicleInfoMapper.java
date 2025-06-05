package com.zyw.mapper;

import com.zyw.entity.VehicleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleInfoMapper {
    @Insert("insert into vehicle_info " +
            "value (#{vid}, #{vin}, #{batteryType}, #{totalMileage}, #{batteryHealth})")
    boolean insert(VehicleInfo vehicleInfo);

    @Select("select * from vehicle_info where vid = #{vid}")
    VehicleInfo findByVid(String vid);

    @Select("select * from vehicle_info where vin = #{vin}")
    VehicleInfo findByVin(long vin);

    @Select("select * from vehicle_info")
    List<VehicleInfo> findAll();

    @Select("select battery_type from vehicle_info where vin = #{vin}")
    String getBatteryTypeByVin(Long vin);

    @Update("update vehicle_info set " +
            "vin = #{vin}, " +
            "battery_type = #{batteryType}, " +
            "total_mileage = #{totalMileage}, " +
            "battery_health = #{batteryHealth} " +
            "WHERE vid = #{vid}")
    boolean update(VehicleInfo vehicleInfo);

    @Delete("delete from vehicle_info where vin = #{vin}")
    boolean delete(String vin);
}
