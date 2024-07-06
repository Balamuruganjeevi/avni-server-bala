--[Data Extract Report] Registration
${concept_maps}
SELECT individual.id                                                                       as "id",
       individual.address_id                                                               as "address_id",
       individual.uuid                                                                     as "uuid",
       individual.first_name                                                               as "first_name",
       individual.last_name                                                                as "last_name",
       g.name                                                                              as "gender",
       individual.date_of_birth                                                            as "date_of_birth",
       EXTRACT(year from age(date_of_birth))                                               as "age_in_years",
       EXTRACT(year FROM age(date_of_birth)) * 12 + EXTRACT(month FROM age(date_of_birth)) as "age_in_months",
       individual.date_of_birth_verified                                                   as "date_of_birth_verified",
       individual.registration_date                                                        as "registration_date",
       individual.registration_location                                                    as "registration_location",
       individual.subject_type_id                                                          as "subject_type_id",
       individual.audit_id                                                                 as "audit_id",
       ost.name                                                                            as "subject_type_name",
       a.title                                                                             as "location_name",
       individual.is_voided                                                                as "is_voided",
       ${selections}
FROM public.individual individual
  ${cross_join_concept_maps}
       LEFT OUTER JOIN public.operational_subject_type ost ON ost.subject_type_id = individual.subject_type_id
       LEFT OUTER JOIN public.gender g ON g.id = individual.gender_id
       LEFT OUTER JOIN public.address_level a ON individual.address_id = a.id
where ost.uuid = '${operationalSubjectTypeUuid}'
