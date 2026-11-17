/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.config;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nangorblast.exception.InvalidMapException;
import nangorblast.util.Direction;
import nangorblast.util.Position;

public final class MapValidator {
    public static final int WIDTH = 14;
    public static final int HEIGHT = 10;
    public void validate(MapData map) throws InvalidMapException {
        Objects.requireNonNull(map);
        if (map.height() != HEIGHT || map.rows().stream().anyMatch(row -> row.length() != WIDTH))
            fail("Ukuran map harus 14 kolom x 10 baris");
        for (String row : map.rows()) for (char ch : row.toCharArray())
            if ("#.DPEBXI".indexOf(ch) < 0) fail("Simbol tidak sesuai White Glacier: " + ch);
        count(map, 'P', 1); count(map, 'E', 5); count(map, 'B', 1); count(map, 'X', 1);
        for (char ch : new char[] {'#', 'D', 'I'})
            if (map.positionsOf(ch).isEmpty()) fail("Map White Glacier harus memiliki " + ch);
        // Satu karakter per sel menjamin marker spawn berbeda dan berada di terrain FLOOR.
        Set<Position> reached = reachableStops(map, true);
        if (!reached.contains(map.bossSpawn()) || !reached.contains(map.exit()))
            fail("Boss atau exit tidak dapat dicapai dengan aturan sliding");
        if (!reached.containsAll(map.enemySpawns())) fail("Ada spawn enemy yang terisolasi");
    }
    private static void count(MapData map, char ch, int expected) throws InvalidMapException {
        int actual = map.positionsOf(ch).size();
        if (actual != expected) fail("Jumlah " + ch + " harus " + expected + ", ditemukan " + actual);
    }
    private static void fail(String message) throws InvalidMapException { throw new InvalidMapException(message); }
    /** D boleh dianggap sudah dihancurkan; hanya posisi akhir sliding yang bisa dibelokkan. */
    public Set<Position> reachableStops(MapData map, boolean afterBlocksDestroyed) {
        Set<Position> seen = new HashSet<>();
        ArrayDeque<Position> queue = new ArrayDeque<>();
        queue.add(map.playerSpawn()); seen.add(map.playerSpawn());
        while (!queue.isEmpty()) {
            Position from = queue.removeFirst();
            for (Direction dir : Direction.values()) {
                Position to = from.translate(dir);
                if (!passable(map, to, afterBlocksDestroyed)) continue;
                while (map.symbolAt(to) == 'I') {
                    Position next = to.translate(dir);
                    if (!passable(map, next, afterBlocksDestroyed)) break;
                    to = next;
                }
                if (seen.add(to)) queue.addLast(to);
            }
        }
        return Set.copyOf(seen);
    }
    private boolean passable(MapData map, Position p, boolean afterBlocksDestroyed) {
        return map.contains(p) && map.symbolAt(p) != '#' && (map.symbolAt(p) != 'D' || afterBlocksDestroyed);
    }
}
