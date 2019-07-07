package com.gumtree.addressbook;

import java.io.IOException;

import com.gumtree.addressbook.data.bom.Person.Gender;

/**
 * Class collecting methods useful to execute checks over the persons in the address book 
 */
public interface PersonUtils {
  /**
   * Counts the number of {@link Person} records filtering per {@link Person.Gender}
   * 
   * @param gender
   * @return the number of {@link Person} matching the {@link Person.Gender}
   * @throws IllegalArgumentException
   * @throws IOException
   */
  public long countByGender(final Gender gender) throws IllegalArgumentException, IOException;
  
}