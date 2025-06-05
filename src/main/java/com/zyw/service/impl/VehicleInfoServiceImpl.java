package com.zyw.service.impl;

import com.zyw.entity.VehicleInfo;
import com.zyw.mapper.VehicleInfoMapper;
import com.zyw.service.VehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class VehicleInfoServiceImpl implements VehicleInfoService {

    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    private final Random random = new Random();

    @Override
    public boolean addVehicleInfo(VehicleInfo vehicleInfo) {
        if (!StringUtils.hasText(vehicleInfo.getVid())) {
            vehicleInfo.setVid(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
        }
        return vehicleInfoMapper.insert(vehicleInfo);
    }

    @Override
    public boolean addVehicleInfos(List<VehicleInfo> vehicleInfos) {
        for(VehicleInfo vehicleInfo:vehicleInfos){
            if(!addVehicleInfo(vehicleInfo)){
                return false;
            }
        }
        return true;
    }

    @Override
    public VehicleInfo getByVid(String vid) {
        return vehicleInfoMapper.findByVid(vid);
    }

    @Override
    public VehicleInfo getByVin(long vin) {
        return vehicleInfoMapper.findByVin(vin);
    }

    @Override
    public List<VehicleInfo> getAllVehicles() {
        return vehicleInfoMapper.findAll();
    }

    @Override
    public boolean updateVehicle(VehicleInfo vehicleInfo) {
        return vehicleInfoMapper.update(vehicleInfo);
    }

    @Override
    public boolean deleteVehicle(String vid) {
        return vehicleInfoMapper.delete(vid);
    }
}
