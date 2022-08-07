package com.github.oauth.repositories.githuboauthreposview.utils.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.github.oauth.repositories.githuboauthreposview.R
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    ViewBindingDelegate(T::class.java, this)

class ViewBindingDelegate<T>(
    bindingClass: Class<T>,
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
    /** Исходные данные */ //region
    // binding
    private var binding: T? = null
    // bindMethod
    private val bindMethod = bindingClass.getMethod("bind", View::class.java)
    //endregion

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding != null) {
            return binding!!
        }

        fragment.lifecycle.addObserver(this)

        val lifecycle = this.fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error(
                "${thisRef.requireActivity().getString(R.string.error_wrong_view)} ${
                    lifecycle.currentState
                }"
            )
        }

        binding = bindMethod(null, thisRef.requireView()) as T
        return binding!!
    }

    override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    binding = null
                }
            })
        }
    }
}