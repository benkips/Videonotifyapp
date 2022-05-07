package com.mabnets.videouploaderapp.viewmodelz

import androidx.lifecycle.*
import com.mabnets.videouploaderapp.Repo.Repostuff
import com.mabnets.videouploaderapp.models.Details
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Healingviewmodel  @Inject constructor(private  val repostuff: Repostuff) : ViewModel() {

    val Healed=repostuff.gethealingz()

    fun deletevidz(id:Int,img:String) = viewModelScope.launch {
        repostuff.deletehealings(id,img)
    }

}