package co.za.sbk.service.dto;

import co.za.sbk.domain.enumeration.IncidentStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link co.za.sbk.domain.Incident} entity.
 */
public class IncidentDTO implements Serializable {

    private Long id;

    private ZonedDateTime startDate;

    private IncidentStatus incidentStatus;

    private String location;

    private String description;

    private Set<OfficerDTO> officers = new HashSet<>();

    private Set<SuspectDTO> suspects = new HashSet<>();

    private Long userId;

    private Long incidentTypesId;

    private String incidentTypesType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<OfficerDTO> getOfficers() {
        return officers;
    }

    public void setOfficers(Set<OfficerDTO> officers) {
        this.officers = officers;
    }

    public Set<SuspectDTO> getSuspects() {
        return suspects;
    }

    public void setSuspects(Set<SuspectDTO> suspects) {
        this.suspects = suspects;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIncidentTypesId() {
        return incidentTypesId;
    }

    public void setIncidentTypesId(Long incidentTypesId) {
        this.incidentTypesId = incidentTypesId;
    }

    public String getIncidentTypesType() {
        return incidentTypesType;
    }

    public void setIncidentTypesType(String incidentTypesType) {
        this.incidentTypesType = incidentTypesType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentDTO incidentDTO = (IncidentDTO) o;
        if (incidentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", incidentStatus='" + getIncidentStatus() + "'" +
            ", location='" + getLocation() + "'" +
            ", description='" + getDescription() + "'" +
            ", user=" + getUserId() +
            ", incidentTypes=" + getIncidentTypesId() +
            ", incidentTypes='" + getIncidentTypesType() + "'" +
            "}";
    }
}
