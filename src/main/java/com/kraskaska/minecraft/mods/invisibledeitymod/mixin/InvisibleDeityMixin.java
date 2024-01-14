package com.kraskaska.minecraft.mods.invisibledeitymod.mixin;

import com.kraskaska.minecraft.mods.invisibledeitymod.InvisibleDeity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSources.class)
public class InvisibleDeityMixin {
    @Inject(at = @At("HEAD"), method = "playerAttack", cancellable = true)
    public void playerAttack(PlayerEntity attacker, CallbackInfoReturnable<DamageSource> ci) {
        if (attacker.isInvisible()) ci.setReturnValue(attacker.getWorld().getDamageSources().create(InvisibleDeity.INSTANCE.getPLAYER_ATTACK_INVISIBLE_DAMAGE_TYPE()));
    }
}
