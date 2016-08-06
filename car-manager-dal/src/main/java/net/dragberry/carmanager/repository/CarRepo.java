package net.dragberry.carmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.domain.Car;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

}
