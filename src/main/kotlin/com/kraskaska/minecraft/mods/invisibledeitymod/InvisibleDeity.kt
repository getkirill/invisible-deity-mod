package com.kraskaska.minecraft.mods.invisibledeitymod

import net.fabricmc.api.ModInitializer
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object InvisibleDeity : ModInitializer {
    val PLAYER_ATTACK_INVISIBLE: RegistryKey<DamageType> = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier("invisible_deity_mod","player_attack_invisible"))
    override fun onInitialize() {
        LoggerFactory.getLogger(InvisibleDeity.javaClass).info("May the invisible deity be with you... erm")
    }
}