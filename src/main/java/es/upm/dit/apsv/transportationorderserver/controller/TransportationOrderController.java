package es.upm.dit.apsv.transportationorderserver.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.dit.apsv.transportationorderserver.repository.TransportationOrderRepository;
import es.upm.dit.apsv.transportationorderserver.model.TransportationOrder;

@RestController
@RequestMapping("/transportationorders") // Endpoint base para todas las operaciones
public class TransportationOrderController {

    private final TransportationOrderRepository repositorio;
    private static final Logger log = LoggerFactory.getLogger(TransportationOrderController.class);

    // Constructor donde se inyecta el repositorio
    public TransportationOrderController(TransportationOrderRepository repositorio) {
        this.repositorio = repositorio;
    }

    // Obtener todas las órdenes de transporte
    @GetMapping
    public List<TransportationOrder> all() {
        log.info("Solicitud GET para obtener todas las órdenes de transporte");
        return (List<TransportationOrder>) repositorio.findAll();
    }

    // Crear una nueva orden de transporte
    @PostMapping
    public TransportationOrder newOrder(@RequestBody TransportationOrder newOrder) {
        log.info("Solicitud POST para crear una nueva orden de transporte: {}", newOrder);
        return repositorio.save(newOrder);
    }

    // Obtener una orden de transporte por camión
    @GetMapping("/{truck}")
    public ResponseEntity<TransportationOrder> getByTruck(@PathVariable String truck) {
        log.info("Solicitud GET para obtener la orden de transporte del camión: {}", truck);
        Optional<TransportationOrder> ot = repositorio.findByTruck(truck);
        return ot.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar una orden de transporte
    @PutMapping
    public ResponseEntity<TransportationOrder> update(@RequestBody TransportationOrder updatedOrder) {
        log.info("Solicitud PUT para actualizar una orden de transporte: {}", updatedOrder);
        TransportationOrder updated = repositorio.save(updatedOrder);
        return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK) 
                               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar una orden de transporte por camión
    @DeleteMapping("/{truck}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String truck) {
        log.info("Solicitud DELETE para eliminar la orden de transporte del camión: {}", truck);
        if (repositorio.existsById(truck)) {
            repositorio.deleteById(truck);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
