package net.dragberry.carmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.domain.TransactionType;

@Repository
public interface TransactionTypeRepo extends JpaRepository<TransactionType, Long> {

}
