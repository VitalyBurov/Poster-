CREATE DATABASE classifier
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
	
CREATE SCHEMA IF NOT EXISTS spring
    AUTHORIZATION postgres;
	
CREATE TABLE spring.concert_category
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title text COLLATE pg_catalog."default",
    CONSTRAINT concert_category_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE spring.concert_category
    OWNER to postgres;
	
	
	
CREATE TABLE IF NOT EXISTS spring.countries
(
    uuid uuid NOT NULL,
	dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying(3) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    CONSTRAINT countries_pkey PRIMARY KEY (uuid),
    CONSTRAINT unique_title_key UNIQUE (title)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS spring.countries
    OWNER to postgres;