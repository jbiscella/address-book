package com.gumtree.addressbook.data;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CSVDataReaderTest {

  private static final Logger LOGGER = Logger.getLogger("UnitTest");
  private DataReader dataReader;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void before() {
    try {
      dataReader = new CSVDataReader(getClass().getClassLoader().getResource("AddressBook").toURI());
    } catch (URISyntaxException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  @Test
  public void testReadAllData() {
    try {
      List<String> filteredLines = dataReader.readAllData();
      assertTrue("List length is not correct", filteredLines.size() == 5);
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IllegalArgumentException' had been thrown.");
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IOException' had been thrown.");
    }
  }

  @Test
  public void testReadFilteredData() {
    // By name..
    Predicate<String> filter = csvLine -> StringUtils.isNotBlank(csvLine) && csvLine.startsWith("Gemma Lane");
    try {
      List<String> filteredLines = dataReader.readFilteredData(filter);
      assertTrue("List length is not correct", filteredLines.size() == 1);
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IllegalArgumentException' had been thrown.");
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IOException' had been thrown.");
    }
  }

  @Test
  public void testErrorMissingFilter() throws IllegalArgumentException, IOException {
    thrown.expect(IllegalArgumentException.class);
    dataReader.readFilteredData(null);
  }

  
  @Test
  public void testErrorWrongSource() throws IllegalArgumentException, IOException, URISyntaxException { //NOSONAR
    dataReader = new CSVDataReader(new URI(""));
    thrown.expect(IllegalArgumentException.class);
    dataReader.readFilteredData(null);
  }
}