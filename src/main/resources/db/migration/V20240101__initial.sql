create sequence domain_event_entry_seq start with 1 increment by 50;
create table domain_event_entry
(
    global_index         bigint       not null,
    sequence_number      bigint       not null,
    aggregate_identifier varchar(255) not null,
    event_identifier     varchar(255) not null unique,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    type                 varchar(255),
    meta_data            oid,
    payload              oid          not null,
    primary key (global_index),
    unique (aggregate_identifier, sequence_number)
);
create table snapshot_event_entry
(
    sequence_number      bigint       not null,
    aggregate_identifier varchar(255) not null,
    event_identifier     varchar(255) not null unique,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    type                 varchar(255) not null,
    meta_data            oid,
    payload              oid          not null,
    primary key (sequence_number, aggregate_identifier, type)
);
create table token_entry
(
    segment        integer      not null,
    owner          varchar(255),
    processor_name varchar(255) not null,
    timestamp      varchar(255) not null,
    token_type     varchar(255),
    token          oid,
    primary key (segment, processor_name)
)
