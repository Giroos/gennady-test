package naya.gennady.model;

import lombok.Data;

@Data
public class ProductDto {

    private Long sku;
    private Long price;
    private Long barcode;

    private String photoUrl;
    private String name;
    private String producer;

    public ProductDto(Product product){
        this.sku = product.getSku();
        this.price = product.getPrice();
        this.barcode = product.getBarcode();
        this.photoUrl = product.getPhotoUrl();
        this.name = product.getName();
        this.producer = product.getProducer();
    }

}
