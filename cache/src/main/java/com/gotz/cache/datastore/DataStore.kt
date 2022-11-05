package com.gotz.cache.datastore

import androidx.datastore.core.Serializer
import com.gotz.cache.User.UserDataStore
import java.io.InputStream
import java.io.OutputStream

object UserDataStoreSerializer: Serializer<UserDataStore>{
    override val defaultValue: UserDataStore
        get() = UserDataStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserDataStore =
        UserDataStore.parseFrom(input)

    override suspend fun writeTo(t: UserDataStore, output: OutputStream) =
        t.writeTo(output)
}