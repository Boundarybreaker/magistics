package T145.magistics.tiles.crafting;

import T145.magistics.api.MagisticsApi;
import T145.magistics.api.crafting.InfuserRecipe;
import T145.magistics.api.logic.IFacing;
import T145.magistics.api.magic.IQuintManager;
import T145.magistics.api.magic.QuintHelper;
import T145.magistics.core.Init;
import T145.magistics.items.ItemShard;
import T145.magistics.network.PacketHandler;
import T145.magistics.network.messages.client.MessageInfuserProgress;
import T145.magistics.tiles.MTileInventory;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileInfuser extends MTileInventory implements ITickable, IQuintManager, IFacing {

	public float progress;
	public float quintCost;

	private final boolean dark;
	private int soundDelay;
	private int boost;
	private int boostDelay = 20;
	private int suction;
	private EnumFacing facing = EnumFacing.NORTH;

	public TileInfuser(boolean dark) {
		this.dark = dark;
	}

	public TileInfuser() {
		this(false);
	}

	public boolean isDark() {
		return dark;
	}

	public int getBoost() {
		return boost;
	}

	@Override
	public boolean isHorizontalFacing() {
		return true;
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	@Override
	public void setFacing(EnumFacing side) {
		facing = side;
	}

	@Override
	public boolean canConnect(EnumFacing facing) {
		return facing != EnumFacing.UP;
	}

	@Override
	public int getSuction() {
		return suction;
	}

	@Override
	public void setSuction(int suction) {
		this.suction = suction;
	}

	@Override
	public int getSizeInventory() {
		return dark ? 6 : 8;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) {
		return slot > (dark ? 0 : 1);
	}

	@Override
	public boolean canExtractItem(int slot, int amount, boolean simulate) {
		return true;
	}

	@Override
	public void readPacketNBT(NBTTagCompound compound) {
		super.readPacketNBT(compound);
		facing = EnumFacing.getFront(compound.getInteger("Facing"));
		progress = compound.getFloat("Progress");
		quintCost = compound.getFloat("QuintCost");

		if (isCrafting()) {
			world.notifyBlockUpdate(pos, getState(), getState(), 1);
		}
	}

	@Override
	public void writePacketNBT(NBTTagCompound compound) {
		super.writePacketNBT(compound);
		compound.setInteger("Facing", facing.getIndex());
		compound.setFloat("Progress", progress);
		compound.setFloat("QuintCost", quintCost);
	}

	public int getCookProgressScaled(int time) {
		return Math.round(progress / quintCost * time);
	}

	public int getBoostScaled() {
		return Math.round(0.1F + boost / 2F) * 6;
	}

	public boolean isCrafting() {
		return progress > 0F;
	}

	public void sendCraftingProgressPacket() {
		PacketHandler.INSTANCE.sendToAllAround(new MessageInfuserProgress(this), PacketHandler.getTargetPoint(world, pos));
	}

	@Override
	public void update() {
		setSuction(0);

		if (soundDelay > 0) {
			--soundDelay;
		}

		if (world.isRemote) {
			return;
		}

		if (isCrafting()) {
			sendCraftingProgressPacket();
		}

		InfuserRecipe infuserRecipe = MagisticsApi.getMatchingInfuserRecipe(itemHandler.getStacks().toArray(new ItemStack[getSizeInventory()]), isDark());

		if (infuserRecipe != null && !world.isBlockPowered(pos)) {
			quintCost = infuserRecipe.getCost();

			if (canCraft(infuserRecipe) && QuintHelper.drainQuints(world, pos, quintCost, false) > 0F) {
				float quintDrain = Math.min(0.5F + 0.05F * boost, quintCost - progress + 0.01F);
				progress += QuintHelper.drainQuints(world, pos, quintDrain, true);

				if (soundDelay == 0 && progress > 0.025F) {
					// slightly discharge this chunk's aura

					world.playSound(null, pos, isDark() ? Init.SOUND_INFUSER_DARK : Init.SOUND_INFUSER, SoundCategory.MASTER, 0.2F, 1F);
					soundDelay = 62;
				}

				if (progress >= quintCost) {
					addProcessedItem(infuserRecipe);
					reset();
					refresh();
				}
			} else { // pause crafting
				hardReset();
			}
		} else {
			hardReset();
		}
	}

	public boolean canCraft(InfuserRecipe infuserRecipe) {
		if (infuserRecipe == null) {
			return false;
		}

		ItemStack result = infuserRecipe.getResult();

		if (result.isEmpty()) {
			return false;
		} else if (itemHandler.getStackInSlot(0).isEmpty()) {
			return true;
		} else if (!MagisticsApi.areItemStacksEqual(result, itemHandler.getStackInSlot(0))) {
			return false;
		} else {
			int resultCount = itemHandler.getStackInSlot(0).getCount() + result.getCount();
			return resultCount <= itemHandler.getSlotLimit(0) && resultCount <= result.getMaxStackSize();
		}
	}

	private void addProcessedItem(InfuserRecipe infuserRecipe) {
		ItemStack result = infuserRecipe.getResult();
		ItemStack[] components = infuserRecipe.getComponents();

		if (itemHandler.getStackInSlot(0).isEmpty()) {
			itemHandler.setStackInSlot(0, result.copy());
		} else if (MagisticsApi.areItemStacksEqual(itemHandler.getStackInSlot(0), result) && itemHandler.getStackInSlot(0).getCount() < result.getMaxStackSize()) {
			itemHandler.getStackInSlot(0).grow(result.getCount());
		}

		for (int slot = dark ? 1 : 2; slot < getSizeInventory(); ++slot) {
			if (!itemHandler.getStackInSlot(slot).isEmpty()) {
				for (ItemStack component : components) {
					if (MagisticsApi.areItemStacksEqual(component, itemHandler.getStackInSlot(slot))) {
						itemHandler.getStackInSlot(slot).shrink(1);

						// fluid container compatibility

						if (!dark && component.getItem() instanceof ItemShard) {
							if (itemHandler.getStackInSlot(1).isEmpty()) {
								itemHandler.setStackInSlot(1, new ItemStack(Init.CRYSTAL_SHARD, 1, 0));
							} else {
								itemHandler.getStackInSlot(1).grow(1);
							}
						}

						if (itemHandler.getStackInSlot(slot).isEmpty()) {
							itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
						}
					}
				}
			}
		}
	}

	private void reset() {
		progress = 0F;
		quintCost = 0F;
	}

	private void hardReset() {
		if (isCrafting()) {
			world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1.6F);
			refresh();
		}

		reset();
	}
}