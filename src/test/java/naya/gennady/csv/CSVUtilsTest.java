package naya.gennady.csv;

import naya.gennady.csv.CSVUtils;
import naya.gennady.model.Product;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CSVUtilsTest {


    @Test
    public void testColumninField(){
        String line = "\"Xx Shelf, Wire St2448c\",,7061999991,99991,3780,";
        CSVUtils csvReader = new CSVUtils();
        List<String> result = csvReader.parseLine(line);
        assertTrue(result.size() == 6);
        assertEquals("Xx Shelf, Wire St2448c",result.get(0));
    }

    @Test
    public void testCoutes(){
        String line = "\"Cont Fm 6\"\"Blk (504 CT)\",,1619404946,760717,2879,PACTIV";
        CSVUtils csvReader = new CSVUtils();
        List<String> result = csvReader.parseLine(line);
        assertEquals("Cont Fm 6\"Blk (504 CT)",result.get(0));
        assertEquals("",result.get(1));
        assertEquals("1619404946",result.get(2));
        assertEquals("760717",result.get(3));
        assertEquals("2879",result.get(4));
        assertEquals("PACTIV",result.get(5));
    }

    @Test
    public void testLongBarCode(){
        CSVUtils csvReader = new CSVUtils();
        assertNull(csvReader.handleCsvNumber(""));
        assertEquals(new Long(670580000000l),csvReader.handleCsvNumber("6.7058E+11"));
    }

    @Test
    public void testCreateProduct(){
        CSVUtils csvReader = new CSVUtils();
        String line = "Papettis Liquid Eggs 2lb - 15/CS," +
                "https://web.fulcrumapp.com/photos/view?photos=ade6d678-eb5a-4e78-9a56-32d009ca655f%2C9c2d00ba-fa47-43cd-9303-d82b36179364," +
                "4602591200," +
                "52701," +
                "3707," +
                "Papetti's";
        List<String> parsedLine = csvReader.parseLine(line);
        Product product = csvReader.createProduct(parsedLine);
        assertEquals("Papettis Liquid Eggs 2lb - 15/CS",product.getName());
        assertEquals("https://web.fulcrumapp.com/photos/view?photos=ade6d678-eb5a-4e78-9a56-32d009ca655f%2C9c2d00ba-fa47-43cd-9303-d82b36179364",product.getPhotoUrl());
        assertEquals(new Long(4602591200L),product.getBarcode());
        assertEquals(new Long(52701L),product.getSku());
        assertEquals(new Long(3707L),product.getPrice());
        assertEquals("Papetti's",product.getProducer());

    }

    @Test(expected = NumberFormatException.class)
    public void testCreateProductNoSku(){
        CSVUtils csvReader = new CSVUtils();
        String line = "Red Trout Boned 4CT/10OZ ~," +
                "https://web.fulcrumapp.com/photos/view?photos=c386a99c-df56-4a62-a196-80a69b45bfc8%2Cb39ee32a-2705-4073-a258-87f711adb7d0," +
                "77485184228," +
                "," +
                "12300," +
                "IdaSea";
        List<String> parsedLine = csvReader.parseLine(line);
        Product product = csvReader.createProduct(parsedLine);
    }

}
