//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.commands.packet.factory.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.network.play.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.impl.util.mcp.*;
import io.netty.buffer.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import io.netty.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.commands.packet.factory.playerlistheaderfooter.*;
import me.earth.earthhack.impl.commands.packet.factory.playerlistitem.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.potion.*;
import net.minecraft.item.crafting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.util.text.*;
import net.minecraft.world.storage.*;
import net.minecraft.advancements.*;
import java.util.*;
import javax.crypto.*;
import java.security.*;
import net.minecraft.world.border.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.network.datasync.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.array.*;
import me.earth.earthhack.impl.commands.packet.generic.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.commands.packet.arguments.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.*;
import java.lang.reflect.*;

public class PacketCommandImpl extends Command implements Globals, PacketCommand
{
    private final Map<Class<? extends Packet<?>>, List<GenericArgument<?>>> generics;
    private final Map<Class<? extends Packet<?>>, PacketFactory> custom;
    private final Set<Class<? extends Packet<?>>> packets;
    private final Map<Class<?>, PacketArgument<?>> arguments;
    private final PacketFactory default_factory;
    
    public PacketCommandImpl() {
        super(new String[][] { { "packet" }, { "packet" }, { "index" }, { "arguments" } });
        CommandDescriptions.register(this, "Send/receive packets.");
        this.custom = new HashMap<Class<? extends Packet<?>>, PacketFactory>();
        this.generics = new HashMap<Class<? extends Packet<?>>, List<GenericArgument<?>>>();
        this.arguments = new HashMap<Class<?>, PacketArgument<?>>();
        this.packets = new HashSet<Class<? extends Packet<?>>>();
        this.default_factory = new DefaultFactory(this);
        this.setup();
    }
    
