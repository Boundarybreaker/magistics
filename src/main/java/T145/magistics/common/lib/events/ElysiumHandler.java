package T145.magistics.common.lib.events;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import T145.magistics.common.Magistics;
import T145.magistics.common.config.ConfigObjects;
import T145.magistics.common.lib.world.dim.ElysiumWorldProvider;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ElysiumHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if(fuel.getItem() == ConfigObjects.itemSulphur)
		{
			return 1600;
		}
		
		return 0;
	}

	public static ElysiumHandler INSTANCE = new ElysiumHandler();
    public Map<Block, Item> buckets = new HashMap<Block, Item>();

    private ElysiumHandler() {
    }
    
    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result == null)
                return;

        event.result = result;
        event.setResult(Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
    {
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        Item bucket = buckets.get(block);
        if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
                world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
                return new ItemStack(bucket);
        } else
                return null;
    }
    
    @SubscribeEvent
	public void onBreakBlock(BlockEvent.BreakEvent event)
	{
		if(!event.getPlayer().capabilities.isCreativeMode &&  event.world.provider instanceof ElysiumWorldProvider && event.y <= Magistics.proxy.labyrinthTop && event.y >= Magistics.proxy.labyrinthBottom)
		{
			if(event.block == Blocks.quartz_block || event.block == ConfigObjects.blockQuartzBlock || event.block == ConfigObjects.blockEnergyCrystal || event.block == Blocks.trapdoor)
				event.setCanceled(true);
		}
	}
    
//    if(world.getBlock(x, y, z) == Blocks.dragon_egg)
//	{
//		world.createExplosion(player, x+0.5D, y+0.5D, z+0.5D, 2F, true);
//		world.setBlock(x, y, z, ConfigObjects.blockPortalCore);
//		if(!player.capabilities.isCreativeMode)
//			stack.stackSize--;
//		//return true;
//	}
    
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event)
    {
    	if(event.action == Action.RIGHT_CLICK_BLOCK)
    	{
    		if(event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() == Items.diamond &&
    				event.world.getBlock(event.x, event.y, event.z) == Blocks.dragon_egg)
    		{
    			event.world.createExplosion(event.entityPlayer, event.x+0.5D, event.y+0.5D, event.z+0.5D, 2F, true);
    			event.world.setBlock(event.x, event.y, event.z, ConfigObjects.blockPortalCore);
				if(!event.entityPlayer.capabilities.isCreativeMode)
					event.entityPlayer.getCurrentEquippedItem().stackSize--;
				
				event.useBlock = Result.DENY;
    		}
    	}
    }
    
}