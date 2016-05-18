package T145.magistics.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import T145.magistics.tiles.TileNetherforge;

public class BlockNetherforge extends BlockContainer {
	public BlockNetherforge() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileNetherforge();
	}
}