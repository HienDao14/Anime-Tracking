package com.example.anilist.retrofit

import com.example.anilist.pojo.Anime
import com.example.anilist.pojo.AnimeFull
import com.example.anilist.pojo.CalledAnime
import com.example.anilist.pojo.Character
import com.example.anilist.pojo.Manga
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AniApi {

    @GET("anime/{id}/full")
    fun getAnimeById(@Path(value = "id") id : Int): Call<AnimeFull>

    @GET("top/anime")
    fun getTrendingNowAnime(@Query("filter") filter : String) : Call<CalledAnime>

    @GET("seasons/now")
    fun getSeasonAnime() : Call<CalledAnime>

    @GET("top/anime")
    fun getRankingAnime(@Query("page") page: Int): Call<CalledAnime>

    @GET("anime")
    fun getSearchAnime(@Query("q") anime: String) : Call<CalledAnime>

    @GET("anime/{id}/characters")
    fun getCharactersById(@Path(value = "id") id : Int) : Call<Character>

    //Manga
    @GET("manga/{id}/full")
    fun getMangaById(@Path(value = "id") id: Int) : Call<Manga>
}