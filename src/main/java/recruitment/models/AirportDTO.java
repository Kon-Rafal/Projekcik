package recruitment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportDTO {

  String airportCode;
  String date;
  int flightsDeparting;
  int flightsArriving;
  double baggageArriving;
  double baggageDeparting;

  public AirportDTO(String airportCode, String date) {
    this.airportCode = airportCode;
    this.date = date;
    this.flightsDeparting = 0;
    this.flightsArriving = 0;
    this.baggageArriving = 0;
    this.baggageDeparting = 0;
  }
}
