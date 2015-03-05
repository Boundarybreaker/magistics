package T145.magistics.common.items.equipment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class ElysiumItemPickaxe extends ItemPickaxe
{

	public ElysiumItemPickaxe(ToolMaterial mat) {
		super(mat);
	}

	@Override
    public Item setTextureName(String name)
    {
        this.iconString = "elysium:" + name;
        return this;
    }
}
