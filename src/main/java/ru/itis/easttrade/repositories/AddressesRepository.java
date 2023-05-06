package ru.itis.easttrade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.easttrade.models.Address;

@Repository
public interface AddressesRepository extends JpaRepository<Address, Integer> {

}
