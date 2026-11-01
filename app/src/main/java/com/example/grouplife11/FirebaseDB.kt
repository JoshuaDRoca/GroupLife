package com.example.grouplife11

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseDB {
    private val db_events = FirebaseDatabase.getInstance().getReference("events")
//    private val db_users = FirebaseDatabase.getInstance().getReference("users")

    fun addEvent(event: Event) {
        val id = db_events.push().key!!
        db_events.child(id).setValue(event.copy(id = id))
    }

    fun getEvents(onDataChange: (List<Event>) -> Unit) {
        db_events.addValueEventListener(object: ValueEventListener {

            //retrieve all available events from current snapshot of the database
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventList = mutableListOf<Event>()
                snapshot.children.forEach {
                    it.getValue(Event::class.java)?.let { event -> eventList.add(event) }
                }
                onDataChange(eventList)
            }

            override fun onCancelled(error: DatabaseError) {
                // handles database errors, leaving empty for now.
            }

        })
    }

}