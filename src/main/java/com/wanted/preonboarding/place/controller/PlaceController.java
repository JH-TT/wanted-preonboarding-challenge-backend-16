package com.wanted.preonboarding.place.controller;

import com.wanted.preonboarding.place.dto.PlaceInfo;
import com.wanted.preonboarding.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("place")
public class PlaceController {

    private final PlaceService placeService;

    @PatchMapping()
    public ResponseEntity<PlaceInfo> create(@RequestBody PlaceInfo info) throws Exception {
        log.info(info.toString());
        placeService.create(info);
        return ResponseEntity.ok(info);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceInfo> getPlace(@PathVariable(value = "id") Long id) throws Exception {
        return ResponseEntity.ok(placeService.getPlace(id));
    }
}
