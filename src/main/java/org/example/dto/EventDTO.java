package org.example.dto;

import java.util.List;

public class EventDTO {
    public String name;
    public String location;
    public int participants;
    public Long creatorId;
    public List<Long> attendeeIds;

    public EventDTO() {}

    public EventDTO(String name, String location, int participants, Long creatorId, List<Long> attendeeIds) {
        this.name = name;
        this.location = location;
        this.participants = participants;
        this.creatorId = creatorId;
        this.attendeeIds = attendeeIds;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public List<Long> getAttendeeIds() {
        return attendeeIds;
    }

    public void setAttendeeIds(List<Long> attendeeIds) {
        this.attendeeIds = attendeeIds;
    }
}
