/*
 * NIM  : 18225097
 * Nama : Fayza Kamilia
 */
package nangorblast.model.entity;

import org.junit.jupiter.api.Test;

import nangorblast.model.bomb.BombType;
import nangorblast.model.powerup.PowerUpType;
import nangorblast.util.Direction;
import nangorblast.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {
    private static final Position SPAWN = new Position(1, 1);

    private Player player() {
        return new Player(SPAWN);
    }

    @Test
    void initialValuesFollowSpec() {
        Player p = player();
        assertEquals(3, p.getLives());
        assertEquals(1, p.getBombCapacity());
        assertEquals(1, p.getBlastRange());
        assertEquals(BombType.NORMAL, p.getSelectedBomb());
        assertFalse(p.isRemoteUnlocked());
    }

    @Test
    void movesOnlyOntoWalkableTile() {
        Player p = player();
        assertFalse(p.tryMove(Direction.UP, pos -> false));
        assertEquals(SPAWN, p.getPosition());
        assertEquals(Direction.UP, p.getDirection());
        assertTrue(p.tryMove(Direction.RIGHT, pos -> true));
        assertEquals(new Position(1, 2), p.getPosition());
    }

    @Test
    void movementIsRateLimitedBySpeed() {
        Player p = player();
        assertTrue(p.tryMove(Direction.RIGHT, pos -> true));
        assertFalse(p.tryMove(Direction.RIGHT, pos -> true));
        p.update(1000);
        assertTrue(p.tryMove(Direction.RIGHT, pos -> true));
    }

    @Test
    void damageRemovesOneLifeAndInvulnerabilityBlocksNext() {
        Player p = player();
        assertTrue(p.takeDamage());
        assertEquals(2, p.getLives());
        p.respawn(SPAWN);
        assertTrue(p.isInvulnerable());
        assertFalse(p.takeDamage());
        assertEquals(2, p.getLives());
        p.update(1_500);
        assertFalse(p.isInvulnerable());
        assertTrue(p.takeDamage());
    }

    @Test
    void lastLifeDeactivatesPlayer() {
        Player p = player();
        for (int i = 0; i < 3; i++) {
            p.update(2_000);
            p.takeDamage();
        }
        assertEquals(0, p.getLives());
        assertFalse(p.isActive());
    }

    @Test
    void powerUpsRespectMaximums() {
        Player p = player();
        for (int i = 0; i < 10; i++) {
            p.applyPowerUp(PowerUpType.BOMB_UP);
            p.applyPowerUp(PowerUpType.FIRE_UP);
            p.applyPowerUp(PowerUpType.HEART);
        }
        assertEquals(3, p.getBombCapacity());
        assertEquals(5, p.getBlastRange());
        assertEquals(5, p.getLives());
    }

    @Test
    void remoteBombNeedsDetonator() {
        Player p = player();
        assertFalse(p.selectBomb(BombType.REMOTE));
        assertEquals(BombType.NORMAL, p.getSelectedBomb());
        p.applyPowerUp(PowerUpType.REMOTE_DETONATOR);
        assertTrue(p.selectBomb(BombType.REMOTE));
        assertTrue(p.selectBomb(BombType.PUMPED));
    }

    @Test
    void bombCapacityIsReturnedAfterExplosion() {
        Player p = player();
        assertTrue(p.canPlaceBomb());
        p.onBombPlaced();
        assertFalse(p.canPlaceBomb());
        p.onBombExploded();
        assertTrue(p.canPlaceBomb());
    }

    @Test
    void resetRestoresEverything() {
        Player p = player();
        p.applyPowerUp(PowerUpType.REMOTE_DETONATOR);
        p.applyPowerUp(PowerUpType.BOMB_UP);
        p.takeDamage();
        p.reset(SPAWN);
        assertEquals(3, p.getLives());
        assertEquals(1, p.getBombCapacity());
        assertFalse(p.isRemoteUnlocked());
        assertTrue(p.isActive());
    }
}
