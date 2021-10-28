-- noinspection SqlResolveForFile

TRUNCATE plates.hca_project RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_experiment RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_plate RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_plate_measurement RESTART IDENTITY CASCADE;

insert into plates.hca_project (id,name,description,created_on,created_by,updated_on,updated_by)
values (1,'SBE_0001','Saša First Test Project','2021-09-30 07:48:19.978','sasa.berberovic','2021-09-30 07:48:19.978','sasa.berberovic');

insert into plates.hca_experiment (id,name,description,status,multiplo_method,multiplo_parameter,created_on,created_by,updated_on,updated_by,project_id)
values (1,'SBE_0001_EXP1','Experiment 1 project SBE_0001','OPEN',null,null,'2021-10-01 07:36:17.854','sasa.berberovic','2021-10-01 07:36:17.854','sasa.berberovic',1);

insert into plates.hca_plate (id,barcode,description,experiment_id,rows,columns,sequence,link_status,link_template_id,
                            link_source,linked_on,calculation_status,calculation_error,calculated_by,calculated_on,
                            validation_status,validated_by,validated_on,approval_status,approved_by,approved_on,
                            upload_status,uploaded_by,uploaded_on,created_on,created_by,updated_on,updated_by)
values (1000,'barcode1','description1',1,16,24,0,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
        null,null,null,null,null,null,null);
insert into plates.hca_plate (id,barcode,description,experiment_id,rows,columns,sequence,link_status,link_template_id,
                              link_source,linked_on,calculation_status,calculation_error,calculated_by,calculated_on,
                              validation_status,validated_by,validated_on,approval_status,approved_by,approved_on,
                              upload_status,uploaded_by,uploaded_on,created_on,created_by,updated_on,updated_by)
values (2000,'barcode2','description2',1,16,24,0,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
        null,null,null,null,null,null,null);

insert into plates.hca_plate_measurement(plate_id, measurement_id, is_active, linked_by, linked_on)
values (2000,1000,true, 'sberberovic', null);
insert into plates.hca_plate_measurement(plate_id, measurement_id, is_active, linked_by, linked_on)
values (2000,2000,false, 'sberberovic', null);
