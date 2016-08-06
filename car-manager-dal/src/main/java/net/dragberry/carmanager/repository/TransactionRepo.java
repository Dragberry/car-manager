package net.dragberry.carmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.domain.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
