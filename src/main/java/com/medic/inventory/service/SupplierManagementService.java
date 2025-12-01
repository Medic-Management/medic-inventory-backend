package com.medic.inventory.service;

import com.medic.inventory.dto.SupplierRequest;
import com.medic.inventory.dto.SupplierResponse;
import com.medic.inventory.entity.Supplier;
import com.medic.inventory.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierManagementService {

    private final SupplierRepository supplierRepository;

    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return mapToResponse(supplier);
    }

    @Transactional
    public SupplierResponse createSupplier(SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setNombre(request.getNombre());
        supplier.setEmail(request.getEmail());
        supplier.setTelefono(request.getTelefono());
        supplier.setLeadTimeDays(request.getLeadTimeDays());
        supplier.setMoq(request.getMoq());
        supplier.setIsActive(true);

        supplier = supplierRepository.save(supplier);
        return mapToResponse(supplier);
    }

    @Transactional
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        supplier.setNombre(request.getNombre());
        supplier.setEmail(request.getEmail());
        supplier.setTelefono(request.getTelefono());
        supplier.setLeadTimeDays(request.getLeadTimeDays());
        supplier.setMoq(request.getMoq());

        supplier = supplierRepository.save(supplier);
        return mapToResponse(supplier);
    }

    @Transactional
    public void toggleSupplierStatus(Long id) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        supplier.setIsActive(!supplier.getIsActive());
        supplierRepository.save(supplier);
    }

    private SupplierResponse mapToResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setNombre(supplier.getNombre());
        response.setEmail(supplier.getEmail());
        response.setTelefono(supplier.getTelefono());
        response.setLeadTimeDays(supplier.getLeadTimeDays());
        response.setMoq(supplier.getMoq());
        response.setActivo(supplier.getIsActive());
        return response;
    }
}
