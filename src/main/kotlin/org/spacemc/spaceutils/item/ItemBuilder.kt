package org.spacemc.spaceutils.item

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.lang.reflect.Field
import java.util.*
import kotlin.reflect.KClass

/**
 * NOTICE: This utility was developer as part of AeolusLib. While you can use it for your own projects, You are NOT allowed to delete or move this header comment.
 *
 * Utility:
 * Chainable [ItemStack]s
 *
 * Example Usage(s):
 * `ItemStack itemStack = new ItemBuilder(Material.SKULL_ITEM).amount(1).durability(3).skullOwner("MCAeolus").name(ChatColor.RED+"MCAeolus's Skull").make())`
 * `ItemStack itemStack = new ItemBuilder().type(Material.BEDROCK).lores(new String[]{"Lore1",ChatColor.RED+"Lore2"}).enchantment(Enchantment.DAMAGE_ALL, 99).make()`
 *
 * @author MCAeolus
 * @version 1.0
 */
@Suppress("unused")
class ItemBuilder {
    private val item: ItemStack
    private val itemMeta: ItemMeta

    /**
     * Init item chainable via given Material parameter.
     *
     * @param itemType
     * the [Material] to initiate the instance with.
     *
     * @since 1.0
     */
    constructor(itemType: Material) {
        item = ItemStack(itemType)
        itemMeta = item.itemMeta
    }

    /**
     * Init item chainable via given ItemStack parameter.
     *
     * @param itemStack
     * the [ItemStack] to initialize the instance with.
     *
     * @since 1.0
     */
    constructor(itemStack: ItemStack) {
        item = itemStack
        itemMeta = item.itemMeta
    }

    /**
     * Init the item chainable with no defined Material/ItemStack
     *
     * @since 1.0
     */
    constructor() {
        item = ItemStack(Material.AIR)
        itemMeta = item.itemMeta
    }

