package pl.konrad.zerowaste.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "master_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Przechowujemy nazwy po angielsku pod integrację z API (np. "Apple", "Milk")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "category")
    private String category; // np. "Dairy", "Vegetables"

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "barcode")
    private String barcode; // Przygotowanie pod skaner w aplikacji mobilnej

    @Enumerated(EnumType.STRING)
    @Column(name = "default_unit")
    private ProductUnit defaultUnit;
}