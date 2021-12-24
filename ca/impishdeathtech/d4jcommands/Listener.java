/*
    Copyright (c) Christopher Stephen Rafuse <ImpishDeathTech@protonmail.ch>
    BSD-3-Clause
*/

package ca.impishdeathtech.d4jcommands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class Listener {
    protected static Map<String, Command> commands = new HashMap<>();

    public static void initialize(final GatewayDiscordClient botClient, String prefix, String loginFmtStr) {
        System.out.println("[ INFO] (initialize) Setting up ReadyEvent listener...");
        onReady(botClient, loginFmtStr);

        System.out.println("[ INFO] (initialize) Setting up Command listener...");
        onMessageCreate(botClient, prefix);
    }

    public static void onReady(final GatewayDiscordClient botClient, String loginFmtStr) {
        botClient.getEventDispatcher().on(ReadyEvent.class)
                .flatMap(event ->
                        Mono.fromRunnable(() -> {
                            final User self = event.getSelf();
                            System.out.printf(loginFmtStr,
                                    self.getUsername(),
                                    self.getDiscriminator());
                        }))
                .subscribe();
    }

    public static void onMessageCreate(final GatewayDiscordClient botClient, String prefix) {
        botClient.getEventDispatcher().on(MessageCreateEvent.class)
                .flatMap(event -> Mono.justOrEmpty(event.getMessage().getContent())
                        .flatMap(content -> Flux.fromIterable(commands.entrySet())
                                .filter(entry -> content.startsWith(prefix.concat(entry.getKey())))
                                .flatMap(entry -> entry.getValue().execute(event))
                                .next()))
                .subscribe();
    }
}
