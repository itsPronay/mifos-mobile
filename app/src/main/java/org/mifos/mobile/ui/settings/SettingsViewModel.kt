package org.mifos.mobile.ui.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mifos.mobile.R
import org.mifos.mobile.api.local.PreferencesHelper
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesHelper: PreferencesHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    @OptIn(SavedStateHandleSaveableApi::class)
    var invokeEndpointUpdate by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    @OptIn(SavedStateHandleSaveableApi::class)
    var invokeLanguageUpdate  by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    @OptIn(SavedStateHandleSaveableApi::class)
    var invokeThemeUpdate by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    fun getTenant(): String? {
        return preferencesHelper.tenant
    }

    fun getBaseUrl(): String? {
        return preferencesHelper.baseUrl
    }

    fun tryUpdatingEndpoint(etBaseUrl: String?, etTenant: String?)  : Boolean{
        if (!isFieldEmpty(etBaseUrl, etTenant) && isUrlValid(etBaseUrl)) {
            if(!(etBaseUrl.equals(getBaseUrl()) && etTenant.equals(getTenant())) ){
                updateConfiguration(etBaseUrl, etTenant)
                preferencesHelper.clear()
                return true
            }
        }
        return false
    }

    private fun isFieldEmpty(baseUrl: String?, tenant: String?): Boolean {
        return baseUrl?.trim()?.isEmpty() == true || tenant?.trim()?.isEmpty() == true
    }

    private fun isUrlValid(baseUrl: String?): Boolean {
        return try {
            if (!baseUrl.isNullOrBlank()) {
                URL(baseUrl)
                true
            } else {
                false
            }
        } catch (e: MalformedURLException) {
            false
        }
    }

    private fun updateConfiguration(baseUrl: String?, tenant: String?) {
        preferencesHelper.updateConfiguration(baseUrl, tenant)
    }

    fun getSettingsCards(): List<SettingsCardItem> {
        return listOf(
            SettingsCardItem.Password,
            SettingsCardItem.Passcode,
            SettingsCardItem.Language,
            SettingsCardItem.Theme,
            SettingsCardItem.EndPoint
        )
    }
}


sealed class SettingsCardItem(
    val title: Int,
    val details: Int,
    val icon: Int,
    val subclassOf : Int,
    val firstItemInSubclass : Boolean = false,
    val showDividerInBottom : Boolean = false
) {
    data object Password : SettingsCardItem(
        title = R.string.change_password,
        details = R.string.change_account_password,
        icon = R.drawable.ic_lock_black_24dp,
        firstItemInSubclass = true,
        subclassOf = R.string.accounts
    )

    data object Passcode : SettingsCardItem(
        title = R.string.change_passcode,
        details = R.string.change_app_passcode,
        icon = R.drawable.ic_passcode,
        showDividerInBottom = true,
        subclassOf = R.string.accounts
    )

    data object Language : SettingsCardItem(
        title = R.string.language,
        details = R.string.choose_language,
        icon = R.drawable.ic_translate,
        firstItemInSubclass = true,
        subclassOf = R.string.other
    )

    data object Theme : SettingsCardItem(
        title = R.string.theme,
        details = R.string.change_app_theme,
        icon = R.drawable.ic_baseline_dark_mode_24,
        subclassOf = R.string.other
    )

    data object EndPoint : SettingsCardItem(
        title = R.string.pref_base_url_title,
        details = R.string.pref_base_url_desc,
        icon = R.drawable.ic_update,
        subclassOf = R.string.other
    )
}
