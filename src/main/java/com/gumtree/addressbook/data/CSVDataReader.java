package com.gumtree.addressbook.data;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An implementation of {@link DataReader} reading the information from a comma
 * separated file.
 */
public class CSVDataReader implements DataReader {

  // Error messages
  private static final String ERROR_MESSAGE_INVALID_FILTER = "Missing filter Predicate";

  // The csv file used as datasource
  private final URI fileName;

  /**
   * Constructor for {@link CSVDataReader}
   * @param fileName the {@link URI} of the csv file used as data source
   */
  public CSVDataReader(URI fileName) {
    this.fileName = fileName;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<String> readFilteredData(Predicate<String> filterCondition) throws IllegalArgumentException, IOException { // NOSONAR
    if (filterCondition == null) {
      throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_FILTER);
    }
    try (Stream<String> csvLinesStream = Files.lines(Paths.get(fileName))) {
      return csvLinesStream.filter(filterCondition)
                           .collect(Collectors.toList());
    } catch (IOException e) {
      throw new IOException("Error reading data from CSV file ", e);
    }
  }
}