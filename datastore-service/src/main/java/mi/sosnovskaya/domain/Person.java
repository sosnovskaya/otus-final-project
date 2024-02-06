package mi.sosnovskaya.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import reactor.util.annotation.NonNull;

@Getter
@ToString
@Table("person")
public class Person {
    @Id
    private final Long id;

    @NonNull
    private final String name;

    @PersistenceCreator
    public Person(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Person(@NonNull String name) {
        this(null, name);
    }

}
