package ru.itis.easttrade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    List<Product> findAllByAccount(Account account);
    List<Product> findAllByOrderByPublishDateDesc();
    List<Product> findAllByOrderByPublishDateAsc();
    @Query(value = "SELECT * FROM products WHERE date(publish_date) = CURRENT_DATE",nativeQuery = true)
    List<Product> findAllForToday();
    @Modifying
    @Query("UPDATE Product a SET a.name = :newName, a.description= :newDescription, a.price= :newPrice WHERE a.id = :id")
    void updateById(@Param("id") Integer id, @Param("newName") String newName, @Param("newDescription") String newDescription, @Param("newPrice") Double  price);

}
