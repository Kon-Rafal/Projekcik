package recruitment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDTO {
  int flightNumber;
  String departureDate;
  double cargoWeight;
  double baggageWeight;
  double totalWeight;

  public FlightDTO(int flightNumber, String departureDate) {
    this.flightNumber = flightNumber;
    this.departureDate = departureDate;
    this.cargoWeight = 0;
    this.baggageWeight = 0;
    this.totalWeight = 0;
  }
}
