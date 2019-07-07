package com.gumtree.addressbook;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gumtree.addressbook.data.CSVDataReader;
import com.gumtree.addressbook.data.DataReader;
import com.gumtree.addressbook.PersonUtils;
import com.gumtree.addressbook.data.bom.Person.Gender;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CSVPersonUtilsTest {
  private static final Logger LOGGER = Logger.getLogger("UnitTest");

  private PersonUtils personUtils;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void before() {
    try {
      DataReader dataReader = new CSVDataReader(getClass().getClassLoader().getResource("AddressBook").toURI());
      personUtils = new CSVPersonUtils(dataReader);
    } catch (URISyntaxException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  @Test
  public void testCountByGender() {
    try {
      assertTrue("Wrong number of males calculated", personUtils.countByGender(Gender.MALE) == 3);
      assertTrue("Wrong number of females calculated", personUtils.countByGender(Gender.FEMALE) == 2);
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IllegalArgumentException' had been thrown.");
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IOException' had been thrown.");
    }
  }

  @Test
  public void testCountByGenderKOMissingDataReader() throws IllegalArgumentException, IOException {
    thrown.expect(IllegalArgumentException.class);
    personUtils = new CSVPersonUtils(null);
    personUtils.countByGender(Gender.MALE);
  }
}