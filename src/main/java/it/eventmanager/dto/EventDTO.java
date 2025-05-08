package it.eventmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.*;
import io.smallrye.common.constraint.NotNull;
import it.eventmanager.entities.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDTO {

    @NotNull
    public String name;

    @NotNull
    public String description;

    @NotNull
    public String location;

    @NotNull
    public Set<User> participants;

    @NotNull
    public Date startDate;

    @NotNull
    public Date endDate;

    @NotNull
    public Integer numberParticipants;

    @NotNull
    public User user;


    @Override
    public String toString() {
        return "Event name = " + name + "Event Description =  " + description + "Number of participants=  " + numberParticipants + "List of Users attendance =  " +  participants;
    }

}
