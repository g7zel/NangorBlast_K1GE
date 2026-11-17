/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import nangorblast.exception.InvalidMapException;
import nangorblast.model.map.GameMap;
import nangorblast.model.map.TileType;
import nangorblast.model.powerup.PowerUpService;
import nangorblast.model.powerup.PowerUpType;
import nangorblast.util.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** Menyiapkan sesi baru sebelum engine beralih PLAYING; tidak memutasi sesi lama. */
public final class GameInitializer<P, E, B> {
    private static final Logger LOG = LoggerFactory.getLogger(GameInitializer.class);
    private final MapData data;
    private final EntityFactory<P, E, B> factory;
    private final BiConsumer<P, PowerUpType> effects;
    private final Runnable collectedEvent;
    private final Supplier<RandomGenerator> random;
    public GameInitializer(Path path, EntityFactory<P, E, B> factory, BiConsumer<P, PowerUpType> effects,
                           Runnable collectedEvent, Supplier<RandomGenerator> random) throws InvalidMapException {
        data = new MapLoader().load(path);
        this.factory = Objects.requireNonNull(factory); this.effects = Objects.requireNonNull(effects);
        this.collectedEvent = Objects.requireNonNull(collectedEvent); this.random = Objects.requireNonNull(random);
    }
    public InitializedGame<P, E, B> initialize() {
        GameMap map = new GameMap(data);
        P player = Objects.requireNonNull(factory.createPlayer(data.playerSpawn(), PlayerDefaults.standard()));
        List<E> enemies = new ArrayList<>();
        Set<Object> identities = Collections.newSetFromMap(new IdentityHashMap<>());
        identities.add(player);
        int index = 0;
        for (Position spawn : data.enemySpawns()) {
            E enemy = Objects.requireNonNull(factory.createNormalEnemy(spawn, index++));
            if (!identities.add(enemy)) throw new IllegalStateException("Factory memakai ulang instance entity");
            enemies.add(enemy);
        }
        B boss = Objects.requireNonNull(factory.createMantis(data.bossSpawn()));
        if (!identities.add(boss)) throw new IllegalStateException("Factory memakai ulang instance boss");
        for (int r = 0; r < map.height(); r++) for (int c = 0; c < map.width(); c++) {
            Position p = new Position(r, c); TileType type = map.tileAt(p).getType();
            if (type == TileType.WALL || type == TileType.DESTRUCTIBLE)
                map.tileAt(p).attachBlock(Objects.requireNonNull(factory.createBlock(p, type)));
        }
        PowerUpService items = new PowerUpService(map, random.get(), type -> effects.accept(player, type), collectedEvent);
        items.ensureGuaranteedRemote();
        LOG.info("Sesi White Glacier siap: normalEnemy={}, boss=Mantis", enemies.size());
        return new InitializedGame<>(data, map, player, enemies, boss, items);
    }
    /** Blueprint awal dipakai lagi; seluruh objek runtime, item dan hasil undian dibuat baru. */
    public InitializedGame<P, E, B> restart() { return initialize(); }

    /** Orkestrasi F00 lengkap melalui adapter, tanpa menambah state/clock kedua. */
    public InitializedGame<P, E, B> start(InitializationTarget<P, E, B> target) {
        Objects.requireNonNull(target);
        InitializedGame<P, E, B> game = initialize();
        target.installWorld(game);
        target.clearTransientState();
        target.resetScore();
        target.resetTimer(game.initialTimerMs());
        target.enterPlaying();
        return game;
    }

    /** Restart saat loop lama sudah berhenti update; semua binding world lama diganti. */
    public InitializedGame<P, E, B> restart(InitializationTarget<P, E, B> target) { return start(target); }
}
