package com.clothware.controller;

import com.clothware.model.Settings;
import com.clothware.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingsRepository settingsRepo;

    /** GET /api/settings */
    @GetMapping
    public ResponseEntity<Settings> getSettings() {
        return settingsRepo.findAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** PUT /api/settings */
    @PutMapping
    public ResponseEntity<Settings> updateSettings(@RequestBody Settings updated) {
        return settingsRepo.findAll().stream().findFirst().map(s -> {
            s.setShopName(updated.getShopName());
            s.setCurrency(updated.getCurrency());
            s.setEmail(updated.getEmail());
            s.setPhone(updated.getPhone());
            s.setAddress(updated.getAddress());
            return ResponseEntity.ok(settingsRepo.save(s));
        }).orElseGet(() -> ResponseEntity.ok(settingsRepo.save(updated)));
    }
}
