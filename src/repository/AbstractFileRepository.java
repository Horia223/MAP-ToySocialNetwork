package repository;

import domain.Entity;
import exception.RepositoryException;
import exception.ValidationException;
import validation.Validator;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>>
        extends InMemoryRepository<ID, E> {


    private final Path file;



    public AbstractFileRepository(Path file, Validator<E> validator) {
        super(validator);
        if (file == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        this.file = file;
        loadData();
    }
    //Receives a line and creates the entity
    protected abstract E extractEntity(String line);
    //Receives an entity and creates the text that will be written in the file
    protected abstract String createEntityAsString(E entity);

    private void loadData() {
        if (!Files.exists(file)) {
            return;
        }

        try {
            List<String> lines;

            try (Stream<String> lineStream =
                         Files.lines(file, StandardCharsets.UTF_8)) {
                lines = lineStream.collect(Collectors.toList());
            }

            IntStream.range(0, lines.size()).forEach(index -> {
                String line = lines.get(index);

                try {
                    super.save(extractEntity(line));
                } catch (IllegalArgumentException | DateTimeParseException | ValidationException e) {
                    System.err.println(
                            "Ignoring line " + (index + 1) + " in " + file + " because: " + e.getMessage()
                    );
                }
            });
        } catch (IOException | UncheckedIOException e) {
            throw new RepositoryException("Could not read file " + file, e);
        }
    }

    private void writeData() {

        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = getAll().stream()
                    .map(this::createEntityAsString)
                    .collect(Collectors.toList());

            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RepositoryException("Could not write file " + file, e);
        }


    }

    @Override
    public E save(E entity) {
        E saved = super.save(entity);
        writeData();
        return saved;
    }

    @Override
    public E delete(ID id) {
        E deleted = super.delete(id);
        writeData();
        return deleted;
    }


    @Override
    public E update(E entity) {
        E updated = super.update(entity);
        writeData();
        return updated;
    }
}
