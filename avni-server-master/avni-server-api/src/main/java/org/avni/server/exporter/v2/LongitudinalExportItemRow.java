package org.avni.server.exporter.v2;

import org.avni.server.domain.Encounter;
import org.avni.server.domain.Individual;
import org.avni.server.domain.ProgramEncounter;
import org.avni.server.domain.ProgramEnrolment;

import java.util.List;
import java.util.Map;

public class LongitudinalExportItemRow {
    private Individual individual;
    private Map<ProgramEnrolment, Map<String, List<ProgramEncounter>>> programEnrolmentToEncountersMap;
    private Map<String, List<Encounter>> encounterTypeToEncountersMap;
    private Map<Individual, Map<String, List<Encounter>>> groupSubjectToEncountersMap;

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public Map<ProgramEnrolment, Map<String, List<ProgramEncounter>>> getProgramEnrolmentToEncountersMap() {
        return programEnrolmentToEncountersMap;
    }

    public void setProgramEnrolmentToEncountersMap(Map<ProgramEnrolment, Map<String, List<ProgramEncounter>>> programEnrolmentToEncountersMap) {
        this.programEnrolmentToEncountersMap = programEnrolmentToEncountersMap;
    }

    public Map<String, List<Encounter>> getEncounterTypeToEncountersMap() {
        return encounterTypeToEncountersMap;
    }

    public void setEncounterTypeToEncountersMap(Map<String, List<Encounter>> encounterTypeToEncountersMap) {
        this.encounterTypeToEncountersMap = encounterTypeToEncountersMap;
    }

    public Map<Individual, Map<String, List<Encounter>>> getGroupSubjectToEncountersMap() {
        return groupSubjectToEncountersMap;
    }

    public void setGroupSubjectToEncountersMap(Map<Individual, Map<String, List<Encounter>>> groupSubjectToEncountersMap) {
        this.groupSubjectToEncountersMap = groupSubjectToEncountersMap;
    }
}
