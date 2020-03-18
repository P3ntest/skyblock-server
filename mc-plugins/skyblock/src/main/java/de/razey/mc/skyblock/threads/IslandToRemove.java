package de.razey.mc.skyblock.threads;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class IslandToRemove {
    private int position;

    public List<Block> blocks;

    public IslandToRemove(int position) {
        this.position = position;
        this.blocks = new ArrayList<Block>();
    }


}
