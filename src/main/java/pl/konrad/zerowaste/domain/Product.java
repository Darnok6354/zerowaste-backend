package pl.konrad.zerowaste.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacja do słownika (nasza nowość)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_product_id", nullable = false)
    private MasterProduct masterProduct;

    // Relacja do użytkownika
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;


    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_status", nullable = false)
    private ProductLifecycleStatus lifecycleStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductUnit unit;

    // Zmienione z 'storageLocation' na 'location' - tak jak miałeś w swoim kodzie
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageLocation location;

    @Column(nullable = false)
    private Double quantity;

    // --- POLA, KTÓRYCH BRAKOWAŁO ---

    @Column(name = "waste_reason")
    private String wasteReason;

    @Column(name = "status_changed_at")
    private LocalDateTime statusChangedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Automatyczne ustawianie daty utworzenia przed zapisem do bazy
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.lifecycleStatus == null) {
            this.lifecycleStatus = ProductLifecycleStatus.ACTIVE;
        }
        if (this.statusChangedAt == null) {
            this.statusChangedAt = LocalDateTime.now();
        }
    }
}