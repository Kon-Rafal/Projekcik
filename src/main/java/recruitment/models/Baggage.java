package recruitment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Baggage {
  int id;
  int weight;
  String weightUnit;
  int pieces;
}
