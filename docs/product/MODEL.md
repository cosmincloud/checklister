# Data Model

## Objects

* list - an uniquely identifiable ordered enumeration of items
* item - a piece of content as part of a list

## list

### list: fields

* id: uuid - a unique UUID for this list
* title: varchar(255) - a short title for the list

### list: operations

* size: int - returns the number of items currently part of this list
* items: list(item) - return a list of the items that are currently part of this list

## item

* id: Long
* content: String - 
* rank: int - the rank of the item in the list

# API

* /api/v1/list
  - GET: retrieve all the lists
  - POST: create a new list
* /api/v1/list/:list-id
  - GET: get a list with the :list-id
  - PATCH: update the list
  - DELETE: delete the list
* /api/v1/list/:list-id/item
  - GET: retrieve all the items of the list
  - POST: add an item to the list
* /api/v1/list/:list-id/item/:item-id
  - GET: get a specific item in the list
  - PATCH: update the item
  - DELETE: delete the item
* /api/v1/list/:list-id/item/:item-id/rank-up
  - POST: move item one rank up in the list
* /api/v1/list/:list-id/item/:item-id/rank-down
  - POST: move item one rank down in the list
* /api/v1/list/:list-id/item/:item-id/rank-top
  - POST: move item to top of list
* /api/v1/list/:list-id/item/:item-id/rank-bottom
  - POST: move item to bottom of list
* /api/v1/list/:list-id/item/:item-id/rank/:rank
    - POST: set the rank of the item
