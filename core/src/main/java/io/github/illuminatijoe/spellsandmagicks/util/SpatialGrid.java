package io.github.illuminatijoe.spellsandmagicks.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpatialGrid {
    private final int cellSize;
    private final Map<Vector2, Set<Entity>> grid = new HashMap<>();

    public SpatialGrid(int cellSize) {
        this.cellSize = cellSize;
    }

    private Vector2 getCell(Vector2 pos) {
        return new Vector2((int)(pos.x/cellSize), (int)(pos.y/cellSize));
    }

    public void addEntity(Entity entity, Vector2 pos) {
        Vector2 cell = getCell(pos);
        grid.computeIfAbsent(cell, k -> new HashSet<>()).add(entity);
    }

    public Set<Entity> getNearbyEntities(Vector2 position) {
        Vector2 cell = getCell(position);
        Set<Entity> nearby = new HashSet<>();

        // Check neighboring cells
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                Vector2 neighbor = new Vector2(cell.x + dx, cell.y + dy);
                if (grid.containsKey(neighbor)) {
                    nearby.addAll(grid.get(neighbor));
                }
            }
        }
        return nearby;
    }

    public void clear() {
        grid.clear();
    }
}
