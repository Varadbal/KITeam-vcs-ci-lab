package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.TrainSensor;
import org.mockito.Mockito;

public class TrainSensorTest {

    TrainController mockController;
    TrainUser mockUser;

    TrainSensor ts;

    @Before
    public void before() {
        mockController = mock(TrainController.class);
        mockUser = mock(TrainUser.class);

        ts = new TrainSensorImpl(mockController, mockUser);
    }

    @Test
    public void negativeNewSpeed() {

        ts.overrideSpeedLimit(-10);

        verify(mockUser).setAlarmState(true);
    }

    @Test
    public void hugeNewSpeed() {

        ts.overrideSpeedLimit(1000);

        verify(mockUser).setAlarmState(true);
    }

    @Test
    public void relativelyTooSmallNewSpeed() {
        when(mockController.getReferenceSpeed()).thenReturn(150);

        ts.overrideSpeedLimit(50);

        verify(mockUser).setAlarmState(true);
    }

    @Test
    public void normalNewSpeed() {

        when(mockController.getReferenceSpeed()).thenReturn(0);

        ts.overrideSpeedLimit(5);

        verify(mockUser).setAlarmState(false);
        verify(mockController).getReferenceSpeed();
    }


}
