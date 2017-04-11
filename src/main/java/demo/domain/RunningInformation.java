package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jmx.snmp.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

/**
 * Created by vagrant on 4/10/17.
 */
@Entity
@Table(name = "RUNNING_INFO")
@Getter
@NoArgsConstructor
//default constructor is compulsory for Hibernate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunningInformation {
    enum HealthWarningLevel {
        LOW, NORMAL, HIGH;
    }
    @Id @GeneratedValue private String runningId;
    private double latitude;
    private double longitude;
    private double runningDistance;
    private double totalRunningTime;
    private int heartRate;
    private Timestamp timestamp;
    @Embedded private UserInfo userInfo;
    private HealthWarningLevel healthWarningLevel;

    @JsonCreator
    public RunningInformation(@JsonProperty("runningId") String runningId,
                              @JsonProperty("latitude") double latitude,
                              @JsonProperty("longitude") double longitude,
                              @JsonProperty("runningDistance") double runningDistance,
                              @JsonProperty("totalRunningTime") double totalRunningTime,
                              @JsonProperty("heartRate") int heartRate,
                              @JsonProperty("timestamp") Timestamp timestamp,
                              @JsonProperty("userInfo") UserInfo userInfo) {
        this.runningId = runningId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.runningDistance = runningDistance;
        this.totalRunningTime = totalRunningTime;

        //this.heartRate = heartRate;
        //delete this when release
        //auto generated a random number between [60, 200)
        if(heartRate == 0) {
            Random rand = new Random();
            this.heartRate = rand.nextInt(140) + 60;
        }

        this.timestamp = timestamp;
        this.userInfo = userInfo;
        init();
    }

    private void init() {
        //this.timestamp = new Timestamp(System.currentTimeMillis());
        if(heartRate >= 60 && heartRate <= 75) {
            healthWarningLevel = HealthWarningLevel.LOW;
        } else if (heartRate > 75 && heartRate <= 120) {
            healthWarningLevel = HealthWarningLevel.NORMAL;
        } else {
            healthWarningLevel = HealthWarningLevel.HIGH;
        }
    }
}
