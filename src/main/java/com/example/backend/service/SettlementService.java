package com.example.backend.service;

import com.example.backend.dto.ApartmentCountDTO;
import com.example.backend.model.Settlement;
import com.example.backend.repository.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Transactional
@RestController
@RequestMapping("/settlement")
public class SettlementService {
    @Autowired
    private SettlementRepository settlementRepository;

    @GetMapping("/getapartmentsandresidentcount")
    public List<ApartmentCountDTO> getApartmentsAndResidentCount(@RequestParam Integer residentId, @RequestParam String s) {
        return settlementRepository.findFilteredApartmentCountsByResidentId(residentId, s);
    }

    @GetMapping("/getapartmentsandresidentcountbysearch")
    public List<ApartmentCountDTO> getApartmentsAndResidentCountBySearch(@RequestParam String s) {
        return settlementRepository.findApartmentCountsBySearch(s);
    }

    @GetMapping("/getsettlementsbyapartmentid")
    public List<Settlement> getSettlementsByApartmentId(@RequestParam Integer id) {
        return settlementRepository.findSettlementsByApartmentId(id);
    }

    @PostMapping("/saveall")
    public void saveAll(@RequestBody List<Settlement>ds) {
        settlementRepository.saveAll(ds);
    }

    @PostMapping("/deletebyids")
    public void deleteByIds(@RequestBody List<Integer> ds) {
        settlementRepository.deleteSettlementsBySettlementId(ds);
    }
}
