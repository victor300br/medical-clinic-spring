package br.uece.clinic.api.response.dto;

import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
public class AvailableTimesResponseDTO {
    private List<LocalTime> availableTimes;
    private boolean hasAvailability;

    public AvailableTimesResponseDTO(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
        this.hasAvailability = !availableTimes.isEmpty();
    }
}