/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;
import java.util.List;
import java.util.Objects;
import nangorblast.model.map.GameMap;
import nangorblast.model.powerup.PowerUpService;
/** Hasil transaksi F00. Engine mengambil ownership runtime saat hasil ini dipasang. */
public record InitializedGame<P, E, B>(MapData data, GameMap map, P player, List<E> enemies,
                                      B boss, PowerUpService powerUps) {
    public InitializedGame {
        Objects.requireNonNull(data); Objects.requireNonNull(map); Objects.requireNonNull(player);
        enemies = List.copyOf(enemies); Objects.requireNonNull(boss); Objects.requireNonNull(powerUps);
    }
    public int initialScore() { return 0; }
    public long initialTimerMs() { return 300_000L; }
    // Bomb/explosion/score/timer dan GameState bukan salinan kedua di sini: dimiliki GameEngine.
}
