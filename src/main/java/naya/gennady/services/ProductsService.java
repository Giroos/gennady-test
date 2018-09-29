package naya.gennady.services;

import naya.gennady.model.ProductDto;

import java.util.List;

public interface ProductsService  {

    List<ProductDto> listProducts(String producer,
                                  Integer page,
                                  Integer size,
                                  String sort,
                                  String order);
}
