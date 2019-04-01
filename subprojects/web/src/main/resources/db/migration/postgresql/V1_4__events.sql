CREATE TABLE event (
    id            UUID NOT NULL,
    type          TEXT NOT NULL,
    bytes         BYTEA,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);