# d4jcommands
a javapackage to be included in a discord4j project for people who are lazy

# Basic Example Bot.java
```java
package ca.impishdeathtech.bot;

import ca.impishdeathtech.d4jcommands.Listener;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;


public class Bot extends Listener {    
    static {
        // link up commands in here
        commands.put("ping", event -> event.getMessage()
                .getChannel()
                .flatMap(channel -> channel.createMessage(pingPong()))
                .then());
    }

    static public void main(String[] args) {
        System.out.println("[ INFO] (main) Building Discord Client...");
        
        final GatewayDiscordClient client = DiscordClientBuilder.create(args[1])
                .build()
                .login()
                .block();
        
        System.out.println("[ INFO] (main) Initializing...");
        initialize(client, "!", "Bot is alive and ready! ^,..,^");

        System.out.println("[ INFO] (main) Awaiting command...");
        client.onDisconnect().block();
    }
    
    static public String pingPong() {
        return "```Pong! ^,..,^```";
    }
}
```
