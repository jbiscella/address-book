package com.gumtree.addressbook.data;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

@FunctionalInterface
public interface DataReader{
  /**
   * Returns a list of csv file lines from a data source filtered through a {@link Predicate} passed as argument.<br>
   * <br>
   * The scope of this method is the one to filter only the requires csv lines to save memory.
   * 
   * @param filterCondition
   * @return
   */
  public List<String> readFilteredData(Predicate<String> filterCondition) throws IllegalArgumentException, IOException; //NOSONAR

  /**
   * Returns a list of all the csv file lines from a data source.
   * 
   * @return
   */
  public default List<String> readAllData() throws IOException{
    return readFilteredData(line -> true);
  }
}