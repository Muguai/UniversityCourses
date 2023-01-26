package com.example.GarbageBinTrack;

public class GarbageBin {
    private int id;

    private int timePickedUpThisMonth;


    private String topic;
    private String name;
    private String lastPickupDate;


    private float longitude;
    private float latitude;
    private float percent;
    private float averagePickupProcentThisMonth;
    private float emptyBinValue;


    private boolean calibrated;
    private boolean pickupNotification;





    public GarbageBin(int id, String topic, String name, float longitude, float latitude, float percent) {
        this.id = id;
        this.topic = topic;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.percent = percent;
        this.calibrated = false;
        this.pickupNotification = false;
        this.emptyBinValue = 0f;
        this.lastPickupDate = "No pickups";
        this.averagePickupProcentThisMonth = 0f;
        this.timePickedUpThisMonth = 0;
    }

    @Override
    public String toString() {
        return "GarbageBin{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", percent=" + percent +
                '}';
    }

    public void calculateAndSetAvgPercent(float newPercent){
        this.averagePickupProcentThisMonth = ((averagePickupProcentThisMonth * (timePickedUpThisMonth - 1)) + newPercent) / timePickedUpThisMonth;
    }


    public boolean isPickupNotification() {
        return pickupNotification;
    }

    public void setPickupNotification(boolean pickupNotification) {
        this.pickupNotification = pickupNotification;
    }


    public int getTimePickedUpThisMonth() { return timePickedUpThisMonth; }

    public void setTimePickedUpThisMonth(int timePickedUpThisMonth) { this.timePickedUpThisMonth = timePickedUpThisMonth; }

    public float getAveragePickupProcentThisMonth() { return averagePickupProcentThisMonth; }

    public void setAveragePickupProcentThisMonth(float averagePickupProcentThisMonth) { this.averagePickupProcentThisMonth = averagePickupProcentThisMonth; }

    public String getLastPickupDate() { return lastPickupDate; }

    public void setLastPickupDate(String lastPickupDate) { this.lastPickupDate = lastPickupDate; }

    public boolean isCalibrated() {
        return calibrated;
    }

    public float getEmptyBinValue() {
        return emptyBinValue;
    }

    public void setCalibrated(boolean calibrated) {
        this.calibrated = calibrated;
    }

    public void setEmptyBinValue(float emptyBinValue) {
        this.emptyBinValue = emptyBinValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
