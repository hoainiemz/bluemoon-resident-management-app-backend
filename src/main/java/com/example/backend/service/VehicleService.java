package com.example.backend.service;

import com.example.backend.dto.VehicleInfo;
import com.example.backend.model.Vehicle;
import com.example.backend.model.enums.VehicleType;
import com.example.backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@Transactional
@RestController
@RequestMapping("/vehicle")
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    @GetMapping("/getvehicleinfobyfilters")
    public List<VehicleInfo> getVehicleInfoByFilters(@RequestParam(required = false) String houseIdFilter, @RequestParam(required = false) VehicleType typeFilter, @RequestParam String searchFilter) {
        for (int i = 0; i < 100; i++) {
            try {
                return vehicleRepository.findVehicleInfoByFilters(houseIdFilter, typeFilter, searchFilter);
            }
            catch (Exception e) {
                continue;
            }
        }
        return vehicleRepository.findVehicleInfoByFilters(houseIdFilter, typeFilter, searchFilter);
    }

    @GetMapping("/getvehicleinfobyresidentandfilters")
    public List<VehicleInfo> getVehicleInfoByResidentAndFilters(@RequestParam Integer residentId, @RequestParam(required = false) String houseIdFilter, @RequestParam(required = false) VehicleType typeFilter, @RequestParam String searchFilter) {
        for (int i = 0; i < 100; i++) {
            try {
                return vehicleRepository.findVehicleInfoByResidentAndFilters(residentId, houseIdFilter, typeFilter, searchFilter);
            }
            catch (Exception e) {
                continue;
            }
        }
        return vehicleRepository.findVehicleInfoByResidentAndFilters(residentId, houseIdFilter, typeFilter, searchFilter);
    }

    @PostMapping("/save")
    public Vehicle save(@RequestBody Vehicle vehicle) {
        for (int i = 0; i < 100; i++) {
            try {
                return vehicleRepository.save(vehicle);
            }
            catch (Exception e) {
                continue;
            }
        }
        return vehicleRepository.save(vehicle);
    }

    @GetMapping("/checkexistbylicenseplate")
    public boolean checkExistByLicensePlate(@RequestParam String val) {
        return vehicleRepository.existsByLicensePlate(val);
    }

    @DeleteMapping("/deletevehiclebyid")
    public void deleteVehicleById(@RequestParam Integer id) {
        vehicleRepository.deleteByVehicleId(id);
    }
}
