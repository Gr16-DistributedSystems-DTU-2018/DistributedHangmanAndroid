package io.inabsentia.superhangman.data.dao

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import io.inabsentia.superhangman.data.dto.MatchDTO

class FireBaseMatchDAO private constructor() : IMatchDAO {

    private var database: FirebaseDatabase? = null
    private var myRef: DatabaseReference? = null

    init {
        database = FirebaseDatabase.getInstance()
        myRef = database!!.getReference("message")
        myRef!!.setValue("Hello, World!")

        myRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: " + value!!)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    override val all: List<MatchDTO>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun add(dto: MatchDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(id: Int): MatchDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(dto: MatchDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        @get:Synchronized
        var instance: IMatchDAO? = null
            private set

        init {
            instance = FireBaseMatchDAO()
        }
    }

}