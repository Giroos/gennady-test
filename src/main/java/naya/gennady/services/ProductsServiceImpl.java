package naya.gennady.services;

import naya.gennady.model.Product;
import naya.gennady.model.ProductDto;
import naya.gennady.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService{

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<ProductDto> listProducts(String producer,
                                         Integer page,
                                         Integer size,
                                         String sort,
                                         String order){



        Sort sortParam;
        PageRequest pageRequest;
        //if sort field is present, use it!
        if (sort != null) {
            sortParam = new Sort(Sort.Direction.fromString(order), sort);
            pageRequest = PageRequest.of(page,size,sortParam);
        } else {
            pageRequest = PageRequest.of(page,size);
        }

        //if no producer submitted, searching for all products with paging
        List<Product> foundProducts;
        if (producer != null)
            foundProducts = productsRepository.findByProducer(producer, pageRequest);
        else{
            foundProducts = productsRepository.findAll(pageRequest).getContent();
        }

        return convertToDtoList(foundProducts);
    }

    private List<ProductDto> convertToDtoList(List<Product> list){
        List<ProductDto> result = new ArrayList<>(list.size());
        list.forEach(p -> result.add(new ProductDto(p)));
        return result;
    }
}
