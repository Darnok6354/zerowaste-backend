package pl.konrad.zerowaste.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kto wykonał akcję
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    // Jaki to był produkt ze słownika
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_product_id", nullable = false)
    private MasterProduct masterProduct;

    // Co się z nim stało (CONSUMED lub WASTED)
    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_status", nullable = false)
    private ProductLifecycleStatus lifecycleStatus;

    // Powód wyrzucenia (jeśli WASTED)
    @Column(name = "waste_reason")
    private String wasteReason;

    // Jaka to była ilość (ważne do statystyk!)
    @Column(nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductUnit unit;

    // Kiedy to się stało
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
}