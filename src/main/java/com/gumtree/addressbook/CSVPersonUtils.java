package com.gumtree.addressbook;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.gumtree.addressbook.data.DataReader;
import com.gumtree.addressbook.data.bom.Person;
import com.gumtree.addressbook.data.bom.Person.Gender;

import org.apache.commons.lang3.StringUtils;

public class CSVPersonUtils implements PersonUtils {

  // Error messages
  private static final String ERROR_MESSAGE_COUNT_BY_GENDER = "Gender is not sat";
  private static final String ERROR_MESSAGE_COUNT_DAYS_NULL_NAMES = "One or both the person full names are null";
  private static final String ERROR_MESSAGE_COUNT_DAYS_WRONG_RECORD_NUMBER = "2 records were expected, less/more were returned";
  private static final String ERROR_MESSAGE_COUNT_DAYS_NO_DATE_OF_BIRTH = "date not present for one or both of the persons";

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
    if (gender == null)
      throw new IllegalArgumentException(ERROR_MESSAGE_COUNT_BY_GENDER);
    Predicate<String> csvFilterByGender = csvLine -> gender.equals(new Person(csvLine).getGender().get());
    return dataReader.readFilteredData(csvFilterByGender).stream().collect(Collectors.counting());
  }

  /**
   * @inheritDoc
   */
  @Override
  public Person getOldestPerson() throws IOException {
    Function<String, Person> csvToPerson = Person::new;
    Comparator<Person> compareDateOfBirth = (person1, person2) -> person2.getDateOfBirth().get()
        .compareTo(person1.getDateOfBirth().get());
    return dataReader.readAllData().stream().map(csvToPerson).filter(person -> person.getDateOfBirth().isPresent())
        .max(compareDateOfBirth).get();
  }

  /**
   * @inheritDoc
   */
  @Override
  public long countDaysBetweenBirths(final String person1FullName, final String person2FullName)
      throws IllegalArgumentException, IOException {
    // Filter the csv lines for the 2 users
    if (StringUtils.isBlank(person1FullName) || StringUtils.isBlank(person2FullName)) {
      throw new IllegalArgumentException(ERROR_MESSAGE_COUNT_DAYS_NULL_NAMES);
    }
    Predicate<String> csvFilterByName = csvLine -> csvLine.startsWith(person1FullName)
        || csvLine.startsWith(person2FullName);
    List<String> csvLines = dataReader.readFilteredData(csvFilterByName);

    // Check that there are records in address book for both the persons
    if (csvLines.size() != 2)
      throw new IllegalStateException(ERROR_MESSAGE_COUNT_DAYS_WRONG_RECORD_NUMBER);
    Person person1 = new Person(csvLines.get(0));
    Person person2 = new Person(csvLines.get(1));

    // Check that date of birth is present for both
    if (!person1.getDateOfBirth().isPresent() || !person2.getDateOfBirth().isPresent())
      throw new IllegalStateException(ERROR_MESSAGE_COUNT_DAYS_NO_DATE_OF_BIRTH);
    return ChronoUnit.DAYS.between(person1.getDateOfBirth().get(), person2.getDateOfBirth().get());
  }
}
