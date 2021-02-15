package recruitment.contollers;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recruitment.models.AirportDTO;
import recruitment.services.JsonService;

@RestController
@RequestMapping("/airport-code")
@RequiredArgsConstructor
public class IATAAirportCodeController {

  private JsonService jsonService;

  @GetMapping()
  public ResponseEntity<?> getAirportDetails(@RequestParam String airportCode, @RequestParam String date) {
    Optional<AirportDTO> airportDetails = jsonService.getAirportDetails(airportCode, date);
    if(airportDetails.isPresent()) {
      return ResponseEntity.ok(airportDetails.get());
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Found");
    }
  }
}
