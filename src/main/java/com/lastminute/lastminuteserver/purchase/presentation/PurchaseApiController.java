package com.lastminute.lastminuteserver.purchase.presentation;

import com.lastminute.lastminuteserver.purchase.dto.PurchaseCreateDto;
import com.lastminute.lastminuteserver.purchase.dto.PurchaseResponseDto;
import com.lastminute.lastminuteserver.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("/api/purchase")
public class PurchaseApiController {
    private final PurchaseService purchaseService;

    @PostMapping(value = "/{productId}")
    public ResponseEntity<PurchaseResponseDto> createPurchase(@PathVariable("productId") Long productId,
                                                              @RequestParam Long userId,
                                                              @Valid PurchaseCreateDto purchaseCreateDto){
        PurchaseResponseDto purchaseResponseDto = purchaseService.createPurchase(productId, userId, purchaseCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseResponseDto);
    }

    @GetMapping(value = "/{purchaseId}")
    public ResponseEntity<PurchaseResponseDto> getPurchase(@PathVariable("purchaseId") Long purchaseId,
                                                           @RequestParam Long userId){
        PurchaseResponseDto purchaseResponseDto = purchaseService.getPurchase(purchaseId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(purchaseResponseDto);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<PurchaseResponseDto>> getPurchaseListByUser(@RequestParam Long userId){
        List<PurchaseResponseDto> purchaseResponseDto = purchaseService.getPurchaseListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(purchaseResponseDto);
    }

    @DeleteMapping(value = "/{purchaseId}")
    public ResponseEntity<Object> deletePurchase(@PathVariable("purchaseId") Long purchaseId,
                                                 @RequestParam Long userId){
        purchaseService.deletePurchase(purchaseId, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
