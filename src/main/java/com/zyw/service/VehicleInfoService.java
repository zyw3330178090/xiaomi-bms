package com.zyw.service;

import com.zyw.entity.VehicleInfo;

import java.util.List;

public interface VehicleInfoService {

    boolean addVehicleInfo(VehicleInfo vehicleInfo);

    boolean addVehicleInfos(List<VehicleInfo> vehicleInfos);

    VehicleInfo getByVid(String vid);

    VehicleInfo getByVin(long vin);

    List<VehicleInfo> getAllVehicles();

    boolean updateVehicle(VehicleInfo vehicleInfo);

    boolean deleteVehicle(String vid);

}

