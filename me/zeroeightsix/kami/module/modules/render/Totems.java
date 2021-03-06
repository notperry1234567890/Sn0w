//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import java.awt.Font;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Module.Info(name="Totems", category=Module.Category.GUI)
public class Totems
extends Module {
    private Setting<Float> x = this.register(Settings.f("InfoX", 591.0f));
    private Setting<Float> y = this.register(Settings.f("InfoY", 300.0f));
    private Setting<Boolean> rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Boolean> smooth = this.register(Settings.b("Smooth Font", true));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(182).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(26).build());
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    @Override
    public void onRender() {
        int color;
        float yCount = this.y.getValue().floatValue();
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int drgb = color = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        int totems = Totems.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (Totems.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++totems;
        }
        if (this.rainbow.getValue().booleanValue()) {
            float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            int red = rgb >> 16 & 0xFF;
            int green = rgb >> 8 & 0xFF;
            int blue = rgb & 0xFF;
            int n = color = ColourUtils.toRGBA(red, green, blue, 255);
        }
        if (this.smooth.getValue().booleanValue()) {
            this.cFontRenderer.drawStringWithShadow("Totems " + KamiMod.getInstance().guiManager.getHUDTextColor() + totems, this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
        } else {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("Totems " + KamiMod.getInstance().guiManager.getHUDTextColor() + totems, this.x.getValue().floatValue(), (yCount += 10.0f) - (float)Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT, color);
        }
    }
}

