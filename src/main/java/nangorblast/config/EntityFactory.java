/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;
import nangorblast.model.block.Block;
import nangorblast.model.map.TileType;
import nangorblast.util.Position;
/** Adapter ke konstruktor F01/F04/F05. Instance selalu baru untuk setiap initialize/restart. */
public interface EntityFactory<P, E, B> {
    P createPlayer(Position spawn, PlayerDefaults defaults);
    E createNormalEnemy(Position spawn, int index);
    B createMantis(Position spawn);
    Block createBlock(Position position, TileType type);
}
