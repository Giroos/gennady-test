package naya.gennady.csv;

import naya.gennady.model.Product;
import naya.gennady.repositories.ProductsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CatalogPersisterTest {

    @Value("${catalog.persister.test.csv.file.path}")
    private String testCsvFile;

    @Autowired
    private CatalogPersister catalogPersister;

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    public void updateCatalog() throws Exception {
        //test all records are inserted
        assertEquals(5,productsRepository.count());
        assertEquals("Spillage Pan S/S",productsRepository.findById(81263L).get().getName());

        //update DB with another file
        catalogPersister.updateCatalog(testCsvFile);

        //test that records are actually updated
        assertEquals(6,productsRepository.count());
        assertTrue(productsRepository.findById(11111L).isPresent());
        assertEquals("TestName",productsRepository.findById(81263L).get().getName());
    }

}