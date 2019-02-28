-- updates the ranks of a list to be even
CREATE FUNCTION update_ranks(list_id UUID) RETURNS void AS $$
    WITH cte AS (
        SELECT id,
        rank() OVER(ORDER BY rank) * 2 AS rnk
        FROM item
        WHERE list_id = $1
    )
    UPDATE item
    SET rank = cte.rnk
    FROM cte
    WHERE item.id = cte.id;
$$ LANGUAGE SQL;

-- move an item one rank up in the list
CREATE FUNCTION rank_up(item_id UUID) RETURNS item AS $$
    UPDATE item SET rank = rank - 3 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
            SELECT list_id
            FROM item
            WHERE id = $1
        ) AS a;
    SELECT * FROM item WHERE id = item_id;
$$ LANGUAGE SQL;

-- move an item down in rank
CREATE FUNCTION rank_down(item_id UUID) RETURNS item AS $$
    UPDATE item SET rank = rank + 3 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
            SELECT list_id
            FROM item
            WHERE id = $1
        ) AS a;
    SELECT * FROM item WHERE id = item_id;
$$ LANGUAGE SQL;

-- move an item to the top of the list
CREATE FUNCTION rank_top(item_id UUID) RETURNS item AS $$
    UPDATE item SET rank = 1 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
        SELECT list_id
        FROM item
        WHERE id = $1
    ) AS a;
    SELECT * FROM item WHERE id = item_id;
$$ LANGUAGE SQL;

-- move an item to the bottom of the list
CREATE FUNCTION rank_bottom(item_id UUID) RETURNS item AS $$
    -- https://www.postgresql.org/docs/current/datatype-numeric.html
    -- 2147483647 is the max for the integer type
    UPDATE item SET rank = 2147483647 WHERE id = $1;
    SELECT update_ranks(a.list_id) FROM (
            SELECT list_id
            FROM item
            WHERE id = $1
        ) AS a;
    SELECT * FROM item WHERE id = item_id;
$$ LANGUAGE SQL;
