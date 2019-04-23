-- https://www.postgresql.org/docs/current/static/datatype-uuid.html
CREATE TABLE list_history (
    event_id      UUID PRIMARY KEY,
    list_id       UUID,
    before        JSONB,
    after         JSONB,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE item_history (
    event_id      UUID PRIMARY KEY,
    item_id       UUID,
    list_id       UUID,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    UNIQUE(list_id, rank)
);
