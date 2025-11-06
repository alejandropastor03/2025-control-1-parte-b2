package es.upm.grise.profundizacion.cruiseControl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.exceptions.IncorrectSpeedSetException;
import es.upm.grise.profundizacion.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {

    private CruiseControl cruiseControl;
    private Speedometer speedometerMock;

    @BeforeEach
    void setUp() {
        speedometerMock = mock(Speedometer.class);
        cruiseControl = new CruiseControl(speedometerMock);
    }

    @Test
    void velocidadCeroONegativaDebeLanzarExcepcion() {
        assertAll(
            () -> assertThrows(IncorrectSpeedSetException.class, () -> cruiseControl.setSpeedSet(0)),
            () -> assertThrows(IncorrectSpeedSetException.class, () -> cruiseControl.setSpeedSet(-10))
        );
    }

    @Test
    void establecerVelocidadSinLimiteDebeFuncionar() throws Exception {
        cruiseControl.setSpeedSet(80);

        assertEquals(80, cruiseControl.getSpeedSet());
    }

    @Test
    void establecerVelocidadDentroDelLimiteDebeFuncionar() throws Exception {
        cruiseControl.setSpeedLimit(100);

        cruiseControl.setSpeedSet(90);

        assertEquals(90, cruiseControl.getSpeedSet());
    }

    @Test
    void velocidadPorEncimaDelLimiteDebeLanzarExcepcion() {
        cruiseControl.setSpeedLimit(100);

        assertThrows(SpeedSetAboveSpeedLimitException.class, () -> cruiseControl.setSpeedSet(120));
    }

    @Test
    void debePermitirCambiarLimiteDeVelocidad() {
        cruiseControl.setSpeedLimit(120);

        assertEquals(120, cruiseControl.getSpeedLimit());
    }
}