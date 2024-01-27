package stubs.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class InMemoryDatabase<T> {

    Map<UUID, T> entries;

    public InMemoryDatabase() {
        entries = new HashMap<>();
    }

    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(entries.get(id));

    }

    public void save(UUID id, T data) {
        if(entries.containsKey(id)) {
            entries.replace(id, data);
        } else {
            entries.put(id, data);
        }
    }

}
