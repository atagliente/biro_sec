-- This script was generated by a beta version of the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
-- BEGIN;

-- Database: rbac

-- DROP DATABASE IF EXISTS rbac;

-- CREATE DATABASE rbac
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'Italian_Italy.1252'
--     LC_CTYPE = 'Italian_Italy.1252'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1;

-- This script was generated by a beta version of the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;

CREATE SEQUENCE IF NOT EXISTS account_id_seq;
CREATE SEQUENCE IF NOT EXISTS permission_id_seq;
CREATE SEQUENCE IF NOT EXISTS role_id_seq;
CREATE SEQUENCE IF NOT EXISTS role_users_id_seq;
CREATE SEQUENCE IF NOT EXISTS role_permission_id_seq;
CREATE SEQUENCE IF NOT EXISTS revoked_token_id_seq;



CREATE TABLE IF NOT EXISTS public.account
(
    id integer NOT NULL DEFAULT nextval('account_id_seq'::regclass),
    username character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.permission
(
    id integer NOT NULL DEFAULT nextval('permission_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT permission_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role
(
    id integer NOT NULL DEFAULT nextval('role_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role_account
(
    id integer NOT NULL DEFAULT nextval('role_users_id_seq'::regclass),
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT role_users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role_permission
(
    id integer NOT NULL DEFAULT nextval('role_permission_id_seq'::regclass),
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.revoked_token
(
    id integer NOT NULL DEFAULT nextval('revoked_token_id_seq'::regclass),
    jwt_token_digest character varying COLLATE pg_catalog."default" NOT NULL,
    revocation_date timestamp default now()
);

ALTER TABLE public.role_account DROP CONSTRAINT IF EXISTS role_users_role_id_fkey;
ALTER TABLE IF EXISTS public.role_account
    ADD CONSTRAINT role_users_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE public.role_account DROP CONSTRAINT IF EXISTS role_users_user_id_fkey;
ALTER TABLE IF EXISTS public.role_account
    ADD CONSTRAINT role_users_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE public.role_permission DROP CONSTRAINT IF EXISTS role_permission_permission_id_fkey;
ALTER TABLE IF EXISTS public.role_permission
    ADD CONSTRAINT role_permission_permission_id_fkey FOREIGN KEY (permission_id)
        REFERENCES public.permission (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE public.role_permission DROP CONSTRAINT IF EXISTS role_permission_role_id_fkey;
ALTER TABLE IF EXISTS public.role_permission
    ADD CONSTRAINT role_permission_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

END;
