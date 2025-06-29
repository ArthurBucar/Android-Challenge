package com.android_tech_challenge.data

import com.google.gson.annotations.SerializedName

data class MainResponse(
    @SerializedName("responses")
    val responses: Responses?
)

data class Responses(
    @SerializedName("listApps")
    val listApps: ListApps?
)

data class ListApps(
    @SerializedName("datasets")
    val datasets: Datasets?
)

data class Datasets(
    @SerializedName("all")
    val all: AllData?
)

data class AllData(
    @SerializedName("data")
    val data: DataList?
)

data class DataList(
    @SerializedName("list")
    val list: List<App>?
)

data class App(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("package")
    val packageName: String? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("graphic")
    val graphic: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("developer")
    val developer: Developer? = null,
    @SerializedName("stats")
    val stats: Stats? = null,
    @SerializedName("file")
    val file: FileInfo? = null
)

data class Developer(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("website")
    val website: String? = null
)

data class Stats(
    @SerializedName("downloads")
    val downloads: String? = null,
    @SerializedName("rating")
    val rating: Rating? = null
)

data class Rating(
    @SerializedName("avg")
    val average: Double? = null,
    @SerializedName("total")
    val total: Int? = null
)

data class FileInfo(
    @SerializedName("size")
    val size: Long? = null,
    @SerializedName("vercode")
    val versionCode: Int? = null,
    @SerializedName("vername")
    val versionName: String? = null
) 