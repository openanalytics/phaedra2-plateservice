-- noinspection SqlResolveForFile

TRUNCATE plates.hca_project RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_experiment RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_plate RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_plate_measurement RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_plate_template RESTART IDENTITY CASCADE;
TRUNCATE plates.hca_well_template RESTART IDENTITY CASCADE;

-- insert into plates.hca_welltype (name, description)
--     values('SAMPLE', 'One the three example welltypes (SAMPLE)');
-- insert into plates.hca_welltype (name, description)
--     values('LC', 'One the three example welltypes (LC: low controls)');
-- insert into plates.hca_welltype (name, description)
--     values('HC', 'One the three example welltypes (HC: high controls)');
-- insert into plates.hca_welltype (name, description)
--     values('EMPTY', 'Extra well type column to be in line with template');
-- insert into plates.hca_welltype (name, description)
--     values('LC', 'Negative controle well type. Used by GLPG');
-- insert into plates.hca_welltype (name, description)
--     values('HC', 'Positive controle well type. Used by GLPG');

insert into plates.hca_project (id,name,description,created_on,created_by,updated_on,updated_by)
values (1000,'SBE_0001','Saša First Test Project','2021-09-30 07:48:19.978','sasa.berberovic','2021-09-30 07:48:19.978','sasa.berberovic');

insert into plates.hca_experiment (id,name,description,status,multiplo_method,multiplo_parameter,created_on,created_by,updated_on,updated_by,project_id)
values (1000,'SBE_0001_EXP1','Experiment 1 project SBE_0001','OPEN',null,null,'2021-10-01 07:36:17.854','sasa.berberovic','2021-10-01 07:36:17.854','sasa.berberovic',1000);

insert into plates.hca_experiment (id,name,description,status,multiplo_method,multiplo_parameter,created_on,created_by,updated_on,updated_by,project_id)
values (2000,'SBE_0003_EXP2','Experiment 2 project SBE_0003','OPEN',null,null,'2021-10-01 07:36:17.854','sasa.berberovic','2021-10-01 07:36:17.854','sasa.berberovic',1000);

insert into plates.hca_plate (id,barcode,description,experiment_id,rows,columns,sequence,link_status,link_template_id,
                            link_source,linked_on,calculation_status,calculation_error,calculated_by,calculated_on,
                            validation_status,validated_by,validated_on,invalidated_reason,approval_status,approved_by,approved_on,disapproved_reason,
                            upload_status,uploaded_by,uploaded_on,created_on,created_by,updated_on,updated_by)
values (1000,'barcode1','description1',1000,16,24,0,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
        null,null,null,null,null,null,null);
insert into plates.hca_plate (id,barcode,description,experiment_id,rows,columns,sequence,link_status,link_template_id,
                              link_source,linked_on,calculation_status,calculation_error,calculated_by,calculated_on,
                              validation_status,validated_by,validated_on,invalidated_reason,approval_status,approved_by,approved_on,disapproved_reason,
                              upload_status,uploaded_by,uploaded_on,created_on,created_by,updated_on,updated_by)
values (2000,'barcode2','description2',1000,16,24,0,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
        null,null,null,null,null,null,null);

insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9494, 1000,  1, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9499, 1000,  1, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9500, 1000,  1, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9502, 1000,  1, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9496, 1000,  1, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9498, 1000,  1, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9507, 1000,  1, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9509, 1000,  1, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9510, 1000,  1, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9511, 1000,  1, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9513, 1000,  1, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9514, 1000,  2, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9515, 1000,  2, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9517, 1000,  2, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9518, 1000,  2, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9519, 1000,  2, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9521, 1000,  2, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9522, 1000,  2, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9523, 1000,  2, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9525, 1000,  2, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9526, 1000,  2, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9527, 1000,  2, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9529, 1000,  2, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9530, 1000,  2, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9531, 1000,  2, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9533, 1000,  2, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9534, 1000,  2, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9535, 1000,  2, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9537, 1000,  2, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9538, 1000,  3, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9539, 1000,  3, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9541, 1000,  3, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9542, 1000,  3, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9543, 1000,  3, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9545, 1000,  3, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9546, 1000,  3, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9547, 1000,  3, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9549, 1000,  3, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9550, 1000,  3, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9551, 1000,  3, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9553, 1000,  3, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9554, 1000,  3, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9555, 1000,  3, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9557, 1000,  3, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9558, 1000,  3, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9559, 1000,  3, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9561, 1000,  3, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9562, 1000,  4, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9563, 1000,  4, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9565, 1000,  4, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9566, 1000,  4, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9567, 1000,  4, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9569, 1000,  4, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9570, 1000,  4, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9571, 1000,  4, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9573, 1000,  4, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9574, 1000,  4, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9575, 1000,  4, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9577, 1000,  4, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9578, 1000,  4, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9579, 1000,  4, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9581, 1000,  4, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9582, 1000,  4, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9583, 1000,  4, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9585, 1000,  4, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9586, 1000,  5, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9587, 1000,  5, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9589, 1000,  5, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9590, 1000,  5, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9591, 1000,  5, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9593, 1000,  5, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9594, 1000,  5, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9595, 1000,  5, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9597, 1000,  5, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9598, 1000,  5, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9505, 1000,  1, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9506, 1000,  1, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9603, 1000,  5, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9604, 1000,  5, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9606, 1000,  5, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9607, 1000,  5, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9608, 1000,  5, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9610, 1000,  6, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9611, 1000,  6, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9612, 1000,  6, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9614, 1000,  6, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9615, 1000,  6, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9616, 1000,  6, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9618, 1000,  6, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9619, 1000,  6, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9620, 1000,  6, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9622, 1000,  6, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9623, 1000,  6, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9624, 1000,  6, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9626, 1000,  6, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9627, 1000,  6, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9628, 1000,  6, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9630, 1000,  6, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9631, 1000,  6, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9632, 1000,  6, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9634, 1000,  7, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9635, 1000,  7, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9636, 1000,  7, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9638, 1000,  7, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9639, 1000,  7, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9640, 1000,  7, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9642, 1000,  7, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9643, 1000,  7, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9644, 1000,  7, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9646, 1000,  7, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9647, 1000,  7, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9648, 1000,  7, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9650, 1000,  7, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9651, 1000,  7, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9652, 1000,  7, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9654, 1000,  7, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9655, 1000,  7, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9656, 1000,  7, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9658, 1000,  8, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9659, 1000,  8, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9660, 1000,  8, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9662, 1000,  8, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9663, 1000,  8, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9664, 1000,  8, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9666, 1000,  8, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9667, 1000,  8, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9668, 1000,  8, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9670, 1000,  8, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9671, 1000,  8, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9672, 1000,  8, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9674, 1000,  8, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9675, 1000,  8, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9676, 1000,  8, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9678, 1000,  8, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9679, 1000,  8, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9680, 1000,  8, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9682, 1000,  9, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9683, 1000,  9, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9684, 1000,  9, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9686, 1000,  9, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9687, 1000,  9, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9688, 1000,  9, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9690, 1000,  9, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9691, 1000,  9, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9692, 1000,  9, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9694, 1000,  9, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9695, 1000,  9, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9600, 1000,  5, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9602, 1000,  5, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9700, 1000,  9, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9701, 1000,  9, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9702, 1000,  9, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9704, 1000,  9, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9705, 1000,  9, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9706, 1000,  10, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9708, 1000,  10, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9709, 1000,  10, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9710, 1000,  10, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9712, 1000,  10, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9713, 1000,  10, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9714, 1000,  10, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9716, 1000,  10, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9717, 1000,  10, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9718, 1000,  10, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9720, 1000,  10, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9721, 1000,  10, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9722, 1000,  10, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9724, 1000,  10, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9725, 1000,  10, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9726, 1000,  10, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9728, 1000,  10, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9729, 1000,  10, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9730, 1000,  11, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9732, 1000,  11, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9733, 1000,  11, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9734, 1000,  11, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9736, 1000,  11, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9737, 1000,  11, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9738, 1000,  11, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9740, 1000,  11, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9741, 1000,  11, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9742, 1000,  11, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9744, 1000,  11, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9745, 1000,  11, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9746, 1000,  11, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9748, 1000,  11, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9749, 1000,  11, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9750, 1000,  11, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9752, 1000,  11, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9753, 1000,  11, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9754, 1000,  12, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9756, 1000,  12, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9757, 1000,  12, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9758, 1000,  12, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9760, 1000,  12, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9761, 1000,  12, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9762, 1000,  12, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9764, 1000,  12, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9765, 1000,  12, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9766, 1000,  12, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9768, 1000,  12, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9769, 1000,  12, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9770, 1000,  12, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9772, 1000,  12, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9773, 1000,  12, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9774, 1000,  12, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9776, 1000,  12, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9777, 1000,  12, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9778, 1000,  13, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9780, 1000,  13, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9781, 1000,  13, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9782, 1000,  13, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9784, 1000,  13, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9785, 1000,  13, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9786, 1000,  13, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9788, 1000,  13, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9789, 1000,  13, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9790, 1000,  13, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9792, 1000,  13, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9793, 1000,  13, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9697, 1000,  9, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9698, 1000,  9, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9657, 1000,  7, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9846, 1000,  15, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9798, 1000,  13, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9800, 1000,  13, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9801, 1000,  13, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9802, 1000,  14, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9804, 1000,  14, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9805, 1000,  14, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9806, 1000,  14, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9808, 1000,  14, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9809, 1000,  14, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9810, 1000,  14, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9812, 1000,  14, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9813, 1000,  14, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9814, 1000,  14, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9816, 1000,  14, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9817, 1000,  14, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9818, 1000,  14, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9820, 1000,  14, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9821, 1000,  14, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9822, 1000,  14, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9824, 1000,  14, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9825, 1000,  14, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9826, 1000,  15, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9828, 1000,  15, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9829, 1000,  15, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9830, 1000,  15, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9832, 1000,  15, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9833, 1000,  15, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9834, 1000,  15, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9836, 1000,  15, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9837, 1000,  15, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9838, 1000,  15, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9840, 1000,  15, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9841, 1000,  15, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9842, 1000,  15, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9844, 1000,  15, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9845, 1000,  15, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9848, 1000,  15, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9849, 1000,  15, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9850, 1000,  16, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9852, 1000,  16, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9853, 1000,  16, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9854, 1000,  16, 5, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9856, 1000,  16, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9857, 1000,  16, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9858, 1000,  16, 9, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9860, 1000,  16, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9861, 1000,  16, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9862, 1000,  16, 13, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9864, 1000,  16, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9865, 1000,  16, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9866, 1000,  16, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9868, 1000, 16, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9869, 1000,  16, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9870, 1000,  16, 21, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9872, 1000,  16, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9873, 1000,  16, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9564, 1000,  4, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9580, 1000,  4, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9596, 1000,  5, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9609, 1000,  5, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9625, 1000,  6, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9641, 1000,  7, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9673, 1000,  8, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9689, 1000,  9, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9703, 1000,  9, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9719, 1000,  10, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9735, 1000,  11, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9751, 1000,  11, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9767, 1000,  12, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9796, 1000,  13, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9797, 1000,  13, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9693, 1000,  9, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9491, 1000,  1, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9584, 1000,  4, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9599, 1000,  5, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9613, 1000,  6, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9629, 1000,  6, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9645, 1000,  7, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9661, 1000,  8, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9677, 1000,  8, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9707, 1000,  10, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9723, 1000,  10, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9739, 1000,  11, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9755, 1000,  12, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9771, 1000,  12, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9783, 1000,  13, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9794, 1000,  13, 17, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9803, 1000,  14, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9815, 1000,  14, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9827, 1000,  15, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9839, 1000,  15, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9851, 1000,  16, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9863, 1000,  16, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9490, 1000,  1, 1, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9495, 1000,  1, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9503, 1000,  1, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9512, 1000,  1, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9524, 1000,  2, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9536, 1000,  2, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9548, 1000,  3, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9568, 1000,  4, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9807, 1000,  14, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9649, 1000,  7, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9665, 1000,  8, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9681, 1000,  8, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9696, 1000,  9, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9711, 1000,  10, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9727, 1000,  10, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9819, 1000,  14, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9831, 1000,  15, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9843, 1000,  15, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9855, 1000,  16, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9867, 1000,  16, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9492, 1000,  1, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9497, 1000,  1, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9504, 1000,  1, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9516, 1000,  2, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9528, 1000,  2, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9540, 1000,  3, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9552, 1000,  3, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9556, 1000,  3, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9572, 1000,  4, 11, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9588, 1000,  5, 3, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9601, 1000,  5, 16, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9617, 1000,  6, 8, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9633, 1000,  6, 24, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9743, 1000,  11, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9759, 1000,  12, 6, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9775, 1000,  12, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9787, 1000,  13, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9795, 1000,  13, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9799, 1000,  13, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9811, 1000,  14, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9823, 1000,  14, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9835, 1000,  15, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9847, 1000,  15, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9859, 1000,  16, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9871, 1000,  16, 22, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9493, 1000,  1, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9501, 1000,  1, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9508, 1000,  1, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9520, 1000,  2, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9532, 1000,  2, 19, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9544, 1000,  3, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9560, 1000,  3, 23, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9576, 1000,  4, 15, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9592, 1000,  5, 7, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9605, 1000,  5, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9621, 1000,  6, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9637, 1000,  7, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9653, 1000,  7, 20, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9669, 1000,  8, 12, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9685, 1000,  9, 4, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9699, 1000,  9, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9715, 1000,  10, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9731, 1000,  11, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9747, 1000,  11, 18, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9763, 1000,  12, 10, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9779, 1000,  13, 2, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(9791, 1000,  13, 14, 'ACCEPTED_DEFAULT', null, null, 'EMPTY');

insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38587,2000,1,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38588,2000,1,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38589,2000,1,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38590,2000,1,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38591,2000,1,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38592,2000,1,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38593,2000,1,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38594,2000,1,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38595,2000,1,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38596,2000,1,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38597,2000,1,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38598,2000,1,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38599,2000,1,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38600,2000,1,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38601,2000,1,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38602,2000,1,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38603,2000,1,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38604,2000,1,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38605,2000,1,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38606,2000,1,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38607,2000,1,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38608,2000,1,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38609,2000,1,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38610,2000,1,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38611,2000,2,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38612,2000,2,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38613,2000,2,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38614,2000,2,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38615,2000,2,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38616,2000,2,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38617,2000,2,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38618,2000,2,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38619,2000,2,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38620,2000,2,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38621,2000,2,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38622,2000,2,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38623,2000,2,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38624,2000,2,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38625,2000,2,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38626,2000,2,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38627,2000,2,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38628,2000,2,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38629,2000,2,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38630,2000,2,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38631,2000,2,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38632,2000,2,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38633,2000,2,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38634,2000,2,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38635,2000,3,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38636,2000,3,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38637,2000,3,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38638,2000,3,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38639,2000,3,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38640,2000,3,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38641,2000,3,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38642,2000,3,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38643,2000,3,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38644,2000,3,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38645,2000,3,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38646,2000,3,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38647,2000,3,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38648,2000,3,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38649,2000,3,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38650,2000,3,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38651,2000,3,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38652,2000,3,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38653,2000,3,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38654,2000,3,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38655,2000,3,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38656,2000,3,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38657,2000,3,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38658,2000,3,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38659,2000,4,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38660,2000,4,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38661,2000,4,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38662,2000,4,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38663,2000,4,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38664,2000,4,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38665,2000,4,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38666,2000,4,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38667,2000,4,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38668,2000,4,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38669,2000,4,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38670,2000,4,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38671,2000,4,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38672,2000,4,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38673,2000,4,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38674,2000,4,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38675,2000,4,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38676,2000,4,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38677,2000,4,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38678,2000,4,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38679,2000,4,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38680,2000,4,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38681,2000,4,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38682,2000,4,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38683,2000,5,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38684,2000,5,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38685,2000,5,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38686,2000,5,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38687,2000,5,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38688,2000,5,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38689,2000,5,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38690,2000,5,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38691,2000,5,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38692,2000,5,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38693,2000,5,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38694,2000,5,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38695,2000,5,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38696,2000,5,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38697,2000,5,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38698,2000,5,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38699,2000,5,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38700,2000,5,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38701,2000,5,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38702,2000,5,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38703,2000,5,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38704,2000,5,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38705,2000,5,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38706,2000,5,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38707,2000,6,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38708,2000,6,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38709,2000,6,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38710,2000,6,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38711,2000,6,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38712,2000,6,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38713,2000,6,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38714,2000,6,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38715,2000,6,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38716,2000,6,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38717,2000,6,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38718,2000,6,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38719,2000,6,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38720,2000,6,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38721,2000,6,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38722,2000,6,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38723,2000,6,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38724,2000,6,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38725,2000,6,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38726,2000,6,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38727,2000,6,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38728,2000,6,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38729,2000,6,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38730,2000,6,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38731,2000,7,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38732,2000,7,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38733,2000,7,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38734,2000,7,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38735,2000,7,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38736,2000,7,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38737,2000,7,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38738,2000,7,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38739,2000,7,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38740,2000,7,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38741,2000,7,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38742,2000,7,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38743,2000,7,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38744,2000,7,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38745,2000,7,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38746,2000,7,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38747,2000,7,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38748,2000,7,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38749,2000,7,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38750,2000,7,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38751,2000,7,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38752,2000,7,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38753,2000,7,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38754,2000,7,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38755,2000,8,1,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38756,2000,8,2,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38757,2000,8,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38758,2000,8,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38759,2000,8,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38760,2000,8,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38761,2000,8,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38762,2000,8,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38763,2000,8,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38764,2000,8,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38765,2000,8,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38766,2000,8,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38767,2000,8,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38768,2000,8,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38769,2000,8,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38770,2000,8,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38771,2000,8,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38772,2000,8,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38773,2000,8,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38774,2000,8,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38775,2000,8,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38776,2000,8,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38777,2000,8,23,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38778,2000,8,24,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38779,2000,9,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38780,2000,9,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38781,2000,9,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38782,2000,9,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38783,2000,9,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38784,2000,9,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38785,2000,9,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38786,2000,9,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38787,2000,9,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38788,2000,9,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38789,2000,9,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38790,2000,9,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38791,2000,9,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38792,2000,9,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38793,2000,9,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38794,2000,9,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38795,2000,9,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38796,2000,9,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38797,2000,9,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38798,2000,9,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38799,2000,9,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38800,2000,9,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38801,2000,9,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38802,2000,9,24,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38803,2000,10,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38804,2000,10,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38805,2000,10,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38806,2000,10,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38807,2000,10,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38808,2000,10,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38809,2000,10,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38810,2000,10,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38811,2000,10,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38812,2000,10,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38813,2000,10,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38814,2000,10,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38815,2000,10,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38816,2000,10,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38817,2000,10,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38818,2000,10,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38819,2000,10,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38820,2000,10,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38821,2000,10,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38822,2000,10,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38823,2000,10,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38824,2000,10,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38825,2000,10,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38826,2000,10,24,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38827,2000,11,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38828,2000,11,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38829,2000,11,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38830,2000,11,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38831,2000,11,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38832,2000,11,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38833,2000,11,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38834,2000,11,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38835,2000,11,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38836,2000,11,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38837,2000,11,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38838,2000,11,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38839,2000,11,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38840,2000,11,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38841,2000,11,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38842,2000,11,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38843,2000,11,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38844,2000,11,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38845,2000,11,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38846,2000,11,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38847,2000,11,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38848,2000,11,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38849,2000,11,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38850,2000,11,24,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38851,2000,12,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38852,2000,12,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38853,2000,12,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38854,2000,12,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38855,2000,12,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38856,2000,12,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38857,2000,12,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38858,2000,12,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38859,2000,12,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38860,2000,12,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38861,2000,12,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38862,2000,12,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38863,2000,12,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38864,2000,12,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38865,2000,12,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38866,2000,12,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38867,2000,12,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38868,2000,12,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38869,2000,12,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38870,2000,12,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38871,2000,12,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38872,2000,12,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38873,2000,12,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38874,2000,12,24,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38875,2000,13,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38876,2000,13,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38877,2000,13,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38878,2000,13,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38879,2000,13,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38880,2000,13,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38881,2000,13,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38882,2000,13,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38883,2000,13,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38884,2000,13,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38885,2000,13,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38886,2000,13,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38887,2000,13,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38888,2000,13,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38889,2000,13,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38890,2000,13,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38891,2000,13,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38892,2000,13,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38893,2000,13,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38894,2000,13,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38895,2000,13,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38896,2000,13,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38897,2000,13,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38898,2000,13,24,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38899,2000,14,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38900,2000,14,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38901,2000,14,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38902,2000,14,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38903,2000,14,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38904,2000,14,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38905,2000,14,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38906,2000,14,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38907,2000,14,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38908,2000,14,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38909,2000,14,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38910,2000,14,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38911,2000,14,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38912,2000,14,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38913,2000,14,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38914,2000,14,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38915,2000,14,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38916,2000,14,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38917,2000,14,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38918,2000,14,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38919,2000,14,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38920,2000,14,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38921,2000,14,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38922,2000,14,24,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38923,2000,15,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38924,2000,15,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38925,2000,15,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38926,2000,15,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38927,2000,15,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38928,2000,15,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38929,2000,15,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38930,2000,15,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38931,2000,15,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38932,2000,15,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38933,2000,15,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38934,2000,15,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38935,2000,15,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38936,2000,15,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38937,2000,15,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38938,2000,15,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38939,2000,15,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38940,2000,15,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38941,2000,15,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38942,2000,15,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38943,2000,15,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38944,2000,15,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38945,2000,15,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38946,2000,15,24,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38947,2000,16,1,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38948,2000,16,2,'ACCEPTED_DEFAULT',null,null,'LC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38949,2000,16,3,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38950,2000,16,4,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38951,2000,16,5,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38952,2000,16,6,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38953,2000,16,7,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38954,2000,16,8,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38955,2000,16,9,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38956,2000,16,10,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38957,2000,16,11,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38958,2000,16,12,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38959,2000,16,13,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38960,2000,16,14,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38961,2000,16,15,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38962,2000,16,16,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38963,2000,16,17,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38964,2000,16,18,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38965,2000,16,19,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38966,2000,16,20,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38967,2000,16,21,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38968,2000,16,22,'ACCEPTED_DEFAULT',null,null,'SAMPLE');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38969,2000,16,23,'ACCEPTED_DEFAULT',null,null,'HC');
insert into plates.hca_well(id, plate_id, row, "column", status, compound_id, description, welltype)
values(38970,2000,16,24,'ACCEPTED_DEFAULT',null,null,'HC');

insert into plates.hca_plate_measurement(plate_id, measurement_id, is_active, linked_by, linked_on)
values (2000,1000,true, 'sberberovic', null);
insert into plates.hca_plate_measurement(plate_id, measurement_id, is_active, linked_by, linked_on)
values (2000,2000,false, 'sberberovic', null);

insert into plates.hca_plate_template(id,name,description,rows,columns,created_on,created_by,updated_on,updated_by)
values (1000,null,null,2,3,'2021-10-01 07:36:17.854','smarien',null,null);

insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(10000,null,1,1,'SAMPLE',false,1000,'GalG264869-3','Compound',0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(10001, null, 1, 2, 'SAMPLE', false, 1000, 'GalG264869-3', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(10002, null, 1, 3, 'SAMPLE', false, 1000, 'GalG264869-3', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(10003, null, 2, 1, 'SAMPLE', false, 1000, 'GalG1125349-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(10004, null, 2, 2, 'SAMPLE', false, 1000, 'GalG264869-3', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(10005, null, 2, 3, 'SAMPLE', false, 1000, 'GalG264869-3', 'Compound', 0.00137);

insert into plates.hca_plate_template(id,name,description,rows,columns,created_on,created_by,updated_on,updated_by)
values(56, 'ImportFromFile2', null, 16, 24, '2022-09-20 07:53:49.801', 'sberberovic', null, null);

insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9494,null,1,5,'SAMPLE',false,56,'GalG264869-3','Compound',0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9499, null, 1, 10, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9500, null, 1, 11, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9502, null, 1, 13, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9496, null, 1, 7, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9498, null, 1, 9, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9507, null, 1, 18, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9509, null, 1, 20, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9510, null, 1, 21, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9511, null, 1, 22, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9513, null, 1, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9514, null, 2, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9515, null, 2, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9517, null, 2, 4, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9518, null, 2, 5, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9519, null, 2, 6, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9521, null, 2, 8, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9522, null, 2, 9, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9523, null, 2, 10, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9525, null, 2, 12, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9526, null, 2, 13, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9527, null, 2, 14, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9529, null, 2, 16, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9530, null, 2, 17, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9531, null, 2, 18, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9533, null, 2, 20, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9534, null, 2, 21, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9535, null, 2, 22, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9537, null, 2, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9538, null, 3, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9539, null, 3, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9541, null, 3, 4, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9542, null, 3, 5, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9543, null, 3, 6, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9545, null, 3, 8, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9546, null, 3, 9, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9547, null, 3, 10, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9549, null, 3, 12, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9550, null, 3, 13, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9551, null, 3, 14, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9553, null, 3, 16, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9554, null, 3, 17, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9555, null, 3, 18, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9557, null, 3, 20, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9558, null, 3, 21, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9559, null, 3, 22, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9561, null, 3, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9562, null, 4, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9563, null, 4, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9565, null, 4, 4, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9566, null, 4, 5, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9567, null, 4, 6, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9569, null, 4, 8, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9570, null, 4, 9, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9571, null, 4, 10, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9573, null, 4, 12, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9574, null, 4, 13, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9575, null, 4, 14, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9577, null, 4, 16, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9578, null, 4, 17, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9579, null, 4, 18, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9581, null, 4, 20, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9582, null, 4, 21, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9583, null, 4, 22, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9585, null, 4, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9586, null, 5, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9587, null, 5, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9589, null, 5, 4, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9590, null, 5, 5, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9591, null, 5, 6, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9593, null, 5, 8, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9594, null, 5, 9, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9595, null, 5, 10, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9597, null, 5, 12, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9598, null, 5, 13, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9505, null, 1, 16, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9506, null, 1, 17, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9603, null, 5, 18, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9604, null, 5, 19, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9606, null, 5, 21, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9607, null, 5, 22, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9608, null, 5, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9610, null, 6, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9611, null, 6, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9612, null, 6, 3, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9614, null, 6, 5, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9615, null, 6, 6, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9616, null, 6, 7, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9618, null, 6, 9, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9619, null, 6, 10, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9620, null, 6, 11, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9622, null, 6, 13, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9623, null, 6, 14, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9624, null, 6, 15, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9626, null, 6, 17, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9627, null, 6, 18, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9628, null, 6, 19, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9630, null, 6, 21, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9631, null, 6, 22, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9632, null, 6, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9634, null, 7, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9635, null, 7, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9636, null, 7, 3, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9638, null, 7, 5, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9639, null, 7, 6, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9640, null, 7, 7, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9642, null, 7, 9, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9643, null, 7, 10, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9644, null, 7, 11, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9646, null, 7, 13, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9647, null, 7, 14, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9648, null, 7, 15, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9650, null, 7, 17, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9651, null, 7, 18, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9652, null, 7, 19, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9654, null, 7, 21, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9655, null, 7, 22, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9656, null, 7, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9658, null, 8, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9659, null, 8, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9660, null, 8, 3, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9662, null, 8, 5, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9663, null, 8, 6, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9664, null, 8, 7, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9666, null, 8, 9, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9667, null, 8, 10, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9668, null, 8, 11, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9670, null, 8, 13, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9671, null, 8, 14, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9672, null, 8, 15, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9674, null, 8, 17, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9675, null, 8, 18, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9676, null, 8, 19, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9678, null, 8, 21, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9679, null, 8, 22, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9680, null, 8, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9682, null, 9, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9683, null, 9, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9684, null, 9, 3, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9686, null, 9, 5, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9687, null, 9, 6, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9688, null, 9, 7, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9690, null, 9, 9, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9691, null, 9, 10, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9692, null, 9, 11, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9694, null, 9, 13, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9695, null, 9, 14, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9600, null, 5, 15, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9602, null, 5, 17, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9700, null, 9, 19, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9701, null, 9, 20, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9702, null, 9, 21, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9704, null, 9, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9705, null, 9, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9706, null, 10, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9708, null, 10, 3, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9709, null, 10, 4, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9710, null, 10, 5, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9712, null, 10, 7, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9713, null, 10, 8, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9714, null, 10, 9, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9716, null, 10, 11, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9717, null, 10, 12, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9718, null, 10, 13, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9720, null, 10, 15, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 0.334);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9721, null, 10, 16, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9722, null, 10, 17, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 0.0371);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9724, null, 10, 19, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9725, null, 10, 20, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9726, null, 10, 21, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 0.000458);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9728, null, 10, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9729, null, 10, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9730, null, 11, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9732, null, 11, 3, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9733, null, 11, 4, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9734, null, 11, 5, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9736, null, 11, 7, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9737, null, 11, 8, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9738, null, 11, 9, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9740, null, 11, 11, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9741, null, 11, 12, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9742, null, 11, 13, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9744, null, 11, 15, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9745, null, 11, 16, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9746, null, 11, 17, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9748, null, 11, 19, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9749, null, 11, 20, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9750, null, 11, 21, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9752, null, 11, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9753, null, 11, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9754, null, 12, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9756, null, 12, 3, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9757, null, 12, 4, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9758, null, 12, 5, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9760, null, 12, 7, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9761, null, 12, 8, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9762, null, 12, 9, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9764, null, 12, 11, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9765, null, 12, 12, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9766, null, 12, 13, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9768, null, 12, 15, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9769, null, 12, 16, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9770, null, 12, 17, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9772, null, 12, 19, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.00411);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9773, null, 12, 20, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9774, null, 12, 21, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9776, null, 12, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9777, null, 12, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9778, null, 13, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9780, null, 13, 3, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9781, null, 13, 4, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9782, null, 13, 5, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9784, null, 13, 7, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9785, null, 13, 8, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9786, null, 13, 9, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9788, null, 13, 11, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9789, null, 13, 12, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9790, null, 13, 13, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9792, null, 13, 15, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9793, null, 13, 16, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9697, null, 9, 16, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9698, null, 9, 17, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9657, null, 7, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9846, null, 15, 21, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9798, null, 13, 21, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9800, null, 13, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9801, null, 13, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9802, null, 14, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9804, null, 14, 3, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9805, null, 14, 4, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9806, null, 14, 5, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9808, null, 14, 7, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9809, null, 14, 8, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9810, null, 14, 9, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9812, null, 14, 11, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9813, null, 14, 12, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9814, null, 14, 13, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9816, null, 14, 15, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9817, null, 14, 16, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9818, null, 14, 17, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9820, null, 14, 19, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9821, null, 14, 20, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9822, null, 14, 21, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9824, null, 14, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9825, null, 14, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9826, null, 15, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9828, null, 15, 3, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9829, null, 15, 4, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9830, null, 15, 5, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9832, null, 15, 7, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9833, null, 15, 8, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.00123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9834, null, 15, 9, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.00411);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9836, null, 15, 11, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9837, null, 15, 12, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9838, null, 15, 13, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9840, null, 15, 15, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9841, null, 15, 16, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9842, null, 15, 17, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9844, null, 15, 19, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9845, null, 15, 20, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9848, null, 15, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9849, null, 15, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9850, null, 16, 1, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9852, null, 16, 3, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9853, null, 16, 4, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9854, null, 16, 5, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9856, null, 16, 7, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9857, null, 16, 8, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9858, null, 16, 9, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9860, null, 16, 11, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9861, null, 16, 12, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9862, null, 16, 13, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9864, null, 16, 15, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9865, null, 16, 16, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9866, null, 16, 17, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9868,  null, 16, 19, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9869, null, 16, 20, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9870, null, 16, 21, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9872, null, 16, 23, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9873, null, 16, 24, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9564, null, 4, 3, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9580, null, 4, 19, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9596, null, 5, 11, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9609, null, 5, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9625, null, 6, 16, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9641, null, 7, 8, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9673, null, 8, 16, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9689, null, 9, 8, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9703, null, 9, 22, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9719, null, 10, 14, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9735, null, 11, 6, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9751, null, 11, 22, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9767, null, 12, 14, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9796, null, 13, 19, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9797, null, 13, 20, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9693, null, 9, 12, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9491, null, 1, 2, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9584, null, 4, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9599, null, 5, 14, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9613, null, 6, 4, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9629, null, 6, 20, 'SAMPLE', false, 56, 'GalG1124374-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9645, null, 7, 12, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9661, null, 8, 4, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9677, null, 8, 20, 'SAMPLE', false, 56, 'GalG1119195-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9707, null, 10, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9723, null, 10, 18, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 0.0124);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9739, null, 11, 10, 'SAMPLE', false, 56, 'GalG1118495-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9755, null, 12, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9771, null, 12, 18, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9783, null, 13, 6, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9794, null, 13, 17, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9803, null, 14, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9815, null, 14, 14, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9827, null, 15, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9839, null, 15, 14, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9851, null, 16, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9863, null, 16, 14, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9490, null, 1, 1, 'HC', false, 56, 'GalG000702-1', 'Compound', 3e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9495, null, 1, 6, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9503, null, 1, 14, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9512, null, 1, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9524, null, 2, 11, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9536, null, 2, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9548, null, 3, 11, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.000457);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9568, null, 4, 7, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9807, null, 14, 6, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9649, null, 7, 16, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9665, null, 8, 8, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9681, null, 8, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9696, null, 9, 15, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9711, null, 10, 6, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9727, null, 10, 22, 'SAMPLE', false, 56, 'GalG1119154-1', 'Compound', 1.53e-05);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9819, null, 14, 18, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9831, null, 15, 6, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9843, null, 15, 18, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9855, null, 16, 6, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.111);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9867, null, 16, 18, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.0123);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9492, null, 1, 3, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9497, null, 1, 8, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 0.00411);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9504, null, 1, 15, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9516, null, 2, 3, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 1.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9528, null, 2, 15, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9540, null, 3, 3, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9552, null, 3, 15, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.167);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9556, null, 3, 19, 'SAMPLE', false, 56, 'GalG1125350-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9572, null, 4, 11, 'SAMPLE', false, 56, 'GalG1125507-1', 'Compound', 0.000229);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9588, null, 5, 3, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 3);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9601, null, 5, 16, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9617, null, 6, 8, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9633, null, 6, 24, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9743, null, 11, 14, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.5);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9759, null, 12, 6, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.0556);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9775, null, 12, 22, 'SAMPLE', false, 56, 'GalG1125146-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9787, null, 13, 10, 'SAMPLE', false, 56, 'GalG1119280-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9795, null, 13, 18, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9799, null, 13, 22, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9811, null, 14, 10, 'SAMPLE', false, 56, 'GalG1118497-2', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9823, null, 14, 22, 'SAMPLE', false, 56, 'GalG1125212-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9835, null, 15, 10, 'SAMPLE', false, 56, 'GalG1122312-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9847, null, 15, 22, 'SAMPLE', false, 56, 'GalG1125549-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9859, null, 16, 10, 'SAMPLE', false, 56, 'GalG1117337-1', 'Compound', 0.00137);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9871, null, 16, 22, 'SAMPLE', false, 56, 'GalG1125214-1', 'Compound', 0.000152);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9493, null, 1, 4, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9501, null, 1, 12, 'SAMPLE', false, 56, 'GalG264869-3', 'Compound', 5.08e-05);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9508, null, 1, 19, 'SAMPLE', false, 56, 'GalG1125349-1', 'Compound', 0.00206);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9520, null, 2, 7, 'SAMPLE', false, 56, 'GalG1125465-1', 'Compound', 0.0185);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9532, null, 2, 19, 'SAMPLE', false, 56, 'GalG1028046-3', 'Compound', 0.00412);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9544, null, 3, 7, 'SAMPLE', false, 56, 'GalG498657-2', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9560, null, 3, 23, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9576, null, 4, 15, 'SAMPLE', false, 56, 'GalG1117864-1', 'Compound', 0.333);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9592, null, 5, 7, 'SAMPLE', false, 56, 'GalG909327-3', 'Compound', 0.037);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9605, null, 5, 20, 'SAMPLE', false, 56, 'GalG957395-5', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9621, null, 6, 12, 'SAMPLE', false, 56, 'GalG1125538-1', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9637, null, 7, 4, 'SAMPLE', false, 56, 'GalG603016-5', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9653, null, 7, 20, 'SAMPLE', false, 56, 'GalG1114606-2', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9669, null, 8, 12, 'SAMPLE', false, 56, 'GalG1118435-4', 'Compound', 7.62e-06);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9685, null, 9, 4, 'SAMPLE', false, 56, 'GalG900683-2', 'Compound', 1);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9699, null, 9, 18, 'SAMPLE', false, 56, 'GalG1124940-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9715, null, 10, 10, 'SAMPLE', false, 56, 'GalG1116765-2', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9731, null, 11, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9747, null, 11, 18, 'SAMPLE', false, 56, 'GalG1125539-1', 'Compound', 0.00617);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9763, null, 12, 10, 'SAMPLE', false, 56, 'GalG1125463-1', 'Compound', 0.000686);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9779, null, 13, 2, 'LC', false, 56, 'GalG000701-1', 'Compound', 3e-07);
insert into plates.hca_well_template(id,description,row,"column",well_type,skipped,plate_template_id,substance_name,substance_type,concentration)
values(9791, null, 13, 14, 'SAMPLE', false, 56, 'GalG1125545-1', 'Compound', 0.5);

