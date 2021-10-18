package com.tech.app.functions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Updater
{
    private final URL urlRepo = new URL("https://github.com/vykio/java-rdp/releases/latest");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final int initialDelay = 0;
    private static final int delay = 2;

    public Updater() throws MalformedURLException
    {

    }

    private String getPageData() throws IOException
    {
        HttpURLConnection conn = (HttpURLConnection) urlRepo.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new IOException();
        }

        StringBuilder inline = new StringBuilder();
        Scanner scanner = new Scanner(urlRepo.openStream());
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        return inline.toString();
    }

    public String getInternetVersion() throws IOException
    {
        String data = getPageData();

        Pattern r = Pattern.compile("v....\\...\\...-....");
        Matcher m = r.matcher(data);
        if(!m.find()) {
            return "NOT FOUND";
        }
        return m.group(0);
    }

    public boolean isUpdatable() throws IOException
    {


        //String internetVersion = getInternetVersion();
        String internetVersion = "v2222.22.22-2222";

        System.out.println("upgradable: " + FUtils.Program.isUpgradable(internetVersion));
        return FUtils.Program.isUpgradable(internetVersion);

    }

    public boolean downloadUpdate() throws IOException
    {
        SSLFix.execute();
        String internetVersion = getInternetVersion();
        URL url = new URL("https://github.com/vykio/java-rdp/releases/download/" + internetVersion + "/jrdp-" + internetVersion + ".windows-latest.zip");
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());

        new FileOutputStream("update.zip")
                .getChannel()
                .transferFrom(
                        rbc,
                        0,
                        Long.MAX_VALUE
                );
        return true;
    }

}
