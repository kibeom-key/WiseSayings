package kr.non.wsayings.ui.vmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.non.wsayings.db.WiseSaying
import kr.non.wsayings.db.WiseSayingRepository

class MainViewModel @ViewModelInject constructor(
    private val wiseSayingRepository: WiseSayingRepository
) : ViewModel() {

    private val wiseSaying = MutableLiveData<WiseSaying>()

    /*
    init {
        loadWiseSaying()
    }
    */

    fun getWiseSaying() : LiveData<WiseSaying>{
        return wiseSaying
    }

    fun loadWiseSaying(){
        wiseSaying.value = wiseSayingRepository.getWiseSaying()
    }

}