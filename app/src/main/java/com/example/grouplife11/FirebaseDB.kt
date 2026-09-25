package com.example.grouplife11

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseDB {
    private val db = FirebaseDatabase.getInstance().getReference("events")

    fun addEvent(event: Event) {
        val id = db.push().key!!
        db.child(id).setValue(event.copy(id = id))
    }

    fun getEvents(onDataChange: (List<Event>) -> Unit) {
        db.addValueEventListener(object: ValueEventListener {

            //retrieve all available events from current snapshot of the db
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventList = mutableListOf<Event>()
                snapshot.children.forEach {
                    it.getValue(Event::class.java)?.let { event -> eventList.add(event) }
                }
                onDataChange(eventList)
            }

            override fun onCancelled(error: DatabaseError) {
                // handles db errors, leaving empty for now.
            }

        })
    }

}