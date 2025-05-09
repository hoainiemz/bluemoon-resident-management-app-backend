package com.example.backend.service;

import com.example.backend.dto.AccountResidentWrapper;
import com.example.backend.model.Account;
import com.example.backend.model.Resident;
import com.example.backend.model.enums.AccountType;
import com.example.backend.repository.RepositoryImpl;
import com.example.backend.repository.ResidentRepository;
import com.example.backend.repository.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resident")
@Transactional
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private RepositoryImpl repositoryImpl;
    @Autowired
    private SettlementRepository settlementRepository;

    @GetMapping("/nativeresidentquery")
    public List<Resident> nativeResidentQuery(@RequestParam String query) {
        return repositoryImpl.executeRawSql(query, Resident.class);
    }

    @GetMapping("/findresidentbyuserid")
    public Resident findResidentByUserId(@RequestParam int id) {
        return residentRepository.findByUserId(id).get();
    }

    @GetMapping("/checkresidentexistbyidentitycard")
    public boolean checkResidentExistByIdentityCard(@RequestParam String identityCard) {
        return residentRepository.existsByIdentityCard(identityCard);
    }

    @PostMapping("/findresidentbyaccount")
    public Resident findResidentByAccount(@RequestBody Account profile) {
        return residentRepository.findByUserId(profile.getUserId()).get();
    }

    @PostMapping("/finddistinctnonnullhouseid")
    public List<String> findDistinctNonNullHouseId(@RequestBody AccountResidentWrapper wrapper) {
        Account profile = wrapper.getProfile();
        Resident resident = wrapper.getResident();
        for (int i = 0; i < 100; i++) {
            try {
                if (profile.getRole() == AccountType.Resident) {
                    return settlementRepository.findApartmentNamesByResidentId(resident.getResidentId());
                }
                return settlementRepository.findAllApartmentNames();
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    @PostMapping("/updateresident")
    public void updateResident(@RequestBody Resident resident) {
        residentRepository.updateRowByUserId(
                resident.getUserId(),
                resident.getFirstName(),
                resident.getLastName(),
                resident.getDateOfBirth(),
                resident.getGender(),
                resident.getIdentityCard(),
                resident.getMoveInDate()
        );
    }

    @PostMapping("/save")
    public void save(@RequestBody Resident resident) {
        residentRepository.save(resident);
    }

    @GetMapping("/findresidentsbyfilters")
    public List<Resident> findResidentsByFilters(@RequestParam String houseNameFilter,
                                                 @RequestParam String roleFilter,
                                                 @RequestParam String searchFilter) {
        return residentRepository.findResidentsByFilters(houseNameFilter, roleFilter, searchFilter);
    }

    @DeleteMapping("/deleteresidentbyid")
    public void deleteResidentById(@RequestParam Integer id) {
        residentRepository.deleteByResidentId(id);
    }
}
