delete from program_encounter where organisation_id = :orgId and organisation_id > 1;
delete from checklist_item where organisation_id = :orgId and organisation_id > 1;
delete from checklist where organisation_id = :orgId and organisation_id > 1;
delete from program_enrolment where organisation_id = :orgId and organisation_id > 1;
delete from individual_relationship where organisation_id = :orgId and organisation_id > 1;
delete from individual_relationship_type where organisation_id = :orgId and organisation_id > 1;
delete from individual_relative where organisation_id = :orgId and organisation_id > 1;
delete from encounter where organisation_id = :orgId and organisation_id > 1;
delete from individual where organisation_id = :orgId and organisation_id > 1;
delete from catchment_address_mapping where addresslevel_id in (select id from address_level where organisation_id = :orgId and organisation_id > 1);
delete from address_level where organisation_id = :orgId and organisation_id > 1;
delete from catchment where organisation_id = :orgId and organisation_id > 1;
delete from concept_answer where organisation_id = :orgId and organisation_id > 1;
delete from operational_encounter_type where organisation_id = :orgId and organisation_id > 1;
delete from encounter_type where organisation_id = :orgId and organisation_id > 1;
delete from non_applicable_form_element where organisation_id = :orgId and organisation_id > 1;
delete from form_element where organisation_id = :orgId and organisation_id > 1;
delete from individual_relation_gender_mapping where organisation_id = :orgId and organisation_id > 1;
delete from concept where organisation_id = :orgId and organisation_id > 1;
delete from form_element_group where organisation_id = :orgId and organisation_id > 1;
delete from form_mapping where organisation_id = :orgId and organisation_id > 1;
delete from rule where organisation_id = :orgId and organisation_id > 1;
delete from form where organisation_id = :orgId and organisation_id > 1;
delete from individual_relation where organisation_id = :orgId and organisation_id > 1;
delete from operational_program where organisation_id = :orgId and organisation_id > 1;
delete from program where organisation_id = :orgId and organisation_id > 1;
delete from rule_dependency where organisation_id = :orgId and organisation_id > 1;
delete from users where organisation_id = :orgId and organisation_id > 1;
delete from organisation where id = :orgId and id > 1;

select setval('catchment_id_seq', coalesce((select max(id)+1 from catchment), 1), false);
select setval('program_encounter_id_seq', coalesce((select max(id)+1 from program_encounter), 1), false);
select setval('checklist_item_id_seq', coalesce((select max(id)+1 from checklist_item), 1), false);
select setval('checklist_id_seq', coalesce((select max(id)+1 from checklist), 1), false);
select setval('program_enrolment_id_seq', coalesce((select max(id)+1 from program_enrolment), 1), false);
select setval('individual_relationship_id_seq', coalesce((select max(id)+1 from individual_relationship), 1), false);
select setval('individual_relationship_type_id_seq', coalesce((select max(id)+1 from individual_relationship_type), 1), false);
select setval('individual_relative_id_seq', coalesce((select max(id)+1 from individual_relative), 1), false);
select setval('encounter_id_seq', coalesce((select max(id)+1 from encounter), 1), false);
select setval('individual_id_seq', coalesce((select max(id)+1 from individual), 1), false);
select setval('catchment_address_mapping_id_seq', coalesce((select max(id)+1 from catchment_address_mapping), 1), false);
select setval('address_level_id_seq', coalesce((select max(id)+1 from address_level), 1), false);
select setval('catchment_id_seq', coalesce((select max(id)+1 from catchment), 1), false);
select setval('concept_answer_id_seq', coalesce((select max(id)+1 from concept_answer), 1), false);
select setval('operational_encounter_type_id_seq', coalesce((select max(id)+1 from operational_encounter_type), 1), false);
select setval('encounter_type_id_seq', coalesce((select max(id)+1 from encounter_type), 1), false);
select setval('non_applicable_form_element_id_seq', coalesce((select max(id)+1 from non_applicable_form_element), 1), false);
select setval('form_element_id_seq', coalesce((select max(id)+1 from form_element), 1), false);
select setval('individual_relation_gender_mapping_id_seq', coalesce((select max(id)+1 from individual_relation_gender_mapping), 1), false);
select setval('concept_id_seq', coalesce((select max(id)+1 from concept), 1), false);
select setval('form_element_group_id_seq', coalesce((select max(id)+1 from form_element_group), 1), false);
select setval('form_mapping_id_seq', coalesce((select max(id)+1 from form_mapping), 1), false);
select setval('rule_id_seq', coalesce((select max(id)+1 from rule), 1), false);
select setval('form_id_seq', coalesce((select max(id)+1 from form), 1), false);
select setval('individual_relation_id_seq', coalesce((select max(id)+1 from individual_relation), 1), false);
select setval('operational_program_id_seq', coalesce((select max(id)+1 from operational_program), 1), false);
select setval('program_id_seq', coalesce((select max(id)+1 from program), 1), false);
select setval('rule_dependency_id_seq', coalesce((select max(id)+1 from rule_dependency), 1), false);
select setval('users_id_seq', coalesce((select max(id)+1 from users), 1), false);
select setval('organisation_id_seq', coalesce((select max(id)+1 from organisation), 1), false);
---- tables ignored
-- schema_version
-- audit
-- gender