package naya.gennady.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class Product {

    @Id
    private Long sku;
    private Long price;
    private Long barcode;

    private String photoUrl;
    private String name;
    private String producer;

}
