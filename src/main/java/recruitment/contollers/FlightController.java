package recruitment.contollers;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recruitment.models.FlightDTO;
import recruitment.services.JsonService;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {

  private final JsonService jsonService;

  @GetMapping()
  public ResponseEntity<?> getFlightDetails(@RequestParam int flightNumber, @RequestParam String departureDate) {
    Optional<FlightDTO> flightDetails = jsonService.getFlightDetails(flightNumber, departureDate);
    if(flightDetails.isPresent()) {
      return ResponseEntity.ok(flightDetails.get());
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Found");
    }
  }
}
