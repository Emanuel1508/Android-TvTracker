package lynx.internship.tv.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lynx.internship.tv.data.api.Movie
import lynx.internship.tv.domain.ResponseListener
import lynx.internship.tv.domain.TvTrackerFactory
import lynx.internship.tv.domain.TvTrackerRepository
import lynx.internship.tv.ui.BaseViewModel

class PopularViewModel : BaseViewModel() {

    private val repository: TvTrackerRepository by lazy { TvTrackerFactory.createTvTrackerRepository() }
    private val popularListMutableLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val popularListLiveData: LiveData<List<Movie>> = popularListMutableLiveData

    fun fetchPopular() {
        repository.fetchPopular(object : ResponseListener<List<Movie>> {
            override fun onSuccess(value: List<Movie>) {
                popularListMutableLiveData.postValue(value)
            }

            override fun onError(error: Exception) {
                errorMutableLiveData.postValue(error.message)
            }
        })
    }
}