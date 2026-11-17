/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nangorblast.model.map.TileType;
import nangorblast.util.Position;

public record MapData(List<String> rows) {
    public MapData { rows = List.copyOf(Objects.requireNonNull(rows)); }
    public int height() { return rows.size(); }
    public int width() { return rows.isEmpty() ? 0 : rows.get(0).length(); }
    public boolean contains(Position p) {
        return p != null && p.row() >= 0 && p.row() < height() && p.col() >= 0 && p.col() < rows.get(p.row()).length();
    }
    public char symbolAt(Position p) { return rows.get(p.row()).charAt(p.col()); }
    public TileType[][] tiles() {
        TileType[][] result = new TileType[height()][];
        for (int r = 0; r < height(); r++) {
            result[r] = new TileType[rows.get(r).length()];
            for (int c = 0; c < result[r].length; c++) result[r][c] = TileType.fromSymbol(rows.get(r).charAt(c));
        }
        return result;
    }
    public List<Position> positionsOf(char symbol) {
        List<Position> result = new ArrayList<>();
        for (int r = 0; r < height(); r++)
            for (int c = 0; c < rows.get(r).length(); c++)
                if (rows.get(r).charAt(c) == symbol) result.add(new Position(r, c));
        return List.copyOf(result);
    }
    private Position single(char symbol) {
        List<Position> positions = positionsOf(symbol);
        if (positions.size() != 1) throw new IllegalStateException("Map belum valid: " + symbol);
        return positions.get(0);
    }
    public Position playerSpawn() { return single('P'); }
    public List<Position> enemySpawns() { return positionsOf('E'); }
    public Position bossSpawn() { return single('B'); }
    public Position exit() { return single('X'); }
    public List<Position> destructibleBlocks() { return positionsOf('D'); }
}
