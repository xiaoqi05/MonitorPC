package cn.edu.cuit.monitorpc;

import java.util.Map;

import cn.edu.cuit.monitorpc.utils.Regex;

public class APInfo {

    private String ssid;
    private int lastSignal;
    private int averageSignal;
    private int detectionCounter;
    private int detectionRate;
    private boolean isSecurityEnabled;
    private boolean connectable;
    private String authentication;
    private String cipher;
    private String phyTypes;
    private String macAddress;
    private int rssi;
    private double channelFrequency;
    private int ChannelNumber;
    private int maximumSpeed;
    private boolean Connected;

    public static APInfo generateAPInfo(Map<String, String> map) {
        APInfo ret = new APInfo();
        ret.setSsid(map.get("SSID"));
        ret.setLastSignal(Integer.parseInt(Regex.matchToString(map
                .get("Last Signal"), "\\d{1,3}")));
        ret.setSecurityEnabled(map.get("Security Enabled").equals("Yes") ? true
                : false);
        ret.setConnectable(map.get("Connectable").equals("Yes") ? true : false);
        ret.setAuthentication(map.get("Authentication"));
        ret.setCipher(map.get("Cipher"));
        ret.setPhyTypes(map.get("PHY Types"));
        ret.setMacAddress(map.get("MAC Address"));
        ret.setRssi(Integer.parseInt(map.get("RSSI").length() != 0 ? map
                .get("RSSI") : "0"));
        ret.setChannelFrequency(Double.parseDouble(map
                .get("Channel Frequency (GHz)").length() != 0 ? map
                .get("Channel Frequency (GHz)") : "0"));
        ret.setChannelNumber(Integer.parseInt(map.get("Channel Number")
                .length() != 0 ? map.get("Channel Number") : "-1"));
        ret.setMaximumSpeed(Integer.parseInt(Regex.matchToString(map
                .get("Maximum Speed"), "\\d{1,4}")));
        ret.setConnected(map.get("Connected").equals("Yes") ? true : false);

        return ret;
    }

    @Override
    public String toString() {
        return "SSID: " + this.getSsid() + "\n" + "Last Signal: "
                + this.getLastSignal() + "\n" + "SecurityEnabled: "
                + this.isSecurityEnabled() + "\n" + "Connectable: "
                + this.isConnectable() + "\n" + "Authentication: "
                + this.getAuthentication() + "\n" + "Cipher: "
                + this.getCipher() + "\n" + "PhyTypes: " + this.getPhyTypes()
                + "\n" + "MacAddress: " + this.getMacAddress() + "\n"
                + "Rssi: " + this.getRssi() + "\n" + "ChannelFrequency: "
                + this.getChannelFrequency() + "\n" + "ChannelNumber: "
                + this.getChannelNumber() + "\n" + "MaximumSpeed: "
                + this.getMaximumSpeed() + " Mbps\n" + "Connected: "
                + this.isConnected() + "\n";
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getLastSignal() {
        return lastSignal;
    }

    public void setLastSignal(int lastSignal) {
        this.lastSignal = lastSignal;
    }

    public int getAverageSignal() {
        return averageSignal;
    }

    public void setAverageSignal(int averageSignal) {
        this.averageSignal = averageSignal;
    }

    public int getDetectionCounter() {
        return detectionCounter;
    }

    public void setDetectionCounter(int detectionCounter) {
        this.detectionCounter = detectionCounter;
    }

    public int getDetectionRate() {
        return detectionRate;
    }

    public void setDetectionRate(int detectionRate) {
        this.detectionRate = detectionRate;
    }

    public boolean isSecurityEnabled() {
        return isSecurityEnabled;
    }

    public void setSecurityEnabled(boolean isSecurityEnabled) {
        this.isSecurityEnabled = isSecurityEnabled;
    }

    public boolean isConnectable() {
        return connectable;
    }

    public void setConnectable(boolean connectable) {
        this.connectable = connectable;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getPhyTypes() {
        return phyTypes;
    }

    public void setPhyTypes(String phyTypes) {
        this.phyTypes = phyTypes;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public double getChannelFrequency() {
        return channelFrequency;
    }

    public void setChannelFrequency(double channelFrequency) {
        this.channelFrequency = channelFrequency;
    }

    public int getChannelNumber() {
        return ChannelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        ChannelNumber = channelNumber;
    }

    public int getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(int maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public boolean isConnected() {
        return Connected;
    }

    public void setConnected(boolean connected) {
        Connected = connected;
    }
}
