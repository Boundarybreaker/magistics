package T145.magistics.api.enums;

import T145.magistics.api.objects.IVariant;
import net.minecraft.util.math.MathHelper;

public enum EnumCrucible implements IVariant {

	BASIC, EYES, THAUMIUM, SOULS;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	@Override
	public String getClientName() {
		return "variant=" + getName();
	}

	public static EnumCrucible byMetadata(int meta) {
		return values()[MathHelper.clamp_int(meta, 0, meta)];
	}
}