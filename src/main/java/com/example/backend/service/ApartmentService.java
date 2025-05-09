package com.example.backend.service;

import com.example.backend.model.Apartment;
import com.example.backend.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Transactional
@RestController
@RequestMapping("/apartment")
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @GetMapping("/checkexistsbyapartmentname")
    public boolean checkExistsByApartmentName(@RequestParam String name) {
        return apartmentRepository.existsByApartmentName(name);
    }

    @PostMapping("/save")
    public Apartment save(@RequestBody Apartment apartment) {
        for (int i = 0; i < 10; i++) {
            try {
                return apartmentRepository.save(apartment);
            }
            catch (Exception e) {
                continue;
            }
        }
        return apartmentRepository.save(apartment);
    }

    @GetMapping("/getbyapartmentid")
    public Apartment getByApartmentId(@RequestParam Integer id) {
        return apartmentRepository.findByApartmentId(id);
    }

    @GetMapping("/getapartmentidsbybillid")
    public List<Integer> getApartmentIdsByBillId(@RequestParam Integer id) {
        return apartmentRepository.findApartmentIdsByBillId(id);
    }

    @GetMapping("/findapartmentbyapartmentname")
    public Apartment findApartmentByApartmentName(@RequestParam String name) {
        return apartmentRepository.findByApartmentName(name);
    }

    @DeleteMapping("/deleteapartmentbyid")
    public void deleteApartmentById(@RequestParam Integer id) {
        apartmentRepository.deleteByApartmentId(id);
    }
}
