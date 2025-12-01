package com.medic.inventory.service;

import com.medic.inventory.dto.ProductRequest;
import com.medic.inventory.dto.ProductResponse;
import com.medic.inventory.dto.ProductWithInventoryDTO;
import com.medic.inventory.entity.Category;
import com.medic.inventory.entity.Product;
import com.medic.inventory.entity.Supplier;
import com.medic.inventory.repository.CategoryRepository;
import com.medic.inventory.repository.ProductRepository;
import com.medic.inventory.repository.SupplierRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final EntityManager entityManager;
    private final com.medic.inventory.repository.LoteRepository loteRepository;
    private final com.medic.inventory.repository.MovimientoRepository movimientoRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertToResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        // HU-12 Escenario 1: Validar unicidad de código
        if (request.getCodigo() != null && !request.getCodigo().isEmpty()) {
            if (productRepository.existsByCodigo(request.getCodigo())) {
                throw new RuntimeException("CODIGO_DUPLICADO: Ya existe un producto con el código '" + request.getCodigo() + "'");
            }
        }

        Product product = new Product();
        updateProductFromRequest(product, request);
        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        updateProductFromRequest(product, request);
        Product updatedProduct = productRepository.save(product);
        return convertToResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }

        // HU-12 Escenario 3: Validar que no tenga movimientos vigentes antes de eliminar
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si tiene lotes asociados
        long lotesCount = loteRepository.findByProductoId(id).size();
        if (lotesCount > 0) {
            throw new RuntimeException("PRODUCTO_EN_USO: El medicamento tiene " + lotesCount + " lote(s) asociado(s) y no se puede eliminar. Debe procesar todos los lotes primero.");
        }

        // Verificar si tiene movimientos asociados (a través de lotes)
        List<com.medic.inventory.entity.Lote> lotes = loteRepository.findByProductoId(id);
        for (com.medic.inventory.entity.Lote lote : lotes) {
            long movimientosCount = movimientoRepository.findByLoteId(lote.getId()).size();
            if (movimientosCount > 0) {
                throw new RuntimeException("PRODUCTO_EN_USO: El medicamento tiene movimientos de inventario asociados y no se puede eliminar.");
            }
        }

        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getCriticalProducts() {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getLowStockProducts() {
        return new ArrayList<>();
    }

    private void updateProductFromRequest(Product product, ProductRequest request) {
        // HU-12: Actualizar código si se proporciona
        if (request.getCodigo() != null) {
            product.setCodigo(request.getCodigo());
        }
        product.setName(request.getName());
        product.setBatchNumber(request.getBatchNumber());
        product.setExpirationDate(request.getExpirationDate());
        product.setQuantity(request.getQuantity());
        product.setAlertValue(request.getAlertValue());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setSalePrice(request.getSalePrice());
        product.setObservations(request.getObservations());
        product.setImageUrl(request.getImageUrl());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(category);
        }

        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            product.setSupplier(supplier);
        }
    }

    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setBatchNumber(product.getBatchNumber());
        response.setExpirationDate(product.getExpirationDate());
        response.setQuantity(product.getQuantity());
        response.setAlertValue(product.getAlertValue());
        response.setPurchasePrice(product.getPurchasePrice());
        response.setSalePrice(product.getSalePrice());
        response.setObservations(product.getObservations());
        response.setImageUrl(product.getImageUrl());
        response.setStatus(product.getStatus().name());
        response.setInitialStock(product.getInitialStock());
        response.setInTransit(product.getInTransit());

        if (product.getCategory() != null) {
            response.setCategoryName(product.getCategory().getName());
        }

        if (product.getSupplier() != null) {
            response.setSupplierName(product.getSupplier().getName());
            response.setSupplierContact(product.getSupplier().getContactNumber());
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<ProductWithInventoryDTO> getProductsWithInventory() {
        String sql = "SELECT " +
                "p.id, " +
                "p.codigo, " +
                "p.nombre, " +
                "c.nombre as categoria, " +
                "COALESCE(SUM(i.cantidad), 0) as cantidad, " +
                "COALESCE(u.minimo, 10) as alertValue, " +
                "2.50 as precio, " +
                "MIN(l.fecha_vencimiento) as fecha_vencimiento, " +
                "MIN(l.codigo_producto_prov) as loteNumero, " +
                "p.requiere_refrigeracion, " +
                "p.controlado, " +
                "COALESCE(u.punto_pedido, 50) as puntoPedido " +
                "FROM PRODUCTOS p " +
                "LEFT JOIN CATEGORIAS c ON p.categoria_id = c.id " +
                "LEFT JOIN LOTES l ON l.producto_id = p.id " +
                "LEFT JOIN INVENTARIO i ON i.lote_id = l.id " +
                "LEFT JOIN UMBRAL_STOCK u ON u.producto_id = p.id AND u.sede_id = 1 " +
                "GROUP BY p.id, p.codigo, p.nombre, c.nombre, u.minimo, u.punto_pedido, p.requiere_refrigeracion, p.controlado " +
                "ORDER BY p.id";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        List<ProductWithInventoryDTO> products = new ArrayList<>();
        for (Object[] row : results) {
            ProductWithInventoryDTO dto = new ProductWithInventoryDTO();
            dto.setId(((Number) row[0]).longValue());
            dto.setCodigo((String) row[1]);
            dto.setNombre((String) row[2]);
            dto.setCategoria((String) row[3]);
            dto.setCantidad(row[4] != null ? ((Number) row[4]).intValue() : 0);
            dto.setAlertValue(row[5] != null ? ((Number) row[5]).intValue() : 10);
            dto.setPrecio(row[6] != null ? new BigDecimal(row[6].toString()) : BigDecimal.ZERO);
            dto.setFechaVencimiento(row[7] != null ? row[7].toString() : "N/A");
            dto.setLoteNumero((String) row[8]);
            dto.setRequiereRefrigeracion(row[9] != null && ((Number) row[9]).intValue() == 1);
            dto.setControlado(row[10] != null && ((Number) row[10]).intValue() == 1);

            int cantidad = dto.getCantidad();
            int minimo = dto.getAlertValue();
            int puntoPedido = row[11] != null ? ((Number) row[11]).intValue() : 50;

            if (cantidad == 0) {
                dto.setStatus("CRITICAL");
            } else if (cantidad < minimo) {
                dto.setStatus("CRITICAL");
            } else if (cantidad < puntoPedido) {
                dto.setStatus("LOW_STOCK");
            } else {
                dto.setStatus("IN_STOCK");
            }

            products.add(dto);
        }

        return products;
    }

    @Transactional(readOnly = true)
    public List<Object> getLotesDisponiblesByProducto(Long productoId) {
        String sql = "SELECT " +
                "l.id, " +
                "l.codigo_producto_prov as codigoProductoProv, " +
                "l.fecha_vencimiento as fechaVencimiento, " +
                "COALESCE(SUM(i.cantidad), 0) as stockDisponible " +
                "FROM LOTES l " +
                "LEFT JOIN INVENTARIO i ON i.lote_id = l.id " +
                "WHERE l.producto_id = :productoId AND l.estado = 1 " +
                "GROUP BY l.id, l.codigo_producto_prov, l.fecha_vencimiento " +
                "HAVING COALESCE(SUM(i.cantidad), 0) > 0 " +
                "ORDER BY l.fecha_vencimiento ASC";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("productoId", productoId);

        List<Object[]> results = query.getResultList();
        List<Object> lotes = new ArrayList<>();

        for (Object[] row : results) {
            java.util.Map<String, Object> lote = new java.util.HashMap<>();
            lote.put("id", ((Number) row[0]).longValue());
            lote.put("codigoProductoProv", row[1]);
            lote.put("fechaVencimiento", row[2] != null ? row[2].toString() : "");
            lote.put("stockDisponible", ((Number) row[3]).intValue());
            lotes.add(lote);
        }

        return lotes;
    }
}
