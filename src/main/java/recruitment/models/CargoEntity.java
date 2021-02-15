package recruitment.models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoEntity {
  public static String resourcesFile = "cargoEntity.json";

  int flightId;
  ArrayList<Baggage> baggage;
  ArrayList<Baggage> cargo;
}
