create schema plateservice;

create table plateservice.hca_project (
	project_id			bigserial primary key,
	name				text not null,
	description			text,
	owner				text,
	team_code			text default 'NONE',
	access_scope		text
);

create table plateservice.hca_experiment (
	experiment_id		bigserial primary key, 
	experiment_name		text,
	experiment_dt		timestamp, 
	experiment_user		text,
	description			text,
	closed				boolean default false,
	comments			text,
	multiplo_method		text,
	multiplo_parameter	text
);

create table plateservice.hca_plate (
	plate_id			bigserial primary key,
	experiment_id		bigint not null references plateservice.hca_experiment (experiment_id) on delete cascade,
	sequence_in_run		integer not null,
	barcode 			text,
	description			text,
	plate_info 			text,
	link_status 		integer default 0, 
	link_user			text,
	link_dt				timestamp,
	calc_status			integer default 0,
	calc_error			text,
	calc_dt				timestamp, 
	validate_status		integer default 0, 
	validate_user		text,
	validate_dt			timestamp, 
	approve_status		integer default 0, 
	approve_user		text,
	approve_dt			timestamp, 
	upload_status		integer default 0, 
	upload_user			text,
	upload_dt			timestamp, 
	plate_rows			integer,
	plate_columns 		integer
);

create index hca_plate_ix_barcode on plateservice.hca_plate (barcode);

create table plateservice.hca_plate_well (
	well_id					bigserial primary key,
	plate_id				bigint not null references plateservice.hca_plate (plate_id) on delete cascade,
	row_nr					integer not null,
	col_nr					integer not null,
	welltype_code			text,
	concentration			double precision, 
	is_valid				integer default 0,
	platecompound_id		bigint,
	description				text
);

create index hca_plate_well_ix_plate_id on plateservice.hca_plate_well (plate_id);
