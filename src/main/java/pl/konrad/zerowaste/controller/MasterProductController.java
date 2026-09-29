package pl.konrad.zerowaste.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.dto.MasterProductResponse;
import pl.konrad.zerowaste.service.MasterProductService;

import java.util.List;

@RestController
@RequestMapping("/api/master-products")
@RequiredArgsConstructor
public class MasterProductController {

    private final MasterProductService masterProductService;

    @GetMapping
    public ResponseEntity<List<MasterProductResponse>> getAllMasterProducts(
            @RequestParam(required = false) String query
    ) {
        return ResponseEntity.ok(masterProductService.getMasterProducts(query));
    }
}