    @Override
    public void execute(final String[] args) {
        if (args == null || args.length == 1) {
            ChatUtil.sendMessage("<PacketCommand> Use this command to send/receive a Packet. Remember to maybe escape your arguments with \", Arrays and Collection arguments are seperated by ], Map.Entries by ). This command should only be used if you know what you are doing!");
            return;
        }
        if (PacketCommandImpl.mc.player == null || PacketCommandImpl.mc.world == null) {
            ChatUtil.sendMessage("§cThis command can only be used while ingame!");
            return;
        }
        final Class<? extends Packet<?>> packetType = this.getPacket(args[1]);
        if (packetType == null) {
            ChatUtil.sendMessage("§cCouldn't find packet: §f" + args[1] + "§c" + "!");
            return;
        }
        final Type type = this.getNetHandlerType(packetType);
        if (type != INetHandlerPlayClient.class && type != INetHandlerPlayServer.class) {
            ChatUtil.sendMessage("§cPacket §f" + packetType.getName() + "§c" + " has unknown NetHandler type: " + "§f" + type + "§c" + "!");
            return;
        }
        if (args.length == 2) {
            ChatUtil.sendMessage("§cPlease specify a constructor index!");
            return;
        }
        final PacketFactory gen = this.custom.getOrDefault(packetType, this.default_factory);
        Packet<?> packet;
        try {
            packet = gen.create(packetType, args);
        }
        catch (ArgParseException e) {
            ChatUtil.sendMessage("§c" + e.getMessage());
            return;
        }
        if (packet == null) {
            ChatUtil.sendMessage("§cPacket for §f" + MappingProvider.simpleName(packetType) + "§c" + " was null?!");
            return;
        }
        if (type == INetHandlerPlayServer.class) {
            ChatUtil.sendMessage("§aSending packet §f" + MappingProvider.simpleName(packetType) + "§a" + " to server!");
            try {
                PacketCommandImpl.mc.player.connection.sendPacket((Packet)packet);
            }
            catch (Throwable t) {
                ChatUtil.sendMessage("§cAn error occurred while sending packet §f" + MappingProvider.simpleName(packetType) + "§c" + ": " + t.getMessage());
                t.printStackTrace();
            }
        }
        else {
            ChatUtil.sendMessage("§aAttempting to receive packet §f" + MappingProvider.simpleName(packetType) + "§a" + "!");
            final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            final List<Object> rs = BufferUtil.saveReleasableFields(packet);
            try {
                packet.writePacketData(buffer);
                packet.readPacketData(buffer);
                if (!NetworkUtil.receive((Packet<INetHandlerPlayClient>)packet)) {
                    ChatUtil.sendMessage("§cThe packet §f" + MappingProvider.simpleName(packetType) + "§c" + " got cancelled!");
                }
            }
            catch (Throwable t2) {
                ChatUtil.sendMessage("§cAn error occurred while receiving packet §f" + MappingProvider.simpleName(packetType) + "§c" + ": " + t2.getMessage());
                t2.printStackTrace();
            }
            finally {
                BufferUtil.release(rs);
                BufferUtil.releaseFields(packet);
                BufferUtil.releaseBuffer((ReferenceCounted)buffer);
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (PacketCommandImpl.mc.world == null || PacketCommandImpl.mc.player == null) {
            return inputs.setRest("§c <This command can only be used while ingame!>");
        }
        if (args.length <= 1 || args[1].isEmpty()) {
            return super.getPossibleInputs(args);
        }
        final Class<? extends Packet<?>> packet = this.getPacket(args[1]);
        if (packet == null) {
            return inputs.setRest("§c not found!");
        }
        final PacketFactory factory = this.custom.getOrDefault(packet, this.default_factory);
        return factory.getInputs(packet, args);
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        final String[] args = completer.getArgs();
        if (args != null && args.length >= 2) {
            final Class<? extends Packet<?>> p = this.getPacket(args[1]);
            final PacketFactory factory = this.custom.getOrDefault(p, this.default_factory);
            switch (factory.onTabComplete(completer)) {
                case RETURN: {
                    return completer;
                }
                case SUPER: {
                    return super.onTabComplete(completer);
                }
            }
        }
        if (completer.isSame()) {
            return completer;
        }
        return super.onTabComplete(completer);
    }
    
    public void addGeneric(final Class<? extends Packet<?>> type, final GenericArgument<?> argument) {
        this.generics.computeIfAbsent(type, v -> new ArrayList()).add(argument);
    }
    
    public <T extends Packet<?>> void addCustom(final Class<T> type, final PacketFactory factory) {
        this.custom.put(type, factory);
    }
    
    public <T> void addArgument(final Class<T> type, final PacketArgument<T> argument) {
        this.arguments.put(type, argument);
    }
    
    public void addPacket(final Class<? extends Packet<?>> packet) {
        this.packets.add(packet);
    }
    
    @Override
    public Class<? extends Packet<?>> getPacket(final String name) {
        for (final Class<? extends Packet<?>> packet : this.packets) {
            if (TextUtil.startsWith(this.getName(packet), name)) {
                return packet;
            }
        }
        return null;
    }
    
    @Override
    public Map<Class<? extends Packet<?>>, List<GenericArgument<?>>> getGenerics() {
        return this.generics;
    }
    
    @Override
    public Map<Class<? extends Packet<?>>, PacketFactory> getCustom() {
        return this.custom;
    }
    
    @Override
    public Set<Class<? extends Packet<?>>> getPackets() {
        return this.packets;
    }
    
    @Override
    public Map<Class<?>, PacketArgument<?>> getArguments() {
        return this.arguments;
    }
    
    @Override
    public String getName(final Class<? extends Packet<?>> packet) {
        String simpleName = MappingProvider.simpleName(packet);
        if (packet.getSuperclass() != Object.class) {
            simpleName = MappingProvider.simpleName(packet.getSuperclass()) + "-" + simpleName;
        }
        return simpleName;
    }
    
    private void setup() {
        this.addCustom((Class<Packet>)SPacketPlayerListHeaderFooter.class, new SPacketPlayerListHeaderFooterFactory(this));
        this.addCustom((Class<Packet>)SPacketPlayerListItem.class, new SPacketPlayerListItemFactory(this));
        this.addArgument(Boolean.TYPE, new BooleanArgument());
        this.addArgument(Integer.TYPE, new IntArgument());
        this.addArgument(Float.TYPE, new FloatArgument());
        this.addArgument(Short.TYPE, new ShortArgument());
        this.addArgument(Long.TYPE, new LongArgument());
        this.addArgument(Double.TYPE, new DoubleArgument());
        this.addArgument(Byte.TYPE, new ByteArgument());
        this.addArgument(String.class, new StringArgument());
        this.addArgument(BlockPos.class, new BlockPosArgument());
        this.addArgument(Vec3d.class, new Vec3dArgument());
        this.addArgument(Chunk.class, new ChunkArgument());
        this.addArgument(UUID.class, new UUIDArgument());
        this.addArgument(GameProfile.class, new GameProfileArgument());
        this.addArgument(ResourceLocation.class, new ResourceLocationArgument());
        this.addArgument(NBTTagCompound.class, new NBTTagCompoundArgument());
        this.addArgument(World.class, new WorldArgument());
        this.addArgument(ITextComponent.class, new TextComponentArgument());
        this.addArgument(PacketBuffer.class, new PacketBufferArgument());
        this.addArgument(Item.class, new ItemArgument());
        this.addArgument(ItemStack.class, new ItemStackArgument());
        this.addArgument(Block.class, new BlockArgument());
        this.addArgument(Potion.class, new PotionArgument());
        this.addArgument(PotionEffect.class, new PotionEffectArgument());
        this.addArgument(WorldType.class, new WorldTypeArgument());
        this.addArgument(SoundEvent.class, new SoundEventArgument());
        this.addArgument(PlayerCapabilities.class, new PlayerCapabilitiesArgument());
        this.addArgument(IRecipe.class, new RecipeArgument());
        this.addArgument(Entity.class, new EntityArgument());
        this.addArgument(EntityXPOrb.class, new EntityXPOrbArgument());
        this.addArgument(EntityPlayer.class, new EntityPlayerArgument());
        this.addArgument(EntityPainting.class, new EntityPaintingArgument());
        this.addArgument(EntityLivingBase.class, new EntityLivingBaseArgument());
        this.addArgument(IAttributeInstance.class, new AttributeArgument());
        this.addArgument((Class<Object>)SPacketPlayerListItem.Action.class, new EnumArgument<Object>((Class<Object>)SPacketPlayerListItem.Action.class));
        this.addArgument((Class<Object>)SPacketWorldBorder.Action.class, new EnumArgument<Object>((Class<Object>)SPacketWorldBorder.Action.class));
        this.addArgument((Class<Object>)SPacketUpdateBossInfo.Operation.class, new EnumArgument<Object>((Class<Object>)SPacketUpdateBossInfo.Operation.class));
        this.addArgument((Class<Object>)SPacketCombatEvent.Event.class, new EnumArgument<Object>((Class<Object>)SPacketCombatEvent.Event.class));
        this.addArgument((Class<Object>)SPacketRecipeBook.State.class, new EnumArgument<Object>((Class<Object>)SPacketRecipeBook.State.class));
        this.addArgument((Class<Object>)SPacketTitle.Type.class, new EnumArgument<Object>((Class<Object>)SPacketTitle.Type.class));
        this.addArgument((Class<Object>)EntityEquipmentSlot.class, new EnumArgument<Object>((Class<Object>)EntityEquipmentSlot.class));
        this.addArgument((Class<Object>)EnumDifficulty.class, new EnumArgument<Object>((Class<Object>)EnumDifficulty.class));
        this.addArgument((Class<Object>)EnumParticleTypes.class, new EnumArgument<Object>((Class<Object>)EnumParticleTypes.class));
        this.addArgument((Class<Object>)SoundCategory.class, new EnumArgument<Object>((Class<Object>)SoundCategory.class));
        this.addArgument((Class<Object>)EnumConnectionState.class, new EnumArgument<Object>((Class<Object>)EnumConnectionState.class));
        this.addArgument((Class<Object>)CPacketClientStatus.State.class, new EnumArgument<Object>((Class<Object>)CPacketClientStatus.State.class));
        this.addArgument((Class<Object>)CPacketEntityAction.Action.class, new EnumArgument<Object>((Class<Object>)CPacketEntityAction.Action.class));
        this.addArgument((Class<Object>)CPacketPlayerDigging.Action.class, new EnumArgument<Object>((Class<Object>)CPacketPlayerDigging.Action.class));
        this.addArgument((Class<Object>)CPacketResourcePackStatus.Action.class, new EnumArgument<Object>((Class<Object>)CPacketResourcePackStatus.Action.class));
        this.addArgument((Class<Object>)CPacketSeenAdvancements.Action.class, new EnumArgument<Object>((Class<Object>)CPacketSeenAdvancements.Action.class));
        this.addArgument((Class<Object>)EnumFacing.class, new EnumArgument<Object>((Class<Object>)EnumFacing.class));
        this.addArgument((Class<Object>)ClickType.class, new EnumArgument<Object>((Class<Object>)ClickType.class));
        this.addArgument((Class<Object>)EnumHandSide.class, new EnumArgument<Object>((Class<Object>)EnumHandSide.class));
        this.addArgument((Class<Object>)EntityPlayer.EnumChatVisibility.class, new EnumArgument<Object>((Class<Object>)EntityPlayer.EnumChatVisibility.class));
        this.addArgument((Class<Object>)EnumHand.class, new EnumArgument<Object>((Class<Object>)EnumHand.class));
        this.addArgument((Class<Object>)ChatType.class, new EnumArgument<Object>((Class<Object>)ChatType.class));
        this.addArgument((Class<Object>)GameType.class, new EnumArgument<Object>((Class<Object>)GameType.class));
        this.addArgument(MapDecoration.class, new MapDecorationArgument());
        this.addArgument(Advancement.class, new AdvancementArgument());
        this.addArgument(NonNullList.class, new NonNullListArgument());
        this.addArgument(Map.class, new MapArgument());
        this.addArgument(List.class, new ListArgument());
        this.addArgument(Collection.class, new CollectionArgument());
        this.addArgument(Set.class, new SetArgument());
        this.addArgument(Iterable.class, new IterableArgument());
        this.addArgument(SecretKey.class, new SecretKeyArgument());
        this.addArgument(PublicKey.class, new PublicKeyArgument());
        this.addArgument(WorldBorder.class, new WorldBorderArgument());
        this.addArgument(BossInfo.class, new BossInfoArgument());
        this.addArgument(ScoreObjective.class, new ScoreObjectiveArgument());
        this.addArgument(CombatTracker.class, new CombatTrackerArgument());
        this.addArgument(EntityDataManager.class, new EntityDataMangerArgument());
        this.addArgument(Score.class, new ScoreArgument());
        this.addArgument(ScorePlayerTeam.class, new ScorePlayerTeamArgument());
        this.addArgument(ServerStatusResponse.class, new ServerStatusResponseArgument());
        this.arguments.put(int[].class, new IntArrayArgument());
        this.arguments.put(byte[].class, new ByteArrayArgument());
        this.arguments.put(short[].class, new ShortArrayArgument());
        this.arguments.put(String[].class, new FunctionArrayArgument<Object>((Class<Object[]>)String[].class, (PacketArgument<Object>)this.getArgument(String.class), x$0 -> new String[x$0]));
        this.arguments.put(ITextComponent[].class, new FunctionArrayArgument<Object>((Class<Object[]>)ITextComponent[].class, (PacketArgument<Object>)this.getArgument(ITextComponent.class), x$0 -> new ITextComponent[x$0]));
        try {
            final Constructor<?> recipe = SPacketRecipeBook.class.getDeclaredConstructor(SPacketRecipeBook.State.class, List.class, List.class, Boolean.TYPE, Boolean.TYPE);
            this.addGeneric((Class<? extends Packet<?>>)SPacketRecipeBook.class, new GenericListArgument<Object>(recipe, 1, this.arguments.get(IRecipe.class)));
            this.addGeneric((Class<? extends Packet<?>>)SPacketRecipeBook.class, new GenericListArgument<Object>(recipe, 2, this.arguments.get(IRecipe.class)));
            final Constructor<?> teams = SPacketTeams.class.getDeclaredConstructor(ScorePlayerTeam.class, Collection.class, Integer.TYPE);
            this.addGeneric((Class<? extends Packet<?>>)SPacketTeams.class, new GenericCollectionArgument<Object>(teams, 1, this.arguments.get(String.class)));
            final Constructor<?> advancement = SPacketAdvancementInfo.class.getDeclaredConstructor(Boolean.TYPE, Collection.class, Set.class, Map.class);
            this.addGeneric((Class<? extends Packet<?>>)SPacketAdvancementInfo.class, new GenericCollectionArgument<Object>(advancement, 1, this.arguments.get(Advancement.class)));
            this.addGeneric((Class<? extends Packet<?>>)SPacketAdvancementInfo.class, new GenericSetArgument<Object>(advancement, 2, this.arguments.get(ResourceLocation.class)));
            this.addGeneric((Class<? extends Packet<?>>)SPacketAdvancementInfo.class, new GenericMapArgument<Object, Object, Object>(HashMap.class, advancement, 3, new ResourceLocationArgument(), new AdvancementProgressArgument()));
            final Constructor<?> properties = SPacketEntityProperties.class.getDeclaredConstructor(Integer.TYPE, Collection.class);
            this.addGeneric((Class<? extends Packet<?>>)SPacketEntityProperties.class, new GenericCollectionArgument<Object>(properties, 1, this.arguments.get(IAttributeInstance.class)));
            final Constructor<?> explosion = SPacketExplosion.class.getDeclaredConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, List.class, Vec3d.class);
            this.addGeneric((Class<? extends Packet<?>>)SPacketExplosion.class, new GenericListArgument<Object>(explosion, 4, this.arguments.get(BlockPos.class)));
            final Constructor<?> posLook = SPacketPlayerPosLook.class.getDeclaredConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Set.class, Integer.TYPE);
            this.addGeneric((Class<? extends Packet<?>>)SPacketPlayerPosLook.class, new GenericSetArgument<Object>(posLook, 5, new EnumArgument<Object>(SPacketPlayerPosLook.EnumFlags.class)));
            final Constructor<?> windowItems = SPacketWindowItems.class.getDeclaredConstructor(Integer.TYPE, NonNullList.class);
            this.addGeneric((Class<? extends Packet<?>>)SPacketWindowItems.class, new GenericNonNullListArgument<Object>(windowItems, 1, this.arguments.get(ItemStack.class)));
            final Constructor<?> maps = SPacketMaps.class.getDeclaredConstructor(Integer.TYPE, Byte.TYPE, Boolean.TYPE, Collection.class, byte[].class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            this.addGeneric((Class<? extends Packet<?>>)SPacketMaps.class, new GenericCollectionArgument<Object>(maps, 3, this.arguments.get(MapDecoration.class)));
            final Constructor<?> statistics = SPacketStatistics.class.getDeclaredConstructor(Map.class);
            this.addGeneric((Class<? extends Packet<?>>)SPacketStatistics.class, new GenericMapArgument<Object, Object, Object>(HashMap.class, statistics, 0, new StatBaseArgument(), new IntArgument()));
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException("Constructor of a packet missing: " + e.getMessage());
        }
        for (final Class<? extends Packet<?>> packet : PacketUtil.getAllPackets()) {
            final Type netHandler = this.getNetHandlerType(packet);
            if (netHandler != INetHandlerPlayClient.class && netHandler != INetHandlerPlayServer.class) {
                continue;
            }
            if (!this.custom.containsKey(packet)) {
                for (final Constructor<?> ctr : packet.getDeclaredConstructors()) {
                    for (final Class<?> type : ctr.getParameterTypes()) {
                        if (!this.arguments.containsKey(type)) {
                            Earthhack.getLogger().error("<PacketCommand> No Argument found for: " + type.getName() + " in " + packet.getName());
                        }
                    }
                }
            }
            for (final Class<? extends Packet<?>> alreadyExisting : this.packets) {
                if (alreadyExisting.getSimpleName().equals(packet.getSimpleName()) && !alreadyExisting.equals(packet)) {
                    Earthhack.getLogger().warn(alreadyExisting.getName() + " SimpleName clashes with: " + packet.getName());
                }
            }
            this.addPacket(packet);
        }
    }
    
    private <T> PacketArgument<T> getArgument(final Class<T> clazz) {
        return (PacketArgument)this.arguments.get(clazz);
    }
    
    private Type getNetHandlerType(final Class<? extends Packet<?>> packet) {
        Class<?> clazz = packet;
        do {
            final Type[] genericInterfaces;
            final Type[] types = genericInterfaces = clazz.getGenericInterfaces();
            for (final Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType && ((ParameterizedType)genericInterface).getRawType() == Packet.class) {
                    final Type[] actualTypeArguments = ((ParameterizedType)genericInterface).getActualTypeArguments();
                    final int length2 = actualTypeArguments.length;
                    final int n = 0;
                    if (n < length2) {
                        final Type type = actualTypeArguments[n];
                        return type;
                    }
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return null;
    }
}
