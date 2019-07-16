package com.example.myapplication.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.db.entity.Patients;

import java.util.List;



import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface PersonDao {


    @Query("SELECT * FROM patients ORDER BY name ASC")
    LiveData<List<Patients>> findAllPersons();

    @Query("SELECT * FROM patients")
    List<Patients> getAllChannels();

    @Query("SELECT * FROM patients WHERE id=:id")
    Patients findPersonById(String id);

    @Query("SELECT * FROM patients WHERE id=:id")
    Patients findPerson(long id);

    @Insert(onConflict = IGNORE)
    long insertPerson(Patients person);

    @Update
    int updatePerson(Patients person);

    @Update
    void updatePerson(List<Patients> people);

    @Delete
    void deletePerson(Patients person);

    @Query("DELETE FROM patients")
    void deleteAll();
}
