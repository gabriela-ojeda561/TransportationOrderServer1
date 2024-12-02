package es.upm.dit.apsv.transportationorderserver.repository;

import es.upm.dit.apsv.transportationorderserver.model.TransportationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportationOrderRepository extends JpaRepository<TransportationOrder, String> {
   
    // Puedes agregar consultas personalizadas aquí si lo necesitas
    Optional<TransportationOrder> findByTruck(String truck);
   
    // O cualquier otra consulta que puedas necesitar, por ejemplo:
    Optional<TransportationOrder> findByToidAndSt(String toid, int st);
   
}