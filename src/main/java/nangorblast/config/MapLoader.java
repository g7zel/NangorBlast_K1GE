/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import nangorblast.exception.InvalidMapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MapLoader {
    private static final Logger LOG = LoggerFactory.getLogger(MapLoader.class);
    private final MapValidator validator;
    public MapLoader() { this(new MapValidator()); }
    public MapLoader(MapValidator validator) { this.validator = Objects.requireNonNull(validator); }
    public MapData load(Path path) throws InvalidMapException {
        Objects.requireNonNull(path);
        final List<String> lines;
        try { lines = Files.readAllLines(path, StandardCharsets.UTF_8); }
        catch (IOException e) {
            LOG.warn("Gagal membaca map {}", path, e);
            throw new InvalidMapException("Map tidak dapat dibaca: " + path, e);
        }
        MapData data = new MapData(lines);
        validator.validate(data);
        LOG.info("Map valid dimuat dari {}", path);
        return data;
    }
}
