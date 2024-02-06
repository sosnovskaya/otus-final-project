package mi.sosnovskaya.domain;

import java.time.LocalDateTime;

public record TaskDto(Long id, String name, String description, Long personId, LocalDateTime startTime, LocalDateTime endTime) {
}
