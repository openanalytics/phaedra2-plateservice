-- Create schema plate service if not exists
create schema if not exists plates;

-- Drop tables if exist
drop table if exists plates.hca_plate_measurement;
drop table if exists plates.hca_well_substance;
drop table if exists plates.hca_well;
drop table if exists plates.hca_plate;
drop table if exists plates.hca_experiment;
drop table if exists plates.hca_project;

-- Create hca_project table
create table plates.hca_project
(
    id          bigserial not null,
    name        text      not null,
    description text,
    created_on  timestamp,
    created_by  text,
    updated_on  timestamp,
    updated_by  text,
    primary key (id)
);
grant all on table plates.hca_project to phaedra_usr;

-- Create hca_experiment table
create table plates.hca_experiment
(
    id                 bigserial not null,
    name               text      not null,
    description        text,
    status             text      not null,
    multiplo_method    text,
    multiplo_parameter text,
    created_on         timestamp,
    created_by         text,
    updated_on         timestamp,
    updated_by         text,
    project_id         bigint    not null,
    primary key (id),
    foreign key (project_id) references plates.hca_project (id) on delete cascade on update cascade
);
grant all on table plates.hca_experiment to phaedra_usr;

-- Create hca_plate table
create table plates.hca_plate
(
    id                 bigserial not null,
    barcode            text,
    description        text,
    experiment_id      bigint    not null,
    rows               integer,
    columns            integer,
    sequence           integer   not null,
    link_status        varchar(150),
    link_template_id   text,
    link_source        text,
    linked_on          timestamp,
    calculation_status varchar(150),
    calculation_error  text,
    calculated_by      text,
    calculated_on      timestamp,
    validation_status  varchar(150),
    validated_by       text,
    validated_on       timestamp,
    approval_status    varchar(150),
    approved_by        text,
    approved_on        timestamp,
    upload_status      varchar(150),
    uploaded_by        text,
    uploaded_on        timestamp,
    created_on         timestamp,
    created_by         text,
    updated_on         timestamp,
    updated_by         text,
    primary key (id),
    foreign key (experiment_id) references plates.hca_experiment (id) on delete cascade on update cascade
);
create index hca_plate_ix_barcode on plates.hca_plate (barcode);
grant all on table plates.hca_plate to phaedra_usr;

-- Create hca_well table
create table plates.hca_well
(
    id          bigserial not null,
    plate_id    bigint    not null,
    row         integer   not null,
    "column"    integer   not null,
    type        text      not null default 'EMPTY',
    status      varchar(150),
    compound_id bigint,
    description text,
    primary key (id),
    foreign key (plate_id) references plates.hca_plate (id) on delete cascade
);
create index hca_plate_well_ix_plate_id on plates.hca_well (plate_id);
grant all on table plates.hca_well to phaedra_usr;

-- Create hca_plate_measurement table
create table plates.hca_plate_measurement
(
    plate_id       bigint not null,
    measurement_id bigint not null,
    linked_by      text,
    linked_on      timestamp,
    primary key (plate_id, measurement_id),
    foreign key (plate_id) references plates.hca_plate (id) on delete cascade on update cascade
);
grant all on table plates.hca_plate_measurement to phaedra_usr;

--Create hca_well_substance table
create table plates.hca_well_substance
(
    id            bigserial not null,
    type          text      not null,
    name          text      not null,
    concentration double precision default 0,
    well_id       bigint    not null,
    primary key (id),
    foreign key (well_id) references plates.hca_well (id) on delete cascade on update cascade
);
grant all on table plates.hca_well_substance to phaedra_usr;