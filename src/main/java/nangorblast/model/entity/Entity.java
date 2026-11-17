/*
 * NIM  : 18225097
 * Nama : Fayza Kamilia
 */
package nangorblast.model.entity;

import nangorblast.util.Position;

/**
 * Induk semua objek yang berposisi di map (Player, Enemy, Boss).
 * Objek yang mati diberi {@code active = false} dan dibuang engine di akhir tick.
 */
public abstract class Entity {
    protected Position position;
    protected boolean active = true;

    protected Entity() {
        this(new Position(0, 0));
    }

    protected Entity(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
