package com.gumtree.addressbook;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.gumtree.addressbook.data.DataReader;
import com.gumtree.addressbook.data.bom.Person;
import com.gumtree.addressbook.data.bom.Person.Gender;

public class CSVPersonUtils implements PersonUtils{

  private final DataReader dataReader;

  public CSVPersonUtils(DataReader dataReader) {
    if (dataReader == null)
      throw new IllegalArgumentException("Missing DataReader");
    this.dataReader = dataReader;
  }

  /**
   * @inheritDoc
   */
  public long countByGender(final Gender gender) throws IllegalArgumentException, IOException {
    Predicate<String> csvFilterByGender = csvLine -> gender.equals(new Person(csvLine).getGender().get());
    return dataReader.readFilteredData(csvFilterByGender)
                     .stream()
                     .collect(Collectors.counting());
  }
}
