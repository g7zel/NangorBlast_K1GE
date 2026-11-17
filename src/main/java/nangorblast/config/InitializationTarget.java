/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;

/** Adapter F00 ke engine Fayza, dipanggil pada game-loop thread sebelum PLAYING. */
public interface InitializationTarget<P, E, B> {
    /** Ganti referensi world lama dan pasang ulang renderer, occupancy, movement serta pickup. */
    void installWorld(InitializedGame<P, E, B> game);
    /** Kosongkan entity bom/ledakan dan antrean aksi lama, bukan sekadar menyembunyikannya. */
    void clearTransientState();
    void resetScore();
    void resetTimer(long milliseconds);
    /** Satu-satunya tahap yang boleh mengubah state menjadi PLAYING. */
    void enterPlaying();
}
