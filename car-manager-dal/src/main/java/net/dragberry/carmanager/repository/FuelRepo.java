package net.dragberry.carmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.domain.Fuel;

@Repository
public interface FuelRepo extends JpaRepository<Fuel, Long>{

}
