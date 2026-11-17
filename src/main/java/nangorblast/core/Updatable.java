/*
 * NIM  : 18225097
 * Nama : Fayza Kamilia
 */
package nangorblast.core;

/** Komponen yang diperbarui setiap tick oleh game loop (F09.3). */
public interface Updatable {
    /** @param deltaMs waktu game yang berlalu sejak update terakhir, dalam milidetik */
    void update(long deltaMs);
}
