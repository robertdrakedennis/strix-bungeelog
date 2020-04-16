package gg.strix.bungeechat;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

public class ChatEvent implements Listener {

    private final String webHook = "https://discordapp.com/api/webhooks/628675492794335252/F5BTJyzz5qDFQOFJ-VGtcOO7hVUoQM4vQqpz20p7QoXsvPV-4Joiq1DP0EwhtKI7I7bA";

    /**
     * Get's a player from InetSocketAddress.
     * @param addr
     * @return
     */
    private ProxiedPlayer findPlayer(InetSocketAddress addr){

        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
            if(player.getAddress().equals(addr)){
                return player;
            }
        }
        return null;
    }

    public String webhookBody(String content){

        return "{\"content\":\"" + content + "\"}";
    }

    public void sendDiscordRequest(String content){
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();


            HttpPost post = new HttpPost(this.webHook);
            String query = webhookBody(content);
            StringEntity params =new StringEntity(query);
            post.setHeader("Content-Type","application/json");
            post.setEntity(params);
            HttpResponse response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the hook for messages.
     * @param event
     */
    @EventHandler
    public void onMessage(net.md_5.bungee.api.event.ChatEvent event){
        InetSocketAddress addr = event.getSender().getAddress();
        ProxiedPlayer player = this.findPlayer(addr);
        String content = event.getMessage();
        if(!content.startsWith("/g") && !content.startsWith("!") && !content.startsWith("/global")) return;

        String message = "`[BungeeChat] <" +  player.getServer().getInfo().getName() + "> [" + player.getDisplayName() + "] :" + content + "`";
        sendDiscordRequest(message);
    }
}