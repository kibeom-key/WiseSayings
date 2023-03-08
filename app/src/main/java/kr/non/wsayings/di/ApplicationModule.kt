package kr.non.wsayings.di

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kr.non.wsayings.UserPreferences
import kr.non.wsayings.db.AppDatabase
import kr.non.wsayings.db.WiseSayingRepository
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideWiseSayingRepository(database: AppDatabase,
                                    userPreferences: UserPreferences) : WiseSayingRepository{
        return WiseSayingRepository(database, userPreferences)
    }

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context) : UserPreferences{
        return UserPreferences(context)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase{
        return AppDatabase.getInstance(context)!!
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabaseReference() : DatabaseReference{
        return Firebase.database.reference
    }



}


