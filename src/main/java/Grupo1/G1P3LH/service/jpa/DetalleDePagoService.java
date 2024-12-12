package Grupo1.G1P3LH.service.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.G1P3LH.entity.DetalleDePago;
import Grupo1.G1P3LH.entity.Venta;
import Grupo1.G1P3LH.repository.DetalleDePagoRepository;
import Grupo1.G1P3LH.repository.VentaRepository;
import Grupo1.G1P3LH.util.ResourceNotFoundException;

@Service
public class DetalleDePagoService {

    @Autowired
    private DetalleDePagoRepository detalleDePagoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public DetalleDePago createDetalleDePago(DetalleDePago detalle) {
        // Validar que la venta exista completada
        Venta venta = ventaRepository.findById(detalle.getVenta().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada"));

        if (!"completado".equalsIgnoreCase(venta.getEstado())) {
            throw new IllegalArgumentException("No se puede agregar el detalle. La venta no está completada.");
        }

        // Validar que no exista otro detalle con el mismo método de pago y estado
        Optional<DetalleDePago> existente = detalleDePagoRepository.findByVentaIdYMetodoDePagoYEstadoPago(
                detalle.getVenta().getId(), detalle.getMetodoDePago(), detalle.getEstadoPago());

        if (existente.isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un detalle de pago con el mismo método y estado para esta venta.");
        }

        // Guardar el detalle de pago
        DetalleDePago nuevoDetalle = detalleDePagoRepository.save(detalle);

        // Log de pago pendiente
        if ("pendiente".equalsIgnoreCase(nuevoDetalle.getEstadoPago())) {
            System.out.println("Se ha creado un pago pendiente: " + nuevoDetalle.getId() + ", "
                    + nuevoDetalle.getMetodoDePago());
        }

        return nuevoDetalle;
    }
}

