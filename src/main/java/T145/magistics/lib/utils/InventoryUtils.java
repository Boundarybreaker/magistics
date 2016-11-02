package T145.magistics.lib.utils;

import net.minecraft.item.ItemStack;

public class InventoryUtils {

	public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
		return stack1 != null && stack2 != null && stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
}