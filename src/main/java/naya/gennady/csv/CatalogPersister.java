package naya.gennady.csv;

import lombok.extern.slf4j.Slf4j;
import naya.gennady.model.Product;
import naya.gennady.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
this class is responsible for reading catalog from csv file and storing to DB
*/
@Component
@Slf4j
public class CatalogPersister {

    @Autowired
    private CSVUtils csvUtils;

    @Autowired
    private ProductsRepository productsRepository;

    @Value("${catalog.persister.batch.size:10000}")
    private Long batchSize;

    @Value("${catalog.persister.csv.file.path}")
    private String csvFilePath;

    @Value("${catalog.persister.csv.columns}")
    private Integer csvColumns;

    @PostConstruct
    @Scheduled(cron="${catalog.persister.cron}")
    public void updateCatalog(){
        updateCatalog(csvFilePath);
    }


    public void updateCatalog(String filePath) {
        Scanner scanner = null;
        log.debug("product catalog update begins...");
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            log.error("catalog file <" + filePath + "> not found", e);
            return;
        }

        List<Product> productsToSave = new ArrayList<>(batchSize.intValue());

        //skip the first line (with column names) in products catalog csv
        scanner.nextLine();
        long lineCounter = 2;

        while (scanner.hasNext()) {
            try {
                List<String> line = csvUtils.parseLine(scanner.nextLine());
                if (line.size() != csvColumns) {
                    log.error("csv line " + lineCounter + " have invalid number of fields, values: " + line);
                } else {
                    Product p = csvUtils.createProduct(line);
                    productsToSave.add(p);
                }
            }catch (Throwable e){
                //skip the invalid record and continue
                log.error("error parsing csv line " + lineCounter, e);
            }

            if (productsToSave.size() == batchSize) {
                try {
                    productsRepository.saveAll(productsToSave);
                }catch (Throwable e){
                    log.error("error saving batch of " + batchSize + " products, csv line: " + lineCounter, e);
                }
                productsToSave = new ArrayList<>(batchSize.intValue());
            }
            lineCounter++;
        }

        if (productsToSave.size() > 0){
            try {
                productsRepository.saveAll(productsToSave);
            }catch (Throwable e){
                log.error("error saving LAST batch of " + productsToSave.size() + " products", e);
            }
        }
    }
}
