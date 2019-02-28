-- https://www.postgresql.org/docs/current/static/pgcrypto.html#id-1.11.7.35.11
-- Enables the gen_random_uuid()
CREATE EXTENSION pgcrypto;

-- https://www.postgresql.org/docs/current/static/datatype-uuid.html
CREATE TABLE list (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title         VARCHAR(512) NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    last_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE item (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    content       TEXT NOT NULL,
    rank          INT NOT NULL,
    list_id       UUID REFERENCES list (id) NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    last_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    UNIQUE(list_id, rank)
);
