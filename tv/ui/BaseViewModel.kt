package lynx.internship.tv.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    protected val errorMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = errorMutableLiveData
}