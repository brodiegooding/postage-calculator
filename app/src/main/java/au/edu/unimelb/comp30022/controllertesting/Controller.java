package au.edu.unimelb.comp30022.controllertesting;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a controller for a simple view that allows the user to input two
 * post codes and generate an appropriate freight cost to deliver goods from
 * one to the other.
 * The user should enter data in to both text fields, then press the calculate
 * button. The result should be displayed in a text label.
 */
public class Controller {
    
    // Button that is pressed to do the calculation.
    Button calculateButton = (Button) UI.getElementById("CALCULATE_BUTTON");

    // The source and destination post code for the freight.
    EditText sourcePostCodeField =
        (EditText) UI.getElementById("SOURCE_POST_CODE");
    EditText destinationPostCodeField =
        (EditText) UI.getElementById("DESTINATION_POST_CODE");

    // Label that displays the cost of the freight to the user.
    TextView costLabel = (TextView) UI.getElementById("COST_LABEL");

    // List of all valid post codes, used during validation.
    List<String> validPostCodes = new ArrayList<String>();

    // Reference to provided tools for dealing with addresses.
    AddressTools addressTools;

    // Reference to provided tools for validating postcodes
    PostcodeValidator postcodeValidator;

    // Reference to provided tools for computing distance-based postage costs
    PostageRateCalculator postageRateCalculator;


    public Controller(AddressTools addressTools, PostcodeValidator postcodeValidator, PostageRateCalculator postageRateCalculator) {
        this.addressTools = addressTools;
        this.postcodeValidator = postcodeValidator;
        this.postageRateCalculator = postageRateCalculator;
    }

    /** 
     * Called when the user presses the calculate button. Should verify that
     * the post codes are valid and then compute the cost to freight between
     * them.
     */
    public void calculateButtonPressed() {

        if (!this.postcodeValidator.isValid(this.sourcePostCodeField.getText())) {
            new Alert("Source post code isn't valid.").show();
            return;
        }

        // This is the bug: it checks that the source is valid a second
        // time, rather than checking if the destination is valid
        // if (!this.postcodeValidator.isValid(this.destinationPostCodeField.getText())) {
        if (!this.postcodeValidator.isValid(this.sourcePostCodeField.getText())) {
            new Alert("Destination post code isn't valid.").show();
            return;
        }

        Location sourceLocation = this.addressTools.locationFromAddress(
            Address.fromPostCode(this.sourcePostCodeField.getText()));
        Location destinationLocation = this.addressTools.locationFromAddress(
            Address.fromPostCode(this.destinationPostCodeField.getText()));

        int cost = postageRateCalculator.computeCost(sourceLocation, destinationLocation);

        costLabel.setText("$" + cost);
    }

}