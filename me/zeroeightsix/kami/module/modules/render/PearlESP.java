//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;

@Module.Info(name="PearlESP", category=Module.Category.RENDER)
public class PearlESP
extends Module {
    private Setting<Boolean> players = this.register(Settings.b("Players", false));
    private Setting<Integer> a = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(50).build());
    private Setting<Integer> r = this.register(Settings.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> g = this.register(Settings.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> b = this.register(Settings.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue(255).build());
    private Setting<Boolean> epearls = this.register(Settings.b("Pearl", true));

    @Override
    public void onWorldRender(RenderEvent event) {
        Color c = new Color(this.r.getValue(), this.g.getValue(), this.b.getValue(), this.a.getValue());
        Color friend = new Color(0, 0, 255, this.a.getValue());
        Color enemy = new Color(255, 0, 0, this.a.getValue());
        Color finalC = c;
        PearlESP.mc.world.loadedEntityList.stream().filter(entity -> entity != PearlESP.mc.player).forEach(e -> {
            KamiTessellator.prepare(7);
            if (this.players.getValue().booleanValue() && e instanceof EntityPlayer) {
                if (Friends.isFriend(e.getName())) {
                    KamiTessellator.drawBox(e.getRenderBoundingBox(), friend.getRGB(), 63);
                } else {
                    KamiTessellator.drawBox(e.getRenderBoundingBox(), enemy.getRGB(), 63);
                }
            }
            if (this.epearls.getValue().booleanValue() && e instanceof EntityEnderPearl) {
                KamiTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), 63);
            }
            KamiTessellator.release();
        });
    }
}

