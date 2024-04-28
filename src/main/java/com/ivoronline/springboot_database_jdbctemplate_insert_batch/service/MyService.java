package com.ivoronline.springboot_database_jdbctemplate_insert_batch.service;

import com.ivoronline.springboot_database_jdbctemplate_insert_batch.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class MyService {

  //PROPERTIES
  @Autowired private JdbcTemplate jdbcTemplate;

  //=========================================================================================================
  // INSERT
  //=========================================================================================================
  public int[] insert(List<PersonDTO> records) {

    return jdbcTemplate.batchUpdate(

      "INSERT INTO PERSON (NAME, AGE) VALUES (?, ?)",        //Order of parameters is used

      new BatchPreparedStatementSetter() {

        @Override
        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
          preparedStatement.setString(1, records.get(i).getName());
          preparedStatement.setLong  (2, records.get(i).getAge ());
        }

        @Override
        public int getBatchSize() {
          return records.size();
        }

      }

    );

  }

  //=========================================================================================================
  // INSERT WITH BATCH SIZE
  //=========================================================================================================
  public int[][] insertWithBatchSize(List<PersonDTO> records) {

    return jdbcTemplate.batchUpdate(
      "INSERT INTO PERSON (NAME, AGE) VALUES (?, ?)",
      records,
      2,    //Batch size
      (PreparedStatement preparedStatement, PersonDTO record) -> {
          preparedStatement.setString(1, record.getName());
          preparedStatement.setLong  (2, record.getAge ());
      }
    );

  }

}
