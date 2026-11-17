/*
 * NIM  : 18225097
 * Nama : Fayza Kamilia
 */
package nangorblast.util;

/**
 * Koordinat (row, col) pada grid, indeks mulai dari 0 (baris 0 paling atas).
 * Immutable. Di spek indeks mulai dari 1, konversi hanya dilakukan di MapLoader dan tampilan.
 */
public record Position(int row, int col) {

    /** Posisi bertetangga satu tile ke arah {@code direction}. */
    public Position translate(Direction direction) {
        return new Position(row + direction.dRow(), col + direction.dCol());
    }
}
