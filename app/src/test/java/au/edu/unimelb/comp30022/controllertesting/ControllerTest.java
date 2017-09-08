package au.edu.unimelb.comp30022.controllertesting;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import android.location.Location;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by William on 31/8/17, modified by Brodie on 02/09/2017 and 08/09/2017
 */

/* Unit Testing */
@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    AddressTools mock_AddressTools = Mockito.mock(AddressTools.class);
    private PostcodeValidator mock_PostcodeValidator =
            Mockito.mock(PostcodeValidator.class);
    private PostageRateCalculator mock_PostageRateCalculator =
            Mockito.mock(PostageRateCalculator.class);
    private Address mock_Address = Mockito.mock(Address.class);
    private Location mock_Location = Mockito.mock(Location.class);

    // The class to be tested
    private Controller controller;

    @Before
    public void setUp() throws Exception {

        // Add widgets for Controller to use
        UI.addWidget("SOURCE_POST_CODE", new EditText());
        UI.addWidget("DESTINATION_POST_CODE", new EditText());
        UI.addWidget("COST_LABEL", new TextView());

        controller = new Controller(mock_AddressTools,
                mock_PostcodeValidator, mock_PostageRateCalculator);

        // Set mocks for other classes, assume they always return correct values
        Mockito.when(mock_AddressTools.locationFromAddress(any(Address.class)))
                .thenReturn(mock_Location);
        Mockito.when(mock_PostcodeValidator.isValid("true")).thenReturn(true);
        Mockito.when(mock_PostcodeValidator.isValid("false")).thenReturn(false);
        Mockito.when(mock_PostageRateCalculator.computeCost(
                any(Location.class), any(Location.class))).thenReturn(5);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void buttonPressed_TrueTrue() {
        controller.sourcePostCodeField.setText("true");
        controller.destinationPostCodeField.setText("true");
        controller.calculateButtonPressed();
        assertEquals("$5", controller.costLabel.getText());
    }

    @Test
    public void buttonPressed_TrueFalse() {
        controller.sourcePostCodeField.setText("true");
        controller.destinationPostCodeField.setText("false");
        controller.calculateButtonPressed();
        assertEquals(null, controller.costLabel.getText());
    }

    @Test
    public void buttonPressed_FalseTrue() {
        controller.sourcePostCodeField.setText("false");
        controller.destinationPostCodeField.setText("true");
        controller.calculateButtonPressed();
        assertEquals(null, controller.costLabel.getText());
    }

    @Test
    public void buttonPressed_FalseFalse() {
        controller.sourcePostCodeField.setText("false");
        controller.destinationPostCodeField.setText("false");
        controller.calculateButtonPressed();
        assertEquals(null, controller.costLabel.getText());
    }
}