    /**
     * Changes the Material type of the [ItemStack]
     *
     * @param material
     * the new [Material] to set for the ItemStack.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun type(material: Material): ItemBuilder {
        make().type = material
        return this
    }

    /**
     * Changes the [ItemStack]s size.
     *
     * @param itemAmt
     * the new Integer count of the ItemStack.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun amount(itemAmt: Int): ItemBuilder {
        make().amount = itemAmt
        return this
    }

    /**
     * Changes the [ItemStack]s display name.
     *
     * @param name
     * the new String for the ItemStack's display name to be set to.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun name(name: String): ItemBuilder {
        meta().displayName(Component.text(name))
        make().itemMeta = meta()
        return this
    }

    fun name(name: Component): ItemBuilder {
        meta().displayName(name)
        make().itemMeta = meta()
        return this
    }

    fun unitalizedName(name: Component): ItemBuilder {
        val unitalizedName = name.decoration(TextDecoration.ITALIC, false)
        return name(unitalizedName)
    }

    /**
     * Adds a line of lore to the [ItemStack]
     *
     * @param lore
     * String you want to add to the ItemStack's lore.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun lore(lore: String): ItemBuilder {
        return lore(Component.text(lore))
    }

    fun lore(lore: Component): ItemBuilder {
        var lores = meta().lore()
        if (lores == null) {
            lores = ArrayList()
        }
        lores.add(lore)
        meta().lore(lores)
        make().itemMeta = meta()
        return this
    }

    /**
     * Clears the [ItemStack]s lore and replaces it with the defined String array.
     *
     * @param lores
     * String array you want to set the ItemStack's lore to.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun lores(lores: Array<String>): ItemBuilder {
        return lores(lores.map { Component.text(it) }.toTypedArray())
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun lores(lores: Array<Component>): ItemBuilder {
        var loresList = meta().lore()
        if (loresList == null) loresList = arrayListOf()

        Collections.addAll(loresList, *lores)

        meta().lore(loresList)
        return this
    }

    fun unitalizedLores(lores: Array<Component>): ItemBuilder {
        val unitalizedLores = lores.map {
            it.decoration(TextDecoration.ITALIC, false)
        }.toTypedArray()
        return lores(unitalizedLores)
    }

    /**
     * Changes the durability of the current [ItemStack]
     * Durability - number of uses left for the tool
     *
     * @param durability
     * the new int amount to set the ItemStack's durability to.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun durability(durability: Int): ItemBuilder {
        val item = make()
        (item as Damageable).damage = item.type.maxDurability - durability
        return this
    }

    /**
     * Adds and UnsafeEnchantment to the [ItemStack] with a defined level int value.
     *
     * @param enchantment
     * the [Enchantment] to add to the ItemStack.
     *
     * @param level
     * the int amount that the Enchantment's level will be set to.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun enchantment(enchantment: Enchantment, level: Int): ItemBuilder {
        make().addUnsafeEnchantment(enchantment, level)
        return this
    }

    /**
     * Adds and UnsafeEnchantment to the {@Link} with a level int value of 1.
     *
     * @param enchantment
     * the [Enchantment] to add to the ItemStack.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun enchantment(enchantment: Enchantment): ItemBuilder {
        make().addUnsafeEnchantment(enchantment, 1)
        return this
    }

    /**
     * Clears all [Enchantment]s from the current [ItemStack] then adds the defined array of Enchantments to the ItemStack.
     *
     * @param enchantments
     * the Enchantment array to replace any current enchantments applied on the ItemStack.
     *
     * @param level
     * the int level value for all Enchantments to be set to.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun enchantments(enchantments: Array<Enchantment>, level: Int): ItemBuilder {
        make().enchantments.clear()
        for (enchantment in enchantments) {
            make().addUnsafeEnchantment(enchantment, level)
        }
        return this
    }

    /**
     * Clears all [Enchantment]s from the current [ItemStack] then adds the defined array of Enchantments to the ItemStack with a level int value of 1.
     *
     * @param enchantments
     * the Enchantment array to replace any current enchantments applied on the ItemStack.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun enchantments(enchantments: Array<Enchantment>): ItemBuilder {
        make().enchantments.clear()
        for (enchantment in enchantments) {
            make().addUnsafeEnchantment(enchantment, 1)
        }
        return this
    }

    /**
     * Clears the defined [Enchantment] from the [ItemStack]
     *
     * @param enchantment
     * the Enchantment to remove from the ItemStack.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun clearEnchantment(enchantment: Enchantment): ItemBuilder {
        val itemEnchantments = make().enchantments
        for (enchantmentC in itemEnchantments.keys) {
            if (enchantment === enchantmentC) {
                itemEnchantments.remove(enchantmentC)
            }
        }
        return this
    }

    /**
     * Clears all [Enchantment]s from the [ItemStack]
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun clearEnchantments(): ItemBuilder {
        make().enchantments.clear()
        return this
    }

    /**
     * Clears the defined [String] of lore from the [ItemStack]
     *
     * @param lore
     * the String to be removed from the ItemStack.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun clearLore(lore: String): ItemBuilder {
        val componentLore = Component.text(lore)
        if (meta().lore()!!.contains(componentLore)) {
            meta().lore()!!.remove(componentLore)
        }
        make().itemMeta = meta()
        return this
    }

    /**
     * Clears all lore [String]s from the [ItemStack]
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun clearLores(): ItemBuilder {
        meta().lore()!!.clear()
        make().itemMeta = meta()
        return this
    }

    /**
     * Sets the [Color] of any LEATHER_ARMOR [Material] types of the [ItemStack]
     *
     * @param color
     * the Color to set the LEATHER_ARMOR ItemStack to.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun color(color: Color): ItemBuilder {
        if (make().type == Material.LEATHER_HELMET || make().type == Material.LEATHER_CHESTPLATE || make().type == Material.LEATHER_LEGGINGS || make().type == Material.LEATHER_BOOTS) {
            val meta = meta() as LeatherArmorMeta
            meta.setColor(color)
            make().itemMeta = meta
        }
        return this
    }

    /**
     * Clears the [Color] of any LEATHER_ARMOR [Material] types of the [ItemStack]
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    fun clearColor(): ItemBuilder {
        if (make().type == Material.LEATHER_HELMET || make().type == Material.LEATHER_CHESTPLATE || make().type == Material.LEATHER_LEGGINGS || make().type == Material.LEATHER_BOOTS) {
            val meta = meta() as LeatherArmorMeta
            meta.setColor(null)
            make().itemMeta = meta
        }
        return this
    }

    /**
     * Sets the custom texture of [Material.PLAYER_HEAD] of type [ItemStack]
     *
     * To get a texture you must visit site https://minecraft-heads.com
     * And click on your custom head then scroll down
     * And get a code named "Minecraft-URL"
     *
     * @param minecraftSkinUrl
     * the [String] value to set the custom texture meta for the PLAYER_HEAD Material type ItemStack.
     *
     * @return the current instance for chainable application
     * @since 1.0
     */
    fun playerHead(minecraftSkinUrl: String?): ItemBuilder {
        if (this.item.type != Material.PLAYER_HEAD)
            throw UnsupportedOperationException("You need to set player head for this function to work")

        val meta: SkullMeta = this.meta() as SkullMeta

        var texture = minecraftSkinUrl
        texture = "https://textures.minecraft.net/texture/$texture"
        if (texture.isEmpty()) {
            return this
        }
        val encodedData: ByteArray =
            Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).toByteArray())

        val profile: PlayerProfile = Bukkit.createProfileExact(UUID.randomUUID(), "Skull")
        profile.properties.add(ProfileProperty("textures", String(encodedData)))

        meta.playerProfile = profile
        this.item.itemMeta = meta

        return this
    }

    fun customTexture(textureId: String): ItemBuilder {
        var texture = textureId
        texture = "https://textures.minecraft.net/texture/$texture"
        if (texture.isEmpty()) {
            return this
        }
        val skullMeta: SkullMeta = this.meta() as SkullMeta
        val profile = Bukkit.createProfile(UUID.randomUUID(), null)
        val encodedData: ByteArray =
            Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).toByteArray())
        profile.properties.add(ProfileProperty("textures", String(encodedData)))
        var profileField: Field? = null
        try {
            profileField = skullMeta.javaClass.getDeclaredField("profile")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        assert(profileField != null)
        profileField!!.isAccessible = true
        try {
            profileField[skullMeta] = profile
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        make().setItemMeta(skullMeta)
        return this
    }

    /**
     * Returns the [ItemMeta] of the [ItemStack]
     *
     * @return the ItemMeta of the ItemStack.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun meta(): ItemMeta {
        return itemMeta
    }

    /**
     * Returns the [ItemStack] of the [ItemBuilder] instance.
     *
     * @return the ItemStack of the ItemBuilder instance.
     */
    fun make(): ItemStack {
        item.itemMeta = itemMeta
        return item
    }

    private val pdcTypeMap = mapOf<KClass<*>, PersistentDataType<*, *>>(
        Byte::class to PersistentDataType.BYTE,
        Short::class to PersistentDataType.SHORT,
        Int::class to PersistentDataType.INTEGER,
        Long::class to PersistentDataType.LONG,
        Float::class to PersistentDataType.FLOAT,
        Double::class to PersistentDataType.DOUBLE,
        String::class to PersistentDataType.STRING,
        ByteArray::class to PersistentDataType.BYTE_ARRAY,
        arrayOf<Int>()::class to PersistentDataType.INTEGER_ARRAY,
        arrayOf<Long>()::class to PersistentDataType.LONG_ARRAY,
        arrayOf<PersistentDataContainer>()::class to PersistentDataType.TAG_CONTAINER_ARRAY,
        PersistentDataContainer::class to PersistentDataType.TAG_CONTAINER
    )

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> setPDCKeyValue(key: NamespacedKey, value: T) {
        val pdcType = pdcTypeMap.entries
            .firstOrNull { it.key.isInstance(value) }
            ?.value as? PersistentDataType<T, T>
            ?: throw Exception("No PersistentDataType found for type ${value::class.simpleName}")

        val meta = meta()
        val pdc = meta.persistentDataContainer
        pdc.set(key, pdcType, value)
        this.item.itemMeta = meta
    }

    fun <T> getPDCKeyValue(key: NamespacedKey, pdt: PersistentDataType<T, T>): T? {
        val meta = meta()
        val pdc = meta.persistentDataContainer
        return pdc.get(key, pdt)
    }
}
