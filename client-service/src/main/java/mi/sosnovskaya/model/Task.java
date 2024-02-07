package mi.sosnovskaya.model;

import java.time.LocalDateTime;

public record Task(Long id, String name, String description, Long personId, LocalDateTime startTime, LocalDateTime endTime) {
}
