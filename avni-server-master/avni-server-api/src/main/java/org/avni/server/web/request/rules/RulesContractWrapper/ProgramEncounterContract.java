package org.avni.server.web.request.rules.RulesContractWrapper;

import org.avni.server.web.request.EntityTypeContract;
import org.avni.server.web.request.ObservationModelContract;
import org.joda.time.DateTime;
import org.avni.server.web.request.rules.request.RuleRequestEntity;

import java.util.ArrayList;
import java.util.List;

public class ProgramEncounterContract implements RuleServerEntityContract {
    private List<ObservationModelContract> observations = new ArrayList<>();
    private RuleRequestEntity rule;
    private ProgramEnrolmentContract programEnrolment;
    private List<VisitSchedule> visitSchedules;
    private String name;
    private String uuid;
    private DateTime cancelDateTime;
    private DateTime earliestVisitDateTime;
    private DateTime maxVisitDateTime;
    private List<ObservationModelContract> cancelObservations = new ArrayList<>();
    private EntityTypeContract encounterType;
    private DateTime encounterDateTime;
    private boolean voided;
    private List<EntityApprovalStatusWrapper> entityApprovalStatuses;

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    public List<EntityApprovalStatusWrapper> getEntityApprovalStatuses() {
        return entityApprovalStatuses;
    }

    public void setEntityApprovalStatuses(List<EntityApprovalStatusWrapper> entityApprovalStatuses) {
        this.entityApprovalStatuses = entityApprovalStatuses;
    }

    public DateTime getEncounterDateTime() {
        return encounterDateTime;
    }

    public void setEncounterDateTime(DateTime encounterDateTime) {
        this.encounterDateTime = encounterDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DateTime getCancelDateTime() {
        return cancelDateTime;
    }

    public void setCancelDateTime(DateTime cancelDateTime) {
        this.cancelDateTime = cancelDateTime;
    }

    public DateTime getEarliestVisitDateTime() {
        return earliestVisitDateTime;
    }

    public void setEarliestVisitDateTime(DateTime earliestVisitDateTime) {
        this.earliestVisitDateTime = earliestVisitDateTime;
    }

    public DateTime getMaxVisitDateTime() {
        return maxVisitDateTime;
    }

    public void setMaxVisitDateTime(DateTime maxVisitDateTime) {
        this.maxVisitDateTime = maxVisitDateTime;
    }

    public List<ObservationModelContract> getCancelObservations() {
        return cancelObservations;
    }

    public void setCancelObservations(List<ObservationModelContract> cancelObservations) {
        this.cancelObservations = cancelObservations;
    }

    public EntityTypeContract getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(EntityTypeContract encounterType) {
        this.encounterType = encounterType;
    }

    public List<VisitSchedule> getVisitSchedules() {
        return visitSchedules;
    }

    public void setVisitSchedules(List<VisitSchedule> visitSchedules) {
        this.visitSchedules = visitSchedules;
    }

    public ProgramEnrolmentContract getProgramEnrolment() {
        return programEnrolment;
    }

    public void setProgramEnrolment(ProgramEnrolmentContract programEnrolment) {
        this.programEnrolment = programEnrolment;
    }

    public List<ObservationModelContract> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationModelContract> observations) {
        this.observations = observations;
    }

    public RuleRequestEntity getRule() {
        return rule;
    }

    public void setRule(RuleRequestEntity rule) {
        this.rule = rule;
    }
}
