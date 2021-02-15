package recruitment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightEntity {
  public static String resourcesFile = "flightEntity.json";

  int flightId;
  int flightNumber;
  String departureAirportIATACode;
  String arrivalAirportIATACode;
  String departureDate;
}
