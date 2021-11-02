package com.vm.frameworkexample

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi


const val FRAGMENT_TAG: String = "FragmentScenario_Fragment_Tag"

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    initialState: Lifecycle.State = Lifecycle.State.RESUMED,
    crossinline action: T.() -> Unit = {}
): ActivityScenario<HiltTestActivity> {
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId)

    return ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs

        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, FRAGMENT_TAG)
            .setMaxLifecycle(fragment, initialState)
            .commitNow()

        (fragment as T).action()
    }

}

fun ActivityScenario<HiltTestActivity>.moveFragmentState(newState: Lifecycle.State) {
    if (newState == Lifecycle.State.DESTROYED) {
        onActivity { activity ->
            val fragment = activity.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
            // Null means the fragment has been destroyed already.
            if (fragment != null) {
                activity
                    .supportFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow()
            }
        }
    } else {
        onActivity { activity ->
            val fragment = activity.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
            Preconditions.checkNotNull(
                fragment,
                "The fragment has been removed from FragmentManager already."
            )
            activity.supportFragmentManager
                .beginTransaction()
                .setMaxLifecycle(fragment!!, newState)
                .commitNow()
        }
    }
}











