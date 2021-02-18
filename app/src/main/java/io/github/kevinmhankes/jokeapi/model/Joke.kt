package io.github.kevinmhankes.jokeapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * Primary data to parse, display, and save to the database from the joke API
 */
@Entity(tableName = "jokes")
data class Joke(
    @PrimaryKey
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("category") val category: String,
    @field:SerializedName("setup") val setup: String,
    @field:SerializedName("delivery") val delivery: String
)