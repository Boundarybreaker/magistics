package T145.magistics.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockInfusionWorkbenchItem extends ItemBlock {
	public BlockInfusionWorkbenchItem(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		String postfix = "";

		if (is.getItemDamage() == 0) {
			postfix = ".base";
		}

		return super.getUnlocalizedName() + postfix;
	}
}