package com.zyw.controller;

import com.zyw.entity.Result;
import com.zyw.entity.VehicleInfo;
import com.zyw.service.VehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-info")
public class VehicleInfoController {

    @Autowired
    private VehicleInfoService vehicleInfoService;

    @PostMapping
    public Result<String> addVehicles(@RequestBody List<VehicleInfo> vehicleInfos) {
        if (vehicleInfoService.addVehicleInfos(vehicleInfos)) {
            return Result.success("插入数据成功！");
        }
        return Result.error("插入数据失败！");
    }

    @GetMapping("/{vid}")
    public Result<VehicleInfo> getByVid(@PathVariable String vid) {
        VehicleInfo vehicleInfo = vehicleInfoService.getByVid(vid);
        return Result.success(vehicleInfo);
    }

    @GetMapping("/by-vin/{vin}")
    public Result<VehicleInfo> getByVin(@PathVariable long vin) {
        VehicleInfo vehicleInfo = vehicleInfoService.getByVin(vin);
        return Result.success(vehicleInfo);
    }

    @DeleteMapping("/{vin}")
    public Result<String> deleteVehicle(@PathVariable String vin) {
        if (vehicleInfoService.deleteVehicle(vin)) {
            return Result.success("删除数据成功！");
        }
        return Result.error("删除数据失败！");
    }

}
