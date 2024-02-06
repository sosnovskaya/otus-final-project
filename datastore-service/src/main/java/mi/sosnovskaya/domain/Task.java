package mi.sosnovskaya.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import reactor.util.annotation.NonNull;

import java.time.LocalDateTime;

@Getter
@ToString
@Table("task")
@Setter
public class Task {
    @Id
    private final Long id;

    @NonNull
    private final String name;

    private final String description;

    @NonNull
    private final Long personId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @PersistenceCreator
    public Task(Long id, @NonNull String name, String description, @NonNull Long personId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.personId = personId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Task(@NonNull String name, String description, @NonNull Long personId, LocalDateTime startTime, LocalDateTime endTime) {
        this(null, name, description, personId, startTime, endTime);
    }
}
