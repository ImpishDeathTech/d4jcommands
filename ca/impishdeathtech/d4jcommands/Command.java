/*
  Copyright (c) Christopher Stephen Rafuse <ImpishDeathTech@protonmail.ch>
  BSD-3-Clause
*/

package ca.impishdeathtech.d4jcommands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface Command {
    Mono<Void> execute(MessageCreateEvent event);
}
