package com.gumtree.addressbook;

import java.io.IOException;

import com.gumtree.addressbook.data.bom.Person;
import com.gumtree.addressbook.data.bom.Person.Gender;

/**
 * Class collecting methods useful to execute checks over the persons in the
 * address book
 */
public interface PersonUtils {
  /**
   * Counts the number of {@link Person} records filtering per
   * {@link Person.Gender}
   * 
   * @param gender
   * @return the number of {@link Person} matching the {@link Person.Gender}
   * @throws IllegalArgumentException
   * @throws IOException
   */
  public long countByGender(final Gender gender) throws IllegalArgumentException, IOException;

  /**
   * Returns the {@link Person} item of the oldest person in the addressbook
   * 
   * @return
   */
  public Person getOldestPerson() throws IOException;

  /**
   * Return the distance in days between the date of birth of 2 persons
   * 
   * @param person1FullName
   * @param person2FullName
   * @return the distance in days between the date of birth of person1 and person2
   */
  public long countDaysBetweenBirths(String person1FullName, String person2FullName)
      throws IllegalArgumentException, IOException;
}