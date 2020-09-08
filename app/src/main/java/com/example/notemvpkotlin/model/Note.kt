package com.example.notemvpkotlin.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(@PrimaryKey(autoGenerate = true) var id: Int?,
                var title: String?,
                var description: String?,
                var date: String?): Parcelable{

    @Ignore
    constructor(title: String?, description: String?, date: String?) : this(null, title, description, date) {

    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let { parcel.writeInt(it) }
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}