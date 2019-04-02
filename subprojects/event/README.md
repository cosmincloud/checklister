# Checklister: event

An event API for Checklister.

## Design details

The `Event` class represents a event that can be sourced by the
application.  An `EventSink` accepts events from the 
application and publishes them externally.
An `EventSerializer` converts an `Event` object into a binary
representation, and an `EventDeserializer` takes a binary
representation of an event and re-creates the `Event` object. 

The `ListEvents` and `ItemEvents` interfaces represent actions
that can generate events for lists and items, respectively.