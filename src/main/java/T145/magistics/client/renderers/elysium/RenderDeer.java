package T145.magistics.client.renderers.elysium;

import T145.magistics.client.renderers.entity.ModelDeer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDeer extends RenderLiving
{
	private ModelDeer model;

    public RenderDeer()
    {
        super(new ModelDeer(), 1F);
        this.model = (ModelDeer)super.mainModel;
        this.setRenderPassModel(this.model);
    }
    
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("elysium:textures/mobs/Deer.png");
	}

}