insert into plates.hca_plate_template(id,name,description,rows,columns,created_on,created_by,updated_on,updated_by)
values (1001,null,null,2,3,'2021-10-01 07:36:17.854','smarien',null,null);

insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1577,'COMPOUND','000702-1',3e-06,38587);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1578,'COMPOUND','000702-1',3e-06,38588);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1579,'COMPOUND','264869-3',1,38589);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1580,'COMPOUND','264869-3',0.333,38590);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1581,'COMPOUND','264869-3',0.111,38591);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1582,'COMPOUND','264869-3',0.037,38592);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1583,'COMPOUND','264869-3',0.0123,38593);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1584,'COMPOUND','264869-3',0.00411,38594);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1585,'COMPOUND','264869-3',0.00137,38595);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1586,'COMPOUND','264869-3',0.000457,38596);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1587,'COMPOUND','264869-3',0.000152,38597);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1588,'COMPOUND','264869-3',5.08e-05,38598);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1589,'COMPOUND','1125349-1',1.5,38599);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1590,'COMPOUND','1125349-1',0.5,38600);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1591,'COMPOUND','1125349-1',0.167,38601);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1592,'COMPOUND','1125349-1',0.0556,38602);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1593,'COMPOUND','1125349-1',0.0185,38603);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1594,'COMPOUND','1125349-1',0.00617,38604);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1595,'COMPOUND','1125349-1',0.00206,38605);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1596,'COMPOUND','1125349-1',0.000686,38606);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1597,'COMPOUND','1125349-1',0.000229,38607);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1598,'COMPOUND','1125349-1',7.62e-06,38608);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1599,'COMPOUND','000701-1',3e-07,38609);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1600,'COMPOUND','000701-1',3e-07,38610);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1601,'COMPOUND','000702-1',3e-06,38611);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1602,'COMPOUND','000702-1',3e-06,38612);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1603,'COMPOUND','1125465-1',1.5,38613);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1604,'COMPOUND','1125465-1',0.5,38614);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1605,'COMPOUND','1125465-1',0.167,38615);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1606,'COMPOUND','1125465-1',0.0556,38616);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1607,'COMPOUND','1125465-1',0.0185,38617);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1608,'COMPOUND','1125465-1',0.00617,38618);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1609,'COMPOUND','1125465-1',0.00206,38619);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1610,'COMPOUND','1125465-1',0.000686,38620);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1611,'COMPOUND','1125465-1',0.000229,38621);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1612,'COMPOUND','1125465-1',7.62e-06,38622);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1613,'COMPOUND','1028046-3',3,38623);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1614,'COMPOUND','1028046-3',1,38624);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1615,'COMPOUND','1028046-3',0.333,38625);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1616,'COMPOUND','1028046-3',0.111,38626);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1617,'COMPOUND','1028046-3',0.037,38627);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1618,'COMPOUND','1028046-3',0.0123,38628);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1619,'COMPOUND','1028046-3',0.00412,38629);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1620,'COMPOUND','1028046-3',0.00137,38630);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1621,'COMPOUND','1028046-3',0.000457,38631);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1622,'COMPOUND','1028046-3',0.000152,38632);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1623,'COMPOUND','000701-1',3e-07,38633);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1624,'COMPOUND','000701-1',3e-07,38634);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1625,'COMPOUND','000702-1',3e-06,38635);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1626,'COMPOUND','000702-1',3e-06,38636);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1627,'COMPOUND','498657-2',3,38637);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1628,'COMPOUND','498657-2',1,38638);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1629,'COMPOUND','498657-2',0.333,38639);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1630,'COMPOUND','498657-2',0.111,38640);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1631,'COMPOUND','498657-2',0.037,38641);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1632,'COMPOUND','498657-2',0.0123,38642);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1633,'COMPOUND','498657-2',0.00412,38643);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1634,'COMPOUND','498657-2',0.00137,38644);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1635,'COMPOUND','498657-2',0.000457,38645);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1636,'COMPOUND','498657-2',0.000152,38646);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1637,'COMPOUND','1125350-1',1.5,38647);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1638,'COMPOUND','1125350-1',0.5,38648);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1639,'COMPOUND','1125350-1',0.167,38649);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1640,'COMPOUND','1125350-1',0.0556,38650);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1641,'COMPOUND','1125350-1',0.0185,38651);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1642,'COMPOUND','1125350-1',0.00617,38652);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1643,'COMPOUND','1125350-1',0.00206,38653);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1644,'COMPOUND','1125350-1',0.000686,38654);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1645,'COMPOUND','1125350-1',0.000229,38655);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1646,'COMPOUND','1125350-1',7.62e-06,38656);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1647,'COMPOUND','000701-1',3e-07,38657);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1648,'COMPOUND','000701-1',3e-07,38658);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1649,'COMPOUND','000702-1',3e-06,38659);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1650,'COMPOUND','000702-1',3e-06,38660);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1651,'COMPOUND','1125507-1',1.5,38661);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1652,'COMPOUND','1125507-1',0.5,38662);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1653,'COMPOUND','1125507-1',0.167,38663);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1654,'COMPOUND','1125507-1',0.0556,38664);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1655,'COMPOUND','1125507-1',0.0185,38665);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1656,'COMPOUND','1125507-1',0.00617,38666);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1657,'COMPOUND','1125507-1',0.00206,38667);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1658,'COMPOUND','1125507-1',0.000686,38668);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1659,'COMPOUND','1125507-1',0.000229,38669);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1660,'COMPOUND','1125507-1',7.62e-06,38670);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1661,'COMPOUND','1117864-1',3,38671);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1662,'COMPOUND','1117864-1',1,38672);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1663,'COMPOUND','1117864-1',0.333,38673);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1664,'COMPOUND','1117864-1',0.111,38674);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1665,'COMPOUND','1117864-1',0.037,38675);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1666,'COMPOUND','1117864-1',0.0123,38676);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1667,'COMPOUND','1117864-1',0.00412,38677);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1668,'COMPOUND','1117864-1',0.00137,38678);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1669,'COMPOUND','1117864-1',0.000457,38679);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1670,'COMPOUND','1117864-1',0.000152,38680);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1671,'COMPOUND','000701-1',3e-07,38681);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1672,'COMPOUND','000701-1',3e-07,38682);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1673,'COMPOUND','000702-1',3e-06,38683);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1674,'COMPOUND','000702-1',3e-06,38684);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1675,'COMPOUND','909327-3',3,38685);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1676,'COMPOUND','909327-3',1,38686);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1677,'COMPOUND','909327-3',0.333,38687);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1678,'COMPOUND','909327-3',0.111,38688);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1679,'COMPOUND','909327-3',0.037,38689);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1680,'COMPOUND','909327-3',0.0123,38690);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1681,'COMPOUND','909327-3',0.00412,38691);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1682,'COMPOUND','909327-3',0.00137,38692);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1683,'COMPOUND','909327-3',0.000457,38693);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1684,'COMPOUND','909327-3',0.000152,38694);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1685,'COMPOUND','957395-5',1.5,38695);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1686,'COMPOUND','957395-5',0.5,38696);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1687,'COMPOUND','957395-5',0.167,38697);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1688,'COMPOUND','957395-5',0.0556,38698);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1689,'COMPOUND','957395-5',0.0185,38699);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1690,'COMPOUND','957395-5',0.00617,38700);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1691,'COMPOUND','957395-5',0.00206,38701);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1692,'COMPOUND','957395-5',0.000686,38702);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1693,'COMPOUND','957395-5',0.000229,38703);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1694,'COMPOUND','957395-5',7.62e-06,38704);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1695,'COMPOUND','000701-1',3e-07,38705);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1696,'COMPOUND','000701-1',3e-07,38706);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1697,'COMPOUND','000702-1',3e-06,38707);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1698,'COMPOUND','000702-1',3e-06,38708);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1699,'COMPOUND','1125538-1',1.5,38709);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1700,'COMPOUND','1125538-1',0.5,38710);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1701,'COMPOUND','1125538-1',0.167,38711);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1702,'COMPOUND','1125538-1',0.0556,38712);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1703,'COMPOUND','1125538-1',0.0185,38713);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1704,'COMPOUND','1125538-1',0.00617,38714);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1705,'COMPOUND','1125538-1',0.00206,38715);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1706,'COMPOUND','1125538-1',0.000686,38716);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1707,'COMPOUND','1125538-1',0.000229,38717);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1708,'COMPOUND','1125538-1',7.62e-06,38718);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1709,'COMPOUND','1124374-1',3,38719);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1710,'COMPOUND','1124374-1',1,38720);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1711,'COMPOUND','1124374-1',0.333,38721);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1712,'COMPOUND','1124374-1',0.111,38722);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1713,'COMPOUND','1124374-1',0.037,38723);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1714,'COMPOUND','1124374-1',0.0123,38724);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1715,'COMPOUND','1124374-1',0.00412,38725);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1716,'COMPOUND','1124374-1',0.00137,38726);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1717,'COMPOUND','1124374-1',0.000457,38727);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1718,'COMPOUND','1124374-1',0.000152,38728);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1719,'COMPOUND','000701-1',3e-07,38729);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1720,'COMPOUND','000701-1',3e-07,38730);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1721,'COMPOUND','000702-1',3e-06,38731);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1722,'COMPOUND','000702-1',3e-06,38732);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1723,'COMPOUND','603016-5',3,38733);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1724,'COMPOUND','603016-5',1,38734);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1725,'COMPOUND','603016-5',0.333,38735);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1726,'COMPOUND','603016-5',0.111,38736);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1727,'COMPOUND','603016-5',0.037,38737);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1728,'COMPOUND','603016-5',0.0123,38738);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1729,'COMPOUND','603016-5',0.00412,38739);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1730,'COMPOUND','603016-5',0.00137,38740);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1731,'COMPOUND','603016-5',0.000457,38741);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1732,'COMPOUND','603016-5',0.000152,38742);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1733,'COMPOUND','1114606-2',1.5,38743);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1734,'COMPOUND','1114606-2',0.5,38744);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1735,'COMPOUND','1114606-2',0.167,38745);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1736,'COMPOUND','1114606-2',0.0556,38746);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1737,'COMPOUND','1114606-2',0.0185,38747);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1738,'COMPOUND','1114606-2',0.00617,38748);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1739,'COMPOUND','1114606-2',0.00206,38749);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1740,'COMPOUND','1114606-2',0.000686,38750);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1741,'COMPOUND','1114606-2',0.000229,38751);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1742,'COMPOUND','1114606-2',7.62e-06,38752);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1743,'COMPOUND','000701-1',3e-07,38753);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1744,'COMPOUND','000701-1',3e-07,38754);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1745,'COMPOUND','000702-1',3e-06,38755);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1746,'COMPOUND','000702-1',3e-06,38756);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1747,'COMPOUND','1118435-4',1.5,38757);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1748,'COMPOUND','1118435-4',0.5,38758);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1749,'COMPOUND','1118435-4',0.167,38759);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1750,'COMPOUND','1118435-4',0.0556,38760);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1751,'COMPOUND','1118435-4',0.0185,38761);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1752,'COMPOUND','1118435-4',0.00617,38762);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1753,'COMPOUND','1118435-4',0.00206,38763);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1754,'COMPOUND','1118435-4',0.000686,38764);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1755,'COMPOUND','1118435-4',0.000229,38765);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1756,'COMPOUND','1118435-4',7.62e-06,38766);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1757,'COMPOUND','1119195-1',3,38767);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1758,'COMPOUND','1119195-1',1,38768);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1759,'COMPOUND','1119195-1',0.333,38769);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1760,'COMPOUND','1119195-1',0.111,38770);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1761,'COMPOUND','1119195-1',0.037,38771);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1762,'COMPOUND','1119195-1',0.0123,38772);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1763,'COMPOUND','1119195-1',0.00412,38773);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1764,'COMPOUND','1119195-1',0.00137,38774);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1765,'COMPOUND','1119195-1',0.000457,38775);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1766,'COMPOUND','1119195-1',0.000152,38776);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1767,'COMPOUND','000701-1',3e-07,38777);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1768,'COMPOUND','000701-1',3e-07,38778);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1769,'COMPOUND','000701-1',3e-07,38779);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1770,'COMPOUND','000701-1',3e-07,38780);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1771,'COMPOUND','900683-2',3,38781);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1772,'COMPOUND','900683-2',1,38782);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1773,'COMPOUND','900683-2',0.333,38783);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1774,'COMPOUND','900683-2',0.111,38784);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1775,'COMPOUND','900683-2',0.037,38785);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1776,'COMPOUND','900683-2',0.0123,38786);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1777,'COMPOUND','900683-2',0.00412,38787);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1778,'COMPOUND','900683-2',0.00137,38788);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1779,'COMPOUND','900683-2',0.000457,38789);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1780,'COMPOUND','900683-2',0.000152,38790);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1781,'COMPOUND','1124940-1',1.5,38791);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1782,'COMPOUND','1124940-1',0.5,38792);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1783,'COMPOUND','1124940-1',0.167,38793);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1784,'COMPOUND','1124940-1',0.0556,38794);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1785,'COMPOUND','1124940-1',0.0185,38795);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1786,'COMPOUND','1124940-1',0.00617,38796);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1787,'COMPOUND','1124940-1',0.00206,38797);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1788,'COMPOUND','1124940-1',0.000686,38798);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1789,'COMPOUND','1124940-1',0.000229,38799);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1790,'COMPOUND','1124940-1',7.62e-06,38800);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1791,'COMPOUND','000702-1',3e-06,38801);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1792,'COMPOUND','000702-1',3e-06,38802);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1793,'COMPOUND','000701-1',3e-07,38803);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1794,'COMPOUND','000701-1',3e-07,38804);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1795,'COMPOUND','1116765-2',1.5,38805);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1796,'COMPOUND','1116765-2',0.5,38806);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1797,'COMPOUND','1116765-2',0.167,38807);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1798,'COMPOUND','1116765-2',0.0556,38808);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1799,'COMPOUND','1116765-2',0.0185,38809);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1800,'COMPOUND','1116765-2',0.00617,38810);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1801,'COMPOUND','1116765-2',0.00206,38811);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1802,'COMPOUND','1116765-2',0.000686,38812);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1803,'COMPOUND','1116765-2',0.000229,38813);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1804,'COMPOUND','1116765-2',7.62e-06,38814);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1805,'COMPOUND','1119154-1',3,38815);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1806,'COMPOUND','1119154-1',1,38816);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1807,'COMPOUND','1119154-1',0.334,38817);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1808,'COMPOUND','1119154-1',0.111,38818);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1809,'COMPOUND','1119154-1',0.0371,38819);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1810,'COMPOUND','1119154-1',0.0124,38820);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1811,'COMPOUND','1119154-1',0.00412,38821);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1812,'COMPOUND','1119154-1',0.00137,38822);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1813,'COMPOUND','1119154-1',0.000458,38823);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1814,'COMPOUND','1119154-1',1.53e-05,38824);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1815,'COMPOUND','000702-1',3e-06,38825);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1816,'COMPOUND','000702-1',3e-06,38826);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1817,'COMPOUND','000701-1',3e-07,38827);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1818,'COMPOUND','000701-1',3e-07,38828);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1819,'COMPOUND','1118495-1',3,38829);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1820,'COMPOUND','1118495-1',1,38830);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1821,'COMPOUND','1118495-1',0.333,38831);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1822,'COMPOUND','1118495-1',0.111,38832);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1823,'COMPOUND','1118495-1',0.037,38833);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1824,'COMPOUND','1118495-1',0.0123,38834);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1825,'COMPOUND','1118495-1',0.00412,38835);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1826,'COMPOUND','1118495-1',0.00137,38836);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1827,'COMPOUND','1118495-1',0.000457,38837);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1828,'COMPOUND','1118495-1',0.000152,38838);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1829,'COMPOUND','1125539-1',1.5,38839);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1830,'COMPOUND','1125539-1',0.5,38840);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1831,'COMPOUND','1125539-1',0.167,38841);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1832,'COMPOUND','1125539-1',0.0556,38842);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1833,'COMPOUND','1125539-1',0.0185,38843);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1834,'COMPOUND','1125539-1',0.00617,38844);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1835,'COMPOUND','1125539-1',0.00206,38845);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1836,'COMPOUND','1125539-1',0.000686,38846);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1837,'COMPOUND','1125539-1',0.000229,38847);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1838,'COMPOUND','1125539-1',7.62e-06,38848);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1839,'COMPOUND','000702-1',3e-06,38849);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1840,'COMPOUND','000702-1',3e-06,38850);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1841,'COMPOUND','000701-1',3e-07,38851);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1842,'COMPOUND','000701-1',3e-07,38852);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1843,'COMPOUND','1125463-1',1.5,38853);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1844,'COMPOUND','1125463-1',0.5,38854);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1845,'COMPOUND','1125463-1',0.167,38855);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1846,'COMPOUND','1125463-1',0.0556,38856);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1847,'COMPOUND','1125463-1',0.0185,38857);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1848,'COMPOUND','1125463-1',0.00617,38858);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1849,'COMPOUND','1125463-1',0.00206,38859);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1850,'COMPOUND','1125463-1',0.000686,38860);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1851,'COMPOUND','1125463-1',0.000229,38861);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1852,'COMPOUND','1125463-1',7.62e-06,38862);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1853,'COMPOUND','1125146-1',3,38863);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1854,'COMPOUND','1125146-1',1,38864);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1855,'COMPOUND','1125146-1',0.333,38865);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1856,'COMPOUND','1125146-1',0.111,38866);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1857,'COMPOUND','1125146-1',0.037,38867);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1858,'COMPOUND','1125146-1',0.0123,38868);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1859,'COMPOUND','1125146-1',0.00411,38869);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1860,'COMPOUND','1125146-1',0.00137,38870);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1861,'COMPOUND','1125146-1',0.000457,38871);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1862,'COMPOUND','1125146-1',0.000152,38872);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1863,'COMPOUND','000702-1',3e-06,38873);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1864,'COMPOUND','000702-1',3e-06,38874);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1865,'COMPOUND','000701-1',3e-07,38875);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1866,'COMPOUND','000701-1',3e-07,38876);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1867,'COMPOUND','1119280-1',3,38877);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1868,'COMPOUND','1119280-1',1,38878);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1869,'COMPOUND','1119280-1',0.333,38879);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1870,'COMPOUND','1119280-1',0.111,38880);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1871,'COMPOUND','1119280-1',0.037,38881);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1872,'COMPOUND','1119280-1',0.0123,38882);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1873,'COMPOUND','1119280-1',0.00412,38883);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1874,'COMPOUND','1119280-1',0.00137,38884);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1875,'COMPOUND','1119280-1',0.000457,38885);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1876,'COMPOUND','1119280-1',0.000152,38886);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1877,'COMPOUND','1125545-1',1.5,38887);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1878,'COMPOUND','1125545-1',0.5,38888);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1879,'COMPOUND','1125545-1',0.167,38889);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1880,'COMPOUND','1125545-1',0.0556,38890);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1881,'COMPOUND','1125545-1',0.0185,38891);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1882,'COMPOUND','1125545-1',0.00617,38892);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1883,'COMPOUND','1125545-1',0.00206,38893);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1884,'COMPOUND','1125545-1',0.000686,38894);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1885,'COMPOUND','1125545-1',0.000229,38895);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1886,'COMPOUND','1125545-1',7.62e-06,38896);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1887,'COMPOUND','000702-1',3e-06,38897);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1888,'COMPOUND','000702-1',3e-06,38898);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1889,'COMPOUND','000701-1',3e-07,38899);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1890,'COMPOUND','000701-1',3e-07,38900);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1891,'COMPOUND','1118497-2',3,38901);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1892,'COMPOUND','1118497-2',1,38902);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1893,'COMPOUND','1118497-2',0.333,38903);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1894,'COMPOUND','1118497-2',0.111,38904);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1895,'COMPOUND','1118497-2',0.037,38905);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1896,'COMPOUND','1118497-2',0.0123,38906);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1897,'COMPOUND','1118497-2',0.00412,38907);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1898,'COMPOUND','1118497-2',0.00137,38908);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1899,'COMPOUND','1118497-2',0.000457,38909);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1900,'COMPOUND','1118497-2',0.000152,38910);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1901,'COMPOUND','1125212-1',3,38911);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1902,'COMPOUND','1125212-1',1,38912);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1903,'COMPOUND','1125212-1',0.333,38913);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1904,'COMPOUND','1125212-1',0.111,38914);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1905,'COMPOUND','1125212-1',0.037,38915);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1906,'COMPOUND','1125212-1',0.0123,38916);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1907,'COMPOUND','1125212-1',0.00412,38917);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1908,'COMPOUND','1125212-1',0.00137,38918);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1909,'COMPOUND','1125212-1',0.000457,38919);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1910,'COMPOUND','1125212-1',0.000152,38920);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1911,'COMPOUND','000702-1',3e-06,38921);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1912,'COMPOUND','000702-1',3e-06,38922);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1913,'COMPOUND','000701-1',3e-07,38923);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1914,'COMPOUND','000701-1',3e-07,38924);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1915,'COMPOUND','1122312-1',3,38925);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1916,'COMPOUND','1122312-1',1,38926);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1917,'COMPOUND','1122312-1',0.333,38927);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1918,'COMPOUND','1122312-1',0.111,38928);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1919,'COMPOUND','1122312-1',0.037,38929);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1920,'COMPOUND','1122312-1',0.00123,38930);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1921,'COMPOUND','1122312-1',0.00411,38931);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1922,'COMPOUND','1122312-1',0.00137,38932);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1923,'COMPOUND','1122312-1',0.000457,38933);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1924,'COMPOUND','1122312-1',0.000152,38934);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1925,'COMPOUND','1125549-1',1.5,38935);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1926,'COMPOUND','1125549-1',0.5,38936);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1927,'COMPOUND','1125549-1',0.167,38937);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1928,'COMPOUND','1125549-1',0.0556,38938);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1929,'COMPOUND','1125549-1',0.0185,38939);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1930,'COMPOUND','1125549-1',0.00617,38940);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1931,'COMPOUND','1125549-1',0.00206,38941);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1932,'COMPOUND','1125549-1',0.000686,38942);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1933,'COMPOUND','1125549-1',0.000229,38943);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1934,'COMPOUND','1125549-1',7.62e-06,38944);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1935,'COMPOUND','000702-1',3e-06,38945);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1936,'COMPOUND','000702-1',3e-06,38946);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1937,'COMPOUND','000701-1',3e-07,38947);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1938,'COMPOUND','000701-1',3e-07,38948);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1939,'COMPOUND','1117337-1',3,38949);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1940,'COMPOUND','1117337-1',1,38950);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1941,'COMPOUND','1117337-1',0.333,38951);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1942,'COMPOUND','1117337-1',0.111,38952);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1943,'COMPOUND','1117337-1',0.037,38953);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1944,'COMPOUND','1117337-1',0.0123,38954);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1945,'COMPOUND','1117337-1',0.00412,38955);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1946,'COMPOUND','1117337-1',0.00137,38956);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1947,'COMPOUND','1117337-1',0.000457,38957);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1948,'COMPOUND','1117337-1',0.000152,38958);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1949,'COMPOUND','1125214-1',3,38959);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1950,'COMPOUND','1125214-1',1,38960);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1951,'COMPOUND','1125214-1',0.333,38961);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1952,'COMPOUND','1125214-1',0.111,38962);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1953,'COMPOUND','1125214-1',0.037,38963);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1954,'COMPOUND','1125214-1',0.0123,38964);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1955,'COMPOUND','1125214-1',0.00412,38965);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1956,'COMPOUND','1125214-1',0.00137,38966);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1957,'COMPOUND','1125214-1',0.000457,38967);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1958,'COMPOUND','1125214-1',0.000152,38968);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1959,'COMPOUND','000702-1',3e-06,38969);
insert into plates.hca_well_substance(id,type,name,concentration,well_id)
values(1960,'COMPOUND','000702-1',3e-06,38970);
