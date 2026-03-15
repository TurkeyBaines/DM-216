package com.dm;

import com.dm.content.WellOfGoodwill;
import com.dm.content.bloodmoney.BloodChestEvent;
import com.dm.content.clanchannel.ClanRepository;
import com.dm.content.itemaction.ItemActionRepository;
import com.dm.content.lms.LMSGameEvent;
import com.dm.content.lms.loadouts.LMSLoadoutManager;
import com.dm.content.lms.lobby.LMSLobbyEvent;
import com.dm.content.mysterybox.MysteryBox;
import com.dm.content.preloads.PreloadRepository;
import com.dm.content.shootingstar.ShootingStar;
import com.dm.content.skill.SkillRepository;
import com.dm.content.tradingpost.TradingPost;
import com.dm.content.triviabot.TriviaBot;
import com.dm.content.wintertodt.Wintertodt;
import com.dm.fs.cache.FileSystem;
import com.dm.fs.cache.decoder.*;
import com.dm.game.engine.GameThread;
import com.dm.game.plugin.PluginManager;
import com.dm.game.service.*;
import com.dm.game.task.impl.ClanUpdateEvent;
import com.dm.game.task.impl.DoubleExperienceEvent;
import com.dm.game.task.impl.MessageEvent;
import com.dm.game.task.impl.PlayerSaveEvent;
import com.dm.game.world.World;
import com.dm.game.world.WorldType;
import com.dm.game.world.cronjobs.Jobs;
import com.dm.game.world.entity.combat.attack.listener.CombatListenerManager;
import com.dm.game.world.entity.combat.strategy.npc.boss.skotizo.SkotizoEvent;
import com.dm.game.world.entity.mob.npc.definition.NpcDefinition;
import com.dm.game.world.entity.mob.player.BannedPlayers;
import com.dm.game.world.entity.mob.player.IPBannedPlayers;
import com.dm.game.world.entity.mob.player.IPMutedPlayers;
import com.dm.game.world.entity.mob.player.profile.ProfileRepository;
import com.dm.game.world.items.ItemDefinition;
import com.dm.io.PacketListenerLoader;
import com.dm.net.LoginExecutorService;
import com.dm.net.discord.Discord;
import com.dm.net.discord.DiscordPlugin;
import com.dm.util.GameSaver;
import com.dm.util.Stopwatch;
import com.dm.util.parser.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dm.OldToNew;
import org.dm.objectexamines.ObjectExamines;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import plugin.click.item.ClueScrollPlugin;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class Starter implements Runnable {

    private final Stopwatch uptime = Stopwatch.start();

    private final StartupService startupService = new StartupService();
    private final NetworkService networkService = new NetworkService(this);

    private final LoginExecutorService loginExecutorService =
            new LoginExecutorService(Runtime.getRuntime().availableProcessors());

    private volatile boolean started;

    private void processSequentialStatupTasks() throws IOException {
        OldToNew.load();
        try {
            //object/region decoding must be done before parallel.
            new ObjectRemovalParser().run();
            final FileSystem fs = FileSystem.create("data/cache");
            new ObjectDefinitionDecoder(fs).run();
            new MapDefinitionDecoder(fs).run();
            new RegionDecoder(fs).run();
            new AnimationDefinitionDecoder(fs).run();
            CacheNpcDefinition.unpackConfig(fs.getArchive(FileSystem.CONFIG_ARCHIVE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ItemDefinition.createParser().run();
        NpcDefinition.createParser().run();
        ObjectExamines.loadObjectExamines();
        new CombatProjectileParser().run();
        CombatListenerManager.load();
        new NpcSpawnParser().run();
        new NpcDropParser().run();
        new NpcForceChatParser().run();
        new StoreParser().run();
        new GlobalObjectParser().run();
        ShootingStar.init();
        Wintertodt.init();
    }

    /**
     * Called after the sequential startup tasks, use this for faster startup.
     * Try not to use this method for tasks that rely on other tasks or you'll run into
     * issues.
     */
    private void processParallelStatupTasks() {
        startupService.submit(new PacketSizeParser());
        startupService.submit(new PacketListenerLoader());
        startupService.submit(TriviaBot::declare);
//        startupService.submit(PersonalStoreSaver::loadPayments);
        startupService.submit(ClanRepository::loadChannels);
        //  startupService.submit(GlobalRecords::load);
        startupService.submit(SkillRepository::load);
        startupService.submit(ProfileRepository::load);
        startupService.submit(ItemActionRepository::declare);
        startupService.submit(ClueScrollPlugin::declare);
        startupService.submit(MysteryBox::load);
        startupService.submit(() -> Discord.start(this));
        startupService.submit(GameSaver::load);
        DiscordPlugin.startUp();
        startupService.submit(PreloadRepository::declare);
        startupService.submit(TradingPost::loadAllListings);
        startupService.submit(TradingPost::loadItemHistory);
        startupService.submit(TradingPost::loadRecentItemHistory);
        startupService.shutdown();
    }

    /**
     * Called when the game engine is running and all the startup tasks have finished loading
     */
    private static void onStart() {
        if (WellOfGoodwill.isActive()) {
            World.schedule(new DoubleExperienceEvent());
        }

        World.schedule(new MessageEvent());
        World.schedule(new ClanUpdateEvent());
        World.schedule(new SkotizoEvent());
        World.schedule(new PlayerSaveEvent());
//        World.schedule(new BotStartupEvent());
        World.schedule(new BloodChestEvent());
        World.schedule(new LMSLobbyEvent());
        World.schedule(new LMSGameEvent());
        logger.info("Events have been scheduled");
    }

    @Override
    public void run() {
        try {
            start();
        } catch (Throwable t) {
            logger.error("A problem has been encountered while starting the server.", t);
        }
    }

    private void start() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down server, initializing shutdown hook");
            World.save();
        }, "Shutdown Hook"));

        if (Config.FORUM_INTEGRATION) {
            ForumService.start(); // used to check users logging in with website credentials

            if (Config.WORLD_TYPE == WorldType.LIVE) {
                PostgreService.start(); // used to start the postgres connection pool
                WebsitePlayerCountService.getInstance().startAsync(); // used to display player count on website
            }
        }

        logger.info("Dead Men is running (client version " + Config.CLIENT_VERSION + ")");
        logger.info(String.format("Game Engine=%s", Config.PARALLEL_GAME_ENGINE ? "Parallel" : "Sequential"));
        processSequentialStatupTasks();
        processParallelStatupTasks();

        startupService.awaitUntilFinished(5, TimeUnit.MINUTES);
        logger.info("Startup service finished");

        LMSLoadoutManager.load();

        PluginManager.load("plugin");

        new GameThread("Game Thread", () -> {
            try {
                logger.info("Game service started");

                onStart();

                Jobs.load();

                BannedPlayers.load();
                IPBannedPlayers.load();
                IPMutedPlayers.load();

                networkService.start(Config.SERVER_PORT);
            } catch (Exception e) {
                logger.error("Failed to start game thread", e);
            }
        }).start();
    }

    public static DateTime currentDateTime() {
        return new DateTime(timeZone());
    }

    public static DateTimeZone timeZone() {
        return DateTimeZone.UTC;
    }

    public LoginExecutorService getLoginExecutorService() {
        return this.loginExecutorService;
    }

    public boolean isServerStarted() {
        return started;
    }

    public Stopwatch getUptime() {
        return uptime;
    }

    public void setServerStarted(boolean started) {
        this.started = started;
    }

    private static final Logger logger = LogManager.getLogger();

}
