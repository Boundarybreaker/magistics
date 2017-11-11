package T145.magistics.client.particles.core;

import T145.magistics.client.particles.ParticleGreenFlame;
import T145.magistics.client.particles.ParticleWisp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ParticleManager {

	private ParticleManager() {}

	public static int particleCount(int base) {
		return FMLClientHandler.instance().getClient().gameSettings.particleSetting == 1 ? base * 1 : FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2 ? base / 2 : base * 2;
	}

	public static void greenFlameFX(World world, float x, float y, float z) {
		ParticleGreenFlame flame = new ParticleGreenFlame(world, x, y, z, 0F, 0F, 0F);
		Minecraft.getMinecraft().effectRenderer.addEffect(flame);
	}

	public static void smallGreenFlameFX(World world, float x, float y, float z) {
		ParticleGreenFlame flame = new ParticleGreenFlame(world, x, y, z, 0F, 0F, 0F);
		flame.setScale(0.1F);
		Minecraft.getMinecraft().effectRenderer.addEffect(flame);
	}

	public static void customWispFX(World world, double x, double y, double z, double destX, double destY, double destZ, float gravity, int type) {
		ParticleWisp fx = new ParticleWisp(world, x, y, z, destX, destY, destZ, gravity, type);
		ParticleEngine.addEffect(world, fx);
	}

	public static void wispFX(World world, double x, double y, double z, float f, float g, float h, float i) {
		ParticleWisp fx = new ParticleWisp(world, x, y, z, f, g, h, i);
		fx.setGravity(0.02F);
		ParticleEngine.addEffect(world, fx);
	}

	public static void wispFX2(World world, double x, double y, double z, float size, int type, boolean shrink, boolean clip, float gravity) {
		ParticleWisp fx = new ParticleWisp(world, x, y, z, size, type);
		fx.setGravity(gravity);
		fx.shrink = shrink;
		fx.setNoClip(clip);
		ParticleEngine.addEffect(world, fx);
	}

	public static void wispFX3(World world, double x, double y, double z, double destX, double destY, double destZ, float size, int type, boolean shrink, float gravity) {
		ParticleWisp fx = new ParticleWisp(world, x, y, z, destX, destY, destZ, size, type);
		fx.setGravity(gravity);
		fx.shrink = shrink;
		ParticleEngine.addEffect(world, fx);
	}

	public static void wispFX4(World world, double x, double y, double z, Entity target, int type, boolean shrink, float gravity) {
		ParticleWisp fx = new ParticleWisp(world, x, y, z, target, type);
		fx.setGravity(gravity);
		fx.shrink = shrink;
		ParticleEngine.addEffect(world, fx);
	}
}