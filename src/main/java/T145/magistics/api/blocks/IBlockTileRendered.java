package T145.magistics.api.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockTileRendered extends IBlockTiled {

	public Class getTileClass();

	@SideOnly(Side.CLIENT)
	public void registerRenderer();
}