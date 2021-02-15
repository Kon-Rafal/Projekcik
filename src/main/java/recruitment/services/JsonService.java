package recruitment.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import recruitment.models.AirportDTO;
import recruitment.models.CargoEntity;
import recruitment.models.FlightDTO;
import recruitment.models.FlightEntity;

@Service
public class JsonService {

  private final ArrayList<CargoEntity> cargoEntities;
  private final ArrayList<FlightEntity> flightEntities;

  public JsonService() {
    this.flightEntities = loadFlightEntities(FlightEntity.resourcesFile);
    this.cargoEntities = loadCargoEntities(CargoEntity.resourcesFile);
  }

  public Optional<FlightDTO> getFlightDetails(int flightNumber, String departureDate) {
    Optional<FlightEntity> flight = flightEntities.stream()
        .filter(flightEntity -> flightEntity.getFlightNumber() == flightNumber &&
            flightEntity.getDepartureDate().equals(departureDate))
        .findFirst();
    if (flight.isPresent()) {
      List<CargoEntity> entities = cargoEntities.stream()
          .filter(cargoEntity -> cargoEntity.getFlightId() == flight.get().getFlightId()).collect(Collectors.toList());

      FlightDTO flightDTO = new FlightDTO(flightNumber, departureDate);

      for (CargoEntity flightCargo : entities) {
        flightDTO.setBaggageWeight(flightDTO.getBaggageWeight() + flightCargo.getBaggage().stream()
            .mapToDouble(baggage -> convertWeight(baggage.getWeight(), baggage.getWeightUnit())).sum());

        flightDTO.setCargoWeight(flightDTO.getCargoWeight() + flightCargo.getCargo().stream()
            .mapToDouble(cargo -> convertWeight(cargo.getWeight(), cargo.getWeightUnit())).sum());

        flightDTO.setTotalWeight(flightDTO.getBaggageWeight() + flightDTO.getCargoWeight());
      }
      return Optional.of(flightDTO);
    } else {
      return Optional.empty();
    }
  }


  public Optional<AirportDTO> getAirportDetails(String airportCode, String date) {
    List<FlightEntity> flights = flightEntities.stream()
        .filter(flightEntity -> flightEntity.getDepartureDate().equals(date) &&
            (flightEntity.getArrivalAirportIATACode().equals(airportCode)
                || flightEntity.getDepartureAirportIATACode().equals(airportCode)))
        .collect(Collectors.toList());

    AirportDTO airportDTO = new AirportDTO(airportCode, date);
    for(FlightEntity flight: flights) {
      List<CargoEntity> flightCargos = cargoEntities.stream()
          .filter(cargoEntity -> cargoEntity.getFlightId() == flight.getFlightId()).collect(Collectors.toList());

      if (flight.getDepartureAirportIATACode().equals(airportCode)) {
        airportDTO.setFlightsDeparting(airportDTO.getFlightsDeparting()+1);
        for (CargoEntity flightCargo : flightCargos) {
          airportDTO.setBaggageDeparting(airportDTO.getBaggageDeparting() + flightCargo.getBaggage().stream()
              .mapToDouble(baggage -> convertWeight(baggage.getWeight(), baggage.getWeightUnit())).sum());
        }
      } else {
        airportDTO.setFlightsArriving(airportDTO.getFlightsArriving()+1);
        for (CargoEntity flightCargo : flightCargos) {
          airportDTO.setBaggageArriving(airportDTO.getBaggageArriving() + flightCargo.getBaggage().stream()
              .mapToDouble(baggage -> convertWeight(baggage.getWeight(), baggage.getWeightUnit())).sum());
        }
      }
    }
    return Optional.of(airportDTO);
  }

  private double convertWeight(int weight, String weightUnit) {
    if (weightUnit.equals("lb")) {
      return weight * 0.453593237;
    } else {
      return weight;
    }
  }

  private ArrayList<CargoEntity> loadCargoEntities(String fileName) {
    try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(in, new TypeReference<>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  private ArrayList<FlightEntity> loadFlightEntities(String fileName) {
    try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(in, new TypeReference<>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

}
