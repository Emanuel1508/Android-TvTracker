package lynx.internship.tv.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lynx.internship.tv.data.api.Movie
import lynx.internship.tv.domain.ResponseListener
import lynx.internship.tv.domain.TvTrackerFactory
import lynx.internship.tv.domain.TvTrackerRepository
import lynx.internship.tv.ui.BaseViewModel

class TopRatedViewModel : BaseViewModel() {

    private val repository: TvTrackerRepository by lazy { TvTrackerFactory.createTvTrackerRepository()}
    private val topRatedListMutableLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val topRatedListLiveData: LiveData<List<Movie>> = topRatedListMutableLiveData

    fun fetchTopRated() {
        repository.fetchTopRated(object : ResponseListener<List<Movie>> {
            override fun onSuccess(value: List<Movie>) {
                topRatedListMutableLiveData.postValue(value)
            }

            override fun onError(error: Exception) {
                errorMutableLiveData.postValue(error.message)
            }
        })
    }
}