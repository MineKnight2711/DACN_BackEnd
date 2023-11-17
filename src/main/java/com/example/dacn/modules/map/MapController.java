package com.example.dacn.modules.map;

import com.example.dacn.entity.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
public class MapController {
    @Autowired
    private MapService mapService;
    @GetMapping
    public ResponseModel getListLocation(@RequestParam("address") String address)
    {
        return mapService.getListLocation(address);
    }
    @GetMapping("/predict")
    public ResponseModel predictLocation(@RequestParam("address") String address)
    {
        return mapService.predictLocation(address);
    }
}
