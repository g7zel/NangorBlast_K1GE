/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;
import nangorblast.model.bomb.BombType;
/** Angka wajib spek; speed dasar 4 tile/detik adalah keputusan implementasi kelompok. */
public record PlayerDefaults(int lives, int bombCapacity, int blastRange, double baseSpeed,
                             boolean remoteUnlocked, int activeBombs, BombType selectedBomb) {
    public static PlayerDefaults standard() { return new PlayerDefaults(3, 1, 1, 4.0, false, 0, BombType.NORMAL); }
}
