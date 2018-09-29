package naya.gennady.repositories;

import naya.gennady.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductsRepository extends PagingAndSortingRepository<Product,Long> {

    List<Product> findByProducer(String producer, Pageable pageable);
}
