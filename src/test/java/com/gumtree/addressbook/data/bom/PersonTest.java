package com.gumtree.addressbook.data.bom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.gumtree.addressbook.data.bom.Person.Gender;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PersonTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testValidCSVRecordAll() {
    Person person1 = new Person("Test name, Male, 02/04/81");

    // Test person full name
    assertEquals("Invalid fullName", "Test name", person1.getFullname()); //NOSONAR

    // Test person gender
    assertTrue("Gender value is missing", person1.getGender().isPresent());//NOSONAR
    assertEquals("Invalid gender", Gender.MALE, person1.getGender().get());//NOSONAR

    // Test date of birth
    assertTrue("Date of birth value is missing", person1.getDateOfBirth().isPresent());//NOSONAR
    assertEquals("Invalid gender", LocalDate.of(1981, 4, 2), person1.getDateOfBirth().get());
  }

  @Test
  public void testValidCSVNoGender() {
    Person person1 = new Person("Test name");

    // Test person full name
    assertEquals("Invalid fullName", "Test name", person1.getFullname());

    // Test person gender
    assertFalse("Gender value is missing", person1.getGender().isPresent());

    // Test date of birth
    assertFalse("Date of birth value is missing", person1.getDateOfBirth().isPresent());
  }
  
  @Test
  public void testValidCSVBlankGender() {
    Person person1 = new Person("Test name, , 02/04/81");

    // Test person full name
    assertEquals("Invalid fullName", "Test name", person1.getFullname());

    // Test person gender
    assertFalse("Gender value is missing", person1.getGender().isPresent());

    // Test date of birth
    assertTrue("Date of birth value is missing", person1.getDateOfBirth().isPresent());
    assertEquals("Invalid gender", LocalDate.of(1981, 4, 2), person1.getDateOfBirth().get());
  }

  @Test
  public void testValidCSVNoDate() {
    Person person1 = new Person("Test name, Male");

    // Test person full name
    assertEquals("Invalid fullName", "Test name", person1.getFullname());

    // Test person gender
    assertTrue("Gender value is missing", person1.getGender().isPresent());
    assertEquals("Invalid gender", Gender.MALE, person1.getGender().get());

    // Test date of birth
    assertFalse("Date of birth value is missing", person1.getDateOfBirth().isPresent());
  }
  
  @Test
  public void testValidCSVBlankDate() {
    Person person1 = new Person("Test name, Male,");

    // Test person full name
    assertEquals("Invalid fullName", "Test name", person1.getFullname());

    // Test person gender
    assertTrue("Gender value is missing", person1.getGender().isPresent());
    assertEquals("Invalid gender", Gender.MALE, person1.getGender().get());

    // Test date of birth
    assertFalse("Date of birth value is missing", person1.getDateOfBirth().isPresent());
  }

  @Test
  public void testValidCSVOnlyName() {
    Person person1 = new Person("Test name");

    // Test person full name
    assertEquals("Invalid fullName", "Test name", person1.getFullname());

    // Test person gender
    assertFalse("Gender value is missing", person1.getGender().isPresent());

    // Test date of birth
    assertFalse("Date of birth value is missing", person1.getDateOfBirth().isPresent());
  }

  @Test
  public void testInvalidCSVNullArgument(){
    thrown.expect(IllegalArgumentException.class);
    new Person(null);
  }

  @Test
  public void testInvalidCSVEmptyLine(){
    thrown.expect(IllegalArgumentException.class);
    new Person("");
  }

  @Test
  public void testInvalidCSVBlankString(){
    thrown.expect(IllegalArgumentException.class);
    new Person(" ");
  }

  @Test
  public void testInvalidCSVIllegalGender(){
    thrown.expect(IllegalArgumentException.class);
    new Person("Test name, male "); // As is case sensitive
  }

  @Test
  public void testInvalidCSVIllegalDateOfBirth(){
    thrown.expect(DateTimeParseException.class);
    new Person("Test name, Male, 02-04-1981 "); // As is case sensitive
  }
}