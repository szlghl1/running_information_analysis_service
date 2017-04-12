package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

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
    //@GeneratedValue
    @Id private String runningId;
    private double latitude;
    private double longitude;
    private double runningDistance;
    private double totalRunningTime;
    private int heartRate;
    private Date timestamp;
    @Embedded private UserInfo userInfo;
    private HealthWarningLevel healthWarningLevel;

    @JsonCreator
    public RunningInformation(@JsonProperty("runningId") String runningId,
                              @JsonProperty("latitude") String latitude,
                              @JsonProperty("longitude") String longitude,
                              @JsonProperty("runningDistance") String runningDistance,
                              @JsonProperty("totalRunningTime") String totalRunningTime,
                              @JsonProperty("heartRate") int heartRate,
                              @JsonProperty("timestamp") @Nullable Date timestamp,
                              @JsonProperty("userInfo") UserInfo userInfo) {
        this.runningId = runningId;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.runningDistance = Double.parseDouble(runningDistance);
        this.totalRunningTime = Double.parseDouble(totalRunningTime);

        //this.heartRate = heartRate;
        //delete this when release
        //auto generated a random number between [60, 200)
        if(heartRate == 0) {
            Random rand = new Random();
            this.heartRate = rand.nextInt(140) + 60;
        }

        this.timestamp = timestamp == null ? new Date() : timestamp;
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

    @JsonIgnore
    public String getSimpleJson() {
        class Pair {
            String first;
            String second;
            Pair(String first, String second) {this.first = first; this.second = second;}
        }
        StringBuilder sb = new StringBuilder();
        List<Pair> l = new LinkedList<Pair>();
        l.add(new Pair("\"" + "runningId" + "\"", "\"" + runningId + "\""));
        l.add(new Pair("\"" + "totalRunningTime" + "\"", Double.toString(totalRunningTime)));
        l.add(new Pair("\"" + "heartRate" + "\"", Integer.toString(heartRate)));
        l.add(new Pair("\"" + "userId" + "\"", Integer.toString(userInfo.getUserId())));
        l.add(new Pair("\"" + "userName" + "\"", "\"" + userInfo.getName() + "\""));
        l.add(new Pair("\"" + "userAddress" + "\"", "\"" + userInfo.getAddress() + "\""));
        l.add(new Pair("\"" + "healthWarningLevel" + "\"", "\"" + healthWarningLevel.toString() + "\""));
        sb.append('{');
        for(int i = 0; i != l.size(); i++) {
            Pair p = l.get(i);
            sb.append(p.first);
            sb.append(':');
            sb.append(p.second);
            if(i != l.size() - 1) sb.append(',');
        }
        sb.append('}');
        return sb.toString();
    }
}
