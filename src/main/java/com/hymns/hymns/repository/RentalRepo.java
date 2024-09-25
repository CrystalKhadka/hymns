package com.hymns.hymns.repository;

import com.hymns.hymns.entity.Rental;
import com.hymns.hymns.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepo extends JpaRepository<Rental, Integer> {
    @Query("SELECT r FROM Rental r WHERE r.user = ?1")
    List<Rental> findAllByUser(User user);
}
