package com.kraskaska.minecraft.mods.invisibledeitymod

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.util.InvalidIdentifierException
import org.slf4j.LoggerFactory
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

object InvisibleDeity : ModInitializer {
    val DEFAULT_DAMAGE_TYPE = Identifier.of("invisible_deity_mod", "player_attack_invisible")
    val logger = LoggerFactory.getLogger(InvisibleDeity.javaClass)
    val config by lazy {
        val configFile = FabricLoader.getInstance().configDir.resolve("invisible-deity-damage-type.txt")
        if (!configFile.exists()) {
            logger.warn("Damage type config doesn't exist, creating one now")
            configFile.writeText(
                """
                # -- lines starting with # are ignored --
                # Damage type to be dealt by invisible players when using Invisible Deity
                # Default value is invisible_deity_mod:player_attack_invisible
                # Mod will look for the first non-empty line that is valid minecraft identifier
                # without # at the beginning. If it doesn't exist it will fall back to default.
                #
                # Common use for this file is to replace damage to vanilla generic one,
                # so that it will be localised regardless if client has mod or not.
                #
                # Examples #
                # the most sensible option:
                #minecraft:magic
                # this one also works
                #minecraft:generic
                # do not use damage that bypasses invulnerability, or else you risk enabling your
                # players to kill other people while bypassing their totems of undying
                invisible_deity_mod:player_attack_invisible
            """.trimIndent()
            )
            return@lazy DEFAULT_DAMAGE_TYPE
        }
        val damageType = configFile.readText().lines().firstOrNull {
            it.isNotBlank() && !it.trim().startsWith("#") && try {
                Identifier.of(it.trim()); true
            } catch (e: InvalidIdentifierException) {
                false
            }
        }?.let { Identifier.of(it.trim()) }
        if (damageType == null) {
            logger.error("Config damage type is invalid, defaulting to invisible_deity_mod:player_attack_invisible")
            return@lazy DEFAULT_DAMAGE_TYPE
        }
        logger.info("Damage type: $damageType")
        return@lazy damageType
    }
    val PLAYER_ATTACK_INVISIBLE_DAMAGE_TYPE: RegistryKey<DamageType> = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, config)
    val DEFAULT_DAMAGE_TYPE_KEY =
        RegistryKey.of(RegistryKeys.DAMAGE_TYPE, (Identifier.of("invisible_deity_mod", "player_attack_invisible")))

    override fun onInitialize() {
        logger.info("May the invisible deity be with you... erm")
    }
}