CREATE DATABASE poster
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
	
CREATE SCHEMA IF NOT EXISTS spring
    AUTHORIZATION postgres;
	

CREATE TABLE IF NOT EXISTS spring.concerts
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying(30) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    dt_event timestamp without time zone,
    dt_dt_end_of_sale timestamp without time zone,
    type character varying(10) COLLATE pg_catalog."default",
    status character varying(10) COLLATE pg_catalog."default",
    category character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT concerts_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS spring.concerts
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS spring.films
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying(30) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    dt_event timestamp without time zone,
    dt_dt_end_of_sale timestamp without time zone,
    type character varying(10) COLLATE pg_catalog."default",
    status character varying(10) COLLATE pg_catalog."default",
    country character varying(100) COLLATE pg_catalog."default",
    release_year bigint,
    release_date character varying(30) COLLATE pg_catalog."default",
    duration bigint,
    CONSTRAINT films_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS spring.films
    OWNER to postgres;