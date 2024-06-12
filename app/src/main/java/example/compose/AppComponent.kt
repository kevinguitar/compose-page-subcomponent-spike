package example.compose

import androidx.activity.ComponentActivity
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * A simple Dagger component component
 */
@MergeComponent(
    scope = AppScope::class,
    modules = [GenericModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance activity: MainActivity): AppComponent
    }
}

/**
 * A module that convert the bounded activity to a generic [ComponentActivity]
 */
@Module
class GenericModule {
    @Provides
    fun provideActivity(activity: MainActivity): ComponentActivity = activity
}

interface AppScope

/**
 * A singleton object that holds the AppComponent instance
 */
object AppComponentInstance {

    private var appComponent: AppComponent? = null

    fun create(activity: MainActivity): AppComponent {
        if (appComponent != null) {
            println("AppComponent:: replacing existing component")
        }
        appComponent = DaggerAppComponent.factory().create(activity)
        return appComponent!!
    }

    fun get(): AppComponent {
        if (appComponent == null) {
            error("AppComponent is not initialized. Call create() first.")
        }
        return appComponent!!
    }
}
