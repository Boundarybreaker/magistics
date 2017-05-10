package T145.magistics.api.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeRegistry {

	private static Map<ItemStack, Float> crucibleRecipes = new HashMap<ItemStack, Float>();
	private static List<InfuserRecipe> infuserRecipes = new ArrayList<InfuserRecipe>();

	public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
		return stackA.isEmpty() && stackB.isEmpty() ? true : (!stackA.isEmpty() && !stackB.isEmpty() ? isItemStackEqual(stackA, stackB) : false);
	}

	private static boolean isItemStackEqual(ItemStack first, ItemStack other) {
		return first.getItem() != other.getItem() ? false : (first.getItemDamage() != other.getItemDamage() ? false : (first.getTagCompound() == null && other.getTagCompound() != null ? false : (first.getTagCompound() == null || first.getTagCompound().equals(other.getTagCompound())) && first.areCapsCompatible(other)));
	}

	public static void addCrucibleRecipe(ItemStack input, float quintOutput) {
		crucibleRecipes.put(input, quintOutput);
	}

	public static void addCrucibleRecipe(Block block, float quintOutput) {
		addCrucibleRecipe(new ItemStack(block), quintOutput);
	}

	public static void addCrucibleRecipe(Item item, float quintOutput) {
		addCrucibleRecipe(new ItemStack(item), quintOutput);
	}

	@Nonnull
	public static float getCrucibleResult(ItemStack stack) {
		if (!stack.isEmpty()) {
			for (Entry<ItemStack, Float> entry : crucibleRecipes.entrySet()) {
				if (areItemStacksEqual(stack, entry.getKey())) {
					return entry.getValue();
				}
			}
		}

		return 0F;
	}

	public static void addInfuserRecipe(ItemStack result, ItemStack[] components, float quintCost, boolean dark) {
		infuserRecipes.add(new InfuserRecipe(result, components, quintCost, dark));
	}

	@Nullable
	public static InfuserRecipe getMatchingInfuserRecipe(ItemStack[] components, boolean isDark) {
		for (InfuserRecipe recipe : infuserRecipes) {
			if (recipe.matches(components, isDark)) {
				return recipe;
			}
		}

		return null;
	}
}