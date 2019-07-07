package com.gumtree.addressbook;

import static org.junit.Assert.assertEquals;
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
  public void testCountByGenderKOMissingGender() throws IllegalArgumentException, IOException {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Gender is not sat");
    personUtils.countByGender(null);
  }

  @Test
  public void testCountByGenderKOMissingDataReader() throws IllegalArgumentException, IOException {
    thrown.expect(IllegalArgumentException.class);
    personUtils = new CSVPersonUtils(null);
    personUtils.countByGender(Gender.MALE);
  }

  @Test
  public void testGetTheOldest() {
    try {
      assertEquals("Wrong oldest found", personUtils.getOldestPerson().getFullname(), "Wes Jackson");
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IllegalArgumentException' had been thrown.");
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IOException' had been thrown.");
    }
  }

  @Test
  public void testCountDaysBetweenBirths() {
    try {
      long days = personUtils.countDaysBetweenBirths("Bill McKnight", "Paul Robinson");
      assertTrue("Wrong number of days between the 2 birth days",
          personUtils.countDaysBetweenBirths("Bill McKnight", "Paul Robinson") == 2862);
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IllegalArgumentException' had been thrown.");
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      fail("Unexpected 'IOException' had been thrown.");
    }
  }

  @Test
  public void testCountDaysBetweenBirthsKOSamePerson() throws IllegalArgumentException, IOException {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("2 records were expected, less/more were returned");
    personUtils.countDaysBetweenBirths("Bill McKnight", "Bill McKnight");
  }

  @Test
  public void testCountDaysBetweenBirthsKOBlankFullName() throws IllegalArgumentException, IOException {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("One or both the person full names are null");
    personUtils.countDaysBetweenBirths("Bill McKnight", null);
    personUtils.countDaysBetweenBirths("Bill McKnight", "");
    personUtils.countDaysBetweenBirths("Bill McKnight", " ");
    personUtils.countDaysBetweenBirths(null, "Bill McKnight");
    personUtils.countDaysBetweenBirths("", "Bill McKnight");
    personUtils.countDaysBetweenBirths(" ", "Bill McKnight");
    personUtils.countDaysBetweenBirths(null, null);
    personUtils.countDaysBetweenBirths("", "");
    personUtils.countDaysBetweenBirths(" ", " ");
  }
}