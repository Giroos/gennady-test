package naya.gennady.rest;

import lombok.extern.slf4j.Slf4j;
import naya.gennady.model.ProductDto;
import naya.gennady.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/v1")
@Slf4j
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Value("${products.default.page.size:50}")
    private Integer size;

    @GetMapping(name = "/products", produces = "application/json")
    public List<ProductDto> getProducts(
            @RequestParam @Nullable  String producer,
            @RequestParam @Nullable Integer page,
            @RequestParam @Nullable Integer size,
            @RequestParam @Nullable String sort,
            @RequestParam @Nullable String order){


        //use default page and size if no values submitted
        if (page == null) page = 0;
        if (size == null) size = this.size;

        //fix to default order in case of wrong parameter
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order))
            order = "asc";

        return productsService.listProducts(producer,page,size,sort,order);
    }
}
