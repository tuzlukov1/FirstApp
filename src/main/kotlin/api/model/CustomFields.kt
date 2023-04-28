package api.model

import com.google.gson.annotations.SerializedName

data class CustomFields(
    val paySourcesFilter: String?,
    val themeCode: String?,
    @Transient
    val otherParams: Map<String, String>?
)