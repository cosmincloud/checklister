-- move an item one rank up in the list
DROP FUNCTION IF EXISTS rank_up;
CREATE FUNCTION rank_up(UUID, TIMESTAMP WITH TIME ZONE)
RETURNS item AS $$
    UPDATE item SET rank = rank - 3, last_modified = $2 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
            SELECT list_id
            FROM item
            WHERE id = $1
        ) AS a;
    SELECT * FROM item WHERE id = $1;
$$ LANGUAGE SQL;

-- move an item down in rank
DROP FUNCTION IF EXISTS rank_down;
CREATE FUNCTION rank_down(item_id UUID, TIMESTAMP WITH TIME ZONE)
RETURNS item AS $$
    UPDATE item SET rank = rank + 3, last_modified = $2 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
            SELECT list_id
            FROM item
            WHERE id = $1
        ) AS a;
    SELECT * FROM item WHERE id = $1;
$$ LANGUAGE SQL;

-- move an item to the top of the list
DROP FUNCTION IF EXISTS rank_top;
CREATE FUNCTION rank_top(item_id UUID, TIMESTAMP WITH TIME ZONE)
RETURNS item AS $$
    UPDATE item SET rank = 1, last_modified = $2 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
        SELECT list_id
        FROM item
        WHERE id = $1
    ) AS a;
    SELECT * FROM item WHERE id = $1;
$$ LANGUAGE SQL;

-- move an item to the bottom of the list
DROP FUNCTION IF EXISTS rank_bottom;
CREATE FUNCTION rank_bottom(item_id UUID, TIMESTAMP WITH TIME ZONE)
RETURNS item AS $$
    -- https://www.postgresql.org/docs/current/datatype-numeric.html
    -- 2147483647 is the max for the integer type
    UPDATE item SET rank = 2147483647, last_modified = $2 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
            SELECT list_id
            FROM item
            WHERE id = $1
        ) AS a;
    SELECT * FROM item WHERE id = $1;
$$ LANGUAGE SQL;
