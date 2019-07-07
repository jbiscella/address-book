package com.gumtree.addressbook.data.bom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/** 
 * Represent a single valid line of a CSV file.
 */
public class Person {
  // Constants in use
  private static final String CSV_SEPARATOR = ",";
  private static final String[] VALID_VALUES_GENDER = new String[] { "Male", "Female", "" };
  private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().appendPattern("dd/MM/")
      .appendValueReduced(ChronoField.YEAR, 2, 2, 1900).toFormatter();

  // Error messages
  private static final String ERROR_MESSAGE_INVALID_CSV_FORMAT = "Impossible to build a 'User' from a blank csv line";
  private static final String ERROR_MESSAGE_INVALID_DATE_OF_BIRTH = "Invalid date of birth format";
  private static final String ERROR_MESSAGE_INVALID_GENDER = "Invalid gender format";

  // Instance fields
  private final String fullname;
  private final Optional<Gender> gender;
  private final Optional<LocalDate> dateOfBirth;

  /**
   * Construction for the data type {@link Person}.<br>
   * <br>
   * A valid CVS line can have any number of values, as far as the line is not blank.<br>
   * <br>
   * <ul>
   * <li>The first value can be any arbitrary string, and will represent the person full name;</li>
   * <li>The second value can be a value among 'Male', 'Female' or blank string, and will represent the gender;</li>
   * <li>The first value can be any string representing the sate with the format dd/mm/yyyy, and will represent the date of birth.</li>
   * 
   * @param csvLine a life of a csv file
   * @throws DateTimeParseException if date format is wrong
   * @throws IllegalArgumentException if csv line is blank or gender format is not correct
   */
  public Person(String csvLine) throws DateTimeParseException, IllegalArgumentException {
    if (StringUtils.isBlank(csvLine))
      throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_CSV_FORMAT);
    String[] csvRecords = csvLine.split(CSV_SEPARATOR);

    // Setting person full name
    fullname = csvRecords[0];

    // Setting person gender
    if (csvRecords.length > 1 && !StringUtils.isBlank(csvRecords[1].trim())) {
      // Validation
      if (!Arrays.stream(VALID_VALUES_GENDER).anyMatch(csvRecords[1].trim()::equals))
        throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_GENDER);
      // Setting value...
      gender = Optional.of(Gender.valueOf(csvRecords[1].trim().toUpperCase()));
    } else {
      gender = Optional.empty();
    }

    // Setting person date of birth
    if (csvRecords.length > 2 && !StringUtils.isBlank(csvRecords[2])) {
      try {
        dateOfBirth = Optional.of(LocalDate.parse(csvRecords[2].trim(), DATE_FORMAT));
      } catch (DateTimeParseException e) {
        throw new DateTimeParseException(ERROR_MESSAGE_INVALID_DATE_OF_BIRTH, e.getParsedString(), e.getErrorIndex(),
            e);
      }
    } else {
      dateOfBirth = Optional.empty();
    }
  }

  public String getFullname() {
    return this.fullname;
  }

  public Optional<Gender> getGender() {
    return this.gender;
  }

  public Optional<LocalDate> getDateOfBirth() {
    return this.dateOfBirth;
  }

  public enum Gender {
    FEMALE, MALE
  }
}