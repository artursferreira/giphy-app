package com.artur.giphyapp.data.remote.giphy.repository

import com.artur.giphyapp.data.local.GifDao
import com.artur.giphyapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.giphyapp.extensions.mapToGifItem
import com.artur.giphyapp.extensions.resultLiveData

class GiphyRepository(
    val dao: GifDao,
    private val remoteSource: GiphyRemoteDataSource
) {

    val trendingGifs = resultLiveData(
        databaseQuery = { dao.getAllTrending() },
        networkCall = { remoteSource.getTrending() },
        saveCallResult = {
            dao.deleteAllTrending()
            dao.insertAll(*it.mapToGifItem().map { it.copy(isTrending = true) }.toTypedArray()) }
    )

}