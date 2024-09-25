package com.hymns.hymns.repository;

import com.hymns.hymns.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepo extends JpaRepository<Instrument, Integer> {
    @Query(value = "select * from instruments where added = true", nativeQuery = true)
    List<Instrument> getAddedInstruments();

    @Query(value = "select * from instruments where added = false", nativeQuery = true)
    List<Instrument> getNotAddedInstruments();


}
