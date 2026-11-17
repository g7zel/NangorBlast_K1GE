/*
 * NIM  : 18225097
 * Nama : Fayza Kamilia
 */
package nangorblast.model.entity;

import java.util.function.Predicate;

import nangorblast.core.Updatable;
import nangorblast.model.bomb.BombType;
import nangorblast.model.powerup.PowerUpType;
import nangorblast.util.Direction;
import nangorblast.util.Position;

/**
 * Deeper, entitas yang dikendalikan pemain (F01).
 * Player tidak mengenal map: pengecekan tile yang bisa dilewati diberikan lewat {@link Predicate}.
 */
public class Player extends Entity implements Updatable {
    public static final int INITIAL_LIVES = 3;
    public static final int MAX_LIVES = 5;
    public static final int INITIAL_BOMB_CAPACITY = 1;
    public static final int MAX_BOMB_CAPACITY = 3;
    public static final int INITIAL_BLAST_RANGE = 1;
    public static final int MAX_BLAST_RANGE = 5;
    public static final double INITIAL_SPEED = 4.0;   // tile per detik, nilai awal usulan tim
    public static final double SPEED_UP_STEP = 1.0;
    public static final double MAX_SPEED = 8.0;
    public static final long INVULNERABLE_MS = 1_500;

    private Direction direction = Direction.DOWN;
    private int lives = INITIAL_LIVES;
    private int bombCapacity = INITIAL_BOMB_CAPACITY;
    private int activeBombs;
    private int blastRange = INITIAL_BLAST_RANGE;
    private double speed = INITIAL_SPEED;
    private long invulnerableMs;
    private long moveCooldownMs;
    private boolean remoteUnlocked;
    private BombType selectedBomb = BombType.NORMAL;

    /** Player dengan nilai awal standar spek (3 nyawa, kapasitas 1, range 1). */
    public Player(Position spawn) {
        super(spawn);
    }

    /** Player dengan nilai awal khusus, dipakai adapter F00 (EntityFactory) dari PlayerDefaults. */
    public Player(Position spawn, int lives, int bombCapacity, int blastRange, double speed) {
        super(spawn);
        this.lives = Math.min(MAX_LIVES, Math.max(1, lives));
        this.bombCapacity = Math.min(MAX_BOMB_CAPACITY, Math.max(1, bombCapacity));
        this.blastRange = Math.min(MAX_BLAST_RANGE, Math.max(1, blastRange));
        this.speed = Math.min(MAX_SPEED, Math.max(1.0, speed));
    }

    @Override
    public void update(long deltaMs) {
        invulnerableMs = Math.max(0, invulnerableMs - deltaMs);
        moveCooldownMs = Math.max(0, moveCooldownMs - deltaMs);
    }

    /**
     * Mencoba bergerak satu tile. Arah hadap selalu berubah, posisi berubah hanya jika
     * cooldown gerak sudah habis dan tile tujuan bisa dilewati.
     *
     * @return true jika player berpindah tile
     */
    public boolean tryMove(Direction dir, Predicate<Position> walkable) {
        direction = dir;
        if (!active || moveCooldownMs > 0) {
            return false;
        }
        Position target = position.translate(dir);
        if (!walkable.test(target)) {
            return false;
        }
        position = target;
        moveCooldownMs = (long) (1000 / speed);
        return true;
    }

    /**
     * Mengurangi satu nyawa kecuali sedang invulnerable (F01.2.2).
     * Nyawa habis menonaktifkan player. Respawn dilakukan pemanggil lewat {@link #respawn(Position)}.
     *
     * @return true jika damage benar-benar diterima
     */
    public boolean takeDamage() {
        if (!active || isInvulnerable()) {
            return false;
        }
        lives--;
        if (lives <= 0) {
            lives = 0;
            active = false;
        }
        return true;
    }

    /** Menempatkan player di {@code spawn} dengan invulnerability 1,5 detik (F01.2.4). */
    public void respawn(Position spawn) {
        position = spawn;
        invulnerableMs = INVULNERABLE_MS;
        moveCooldownMs = 0;
    }

    /** Efek Power-Up berlaku sampai permainan berakhir atau restart, dan tidak hilang saat kehilangan nyawa (F06.3). */
    public void applyPowerUp(PowerUpType type) {
        switch (type) {
            case BOMB_UP -> bombCapacity = Math.min(MAX_BOMB_CAPACITY, bombCapacity + 1);
            case FIRE_UP -> blastRange = Math.min(MAX_BLAST_RANGE, blastRange + 1);
            case SPEED_UP -> speed = Math.min(MAX_SPEED, speed + SPEED_UP_STEP);
            case REMOTE_DETONATOR -> remoteUnlocked = true;
            case HEART -> lives = Math.min(MAX_LIVES, lives + 1);
        }
    }

    /**
     * Memilih jenis bomb untuk pemasangan berikutnya (F02.3). Remote Bomb hanya bisa dipilih
     * setelah memperoleh Remote Detonator.
     *
     * @return true jika pilihan diterima
     */
    public boolean selectBomb(BombType type) {
        if (type == BombType.REMOTE && !remoteUnlocked) {
            return false;
        }
        selectedBomb = type;
        return true;
    }

    public boolean canPlaceBomb() {
        return active && activeBombs < bombCapacity;
    }

    /** Dipanggil modul bomb saat bomb berhasil dipasang. */
    public void onBombPlaced() {
        activeBombs++;
    }

    /** Dipanggil modul bomb saat bomb selesai meledak, mengembalikan kapasitas (F01.2.5). */
    public void onBombExploded() {
        activeBombs = Math.max(0, activeBombs - 1);
    }

    /** Mengembalikan semua atribut ke nilai awal untuk restart (F00.4). */
    public void reset(Position spawn) {
        position = spawn;
        direction = Direction.DOWN;
        active = true;
        lives = INITIAL_LIVES;
        bombCapacity = INITIAL_BOMB_CAPACITY;
        activeBombs = 0;
        blastRange = INITIAL_BLAST_RANGE;
        speed = INITIAL_SPEED;
        invulnerableMs = 0;
        moveCooldownMs = 0;
        remoteUnlocked = false;
        selectedBomb = BombType.NORMAL;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLives() {
        return lives;
    }

    public int getBombCapacity() {
        return bombCapacity;
    }

    public int getActiveBombs() {
        return activeBombs;
    }

    public int getBlastRange() {
        return blastRange;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isInvulnerable() {
        return invulnerableMs > 0;
    }

    public boolean isRemoteUnlocked() {
        return remoteUnlocked;
    }

    public BombType getSelectedBomb() {
        return selectedBomb;
    }
}
