package repository;

import domain.Entity;
import exception.RepositoryException;
import exception.ValidationException;
import validation.Validator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>>
        extends InMemoryRepository<ID, E> {

    private final Path file;

    public AbstractFileRepository(Path file, Validator<E> validator) {
        super(validator);
        if (file == null) {
            throw new IllegalArgumentException("File path cannot be null.");
        }
        this.file = file;
        loadData();
    }

    protected abstract E extractEntity(String line);

    protected abstract String createEntityAsString(E entity);

    private void loadData() {
        if (!Files.exists(file)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    super.save(extractEntity(line));
                } catch (IllegalArgumentException | DateTimeParseException | ValidationException e) {
                    System.err.println("Ignoring invalid line " + lineNumber + " in " + file
                            + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RepositoryException("Could not read file " + file, e);
        }
    }

    private void writeData() {
        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                for (E entity : getAll()) {
                    writer.write(createEntityAsString(entity));
                    writer.newLine();
                }
            }
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
