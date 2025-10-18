package flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FlightSearch {

    private String departureDate;
    private String departureAirportCode;
    private boolean emergencyRowSeating;
    private String returnDate;
    private String destinationAirportCode;
    private String seatingClass;
    private int adultPassengerCount;
    private int childPassengerCount;
    private int infantPassengerCount;

    private static final List<String> VALID_AIRPORTS = Arrays.asList("syd", "mel", "lax", "cdg", "del", "pvg", "doh");
    private static final List<String> VALID_CLASSES = Arrays.asList("economy", "premium economy", "business", "first");

    public boolean runFlightSearch(String departureDate, String departureAirportCode, boolean emergencyRowSeating,
                                   String returnDate, String destinationAirportCode, String seatingClass,
                                   int adultPassengerCount, int childPassengerCount, int infantPassengerCount) {
        boolean valid = true;

        // Condition 1: Total passengers between 1 and 9
        int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount;
        if (totalPassengers < 1 || totalPassengers > 9) return false;

        // Condition 2: Children cannot sit in emergency row or first class
        if ((childPassengerCount > 0) && (emergencyRowSeating || seatingClass.equals("first"))) return false;

        // Condition 3: Infants cannot sit in emergency row or business class
        if ((infantPassengerCount > 0) && (emergencyRowSeating || seatingClass.equals("business"))) return false;

        // Condition 4: Children ≤ adults * 2
        if (childPassengerCount > adultPassengerCount * 2) return false;

        // Condition 5: Infants ≤ adults * 1
        if (infantPassengerCount > adultPassengerCount) return false;

        // Condition 6 & 7: Date validation
        if (!isValidDate(departureDate) || !isValidDate(returnDate)) return false;
        if (isPastDate(departureDate)) return false;

        // Condition 8: Return date must be after departure
        if (!isReturnAfterDeparture(departureDate, returnDate)) return false;

        // Condition 9: Seating class must be valid
        if (!VALID_CLASSES.contains(seatingClass)) return false;

        // Condition 10: Only economy can have emergency row
        if (emergencyRowSeating && !seatingClass.equals("economy")) return false;

        // Condition 11: Valid airports and not the same
        if (!VALID_AIRPORTS.contains(departureAirportCode) ||
            !VALID_AIRPORTS.contains(destinationAirportCode) ||
            departureAirportCode.equals(destinationAirportCode)) return false;

        // If valid, initialize all attributes
        this.departureDate = departureDate;
        this.departureAirportCode = departureAirportCode;
        this.emergencyRowSeating = emergencyRowSeating;
        this.returnDate = returnDate;
        this.destinationAirportCode = destinationAirportCode;
        this.seatingClass = seatingClass;
        this.adultPassengerCount = adultPassengerCount;
        this.childPassengerCount = childPassengerCount;
        this.infantPassengerCount = infantPassengerCount;

        return valid;
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isPastDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateStr);
            return date.before(new Date());
        } catch (ParseException e) {
            return true;
        }
    }

    private boolean isReturnAfterDeparture(String dep, String ret) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = sdf.parse(dep);
            Date d2 = sdf.parse(ret);
            return !d2.before(d1);
        } catch (ParseException e) {
            return false;
        }
    }

    // Getters (for unit testing)
    public String getDepartureDate() { return departureDate; }
    public String getDepartureAirportCode() { return departureAirportCode; }
    public boolean isEmergencyRowSeating() { return emergencyRowSeating; }
    public String getReturnDate() { return returnDate; }
    public String getDestinationAirportCode() { return destinationAirportCode; }
    public String getSeatingClass() { return seatingClass; }
    public int getAdultPassengerCount() { return adultPassengerCount; }
    public int getChildPassengerCount() { return childPassengerCount; }
    public int getInfantPassengerCount() { return infantPassengerCount; }
}
