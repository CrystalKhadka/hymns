package com.hymns.hymns.repository;

import com.hymns.hymns.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepo extends JpaRepository<Instrument, Integer> {
    @Modifying
    @Query("update Instrument i set i.instrumentRentalStatus = ?1 where i.instrumentId = ?2")
    void updateInstrumentStatus(String rented, int instrument);
}
