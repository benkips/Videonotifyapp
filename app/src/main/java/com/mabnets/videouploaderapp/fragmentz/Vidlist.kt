package com.mabnets.videouploaderapp.fragmentz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.mabnets.videouploaderapp.R
import com.mabnets.videouploaderapp.Utils.showmessages
import com.mabnets.videouploaderapp.Utils.snackbarz
import com.mabnets.videouploaderapp.adapters.Healadapter
import com.mabnets.videouploaderapp.adapters.HealingLoadStateAdapter
import com.mabnets.videouploaderapp.databinding.FragmentVidlistBinding
import com.mabnets.videouploaderapp.models.Healings
import com.mabnets.videouploaderapp.viewmodelz.Healingviewmodel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class Vidlist : Fragment(R.layout.fragment_vidlist), Healadapter.OnItemClickListner{
        private  val viewmodel by viewModels<Healingviewmodel>()
        private  var _binding :FragmentVidlistBinding?=null
        private val binding get() = _binding!!
        private lateinit var adapter:Healadapter

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            _binding=FragmentVidlistBinding.bind(view)
            adapter= Healadapter(this)

            binding.apply {
                rvheals.setHasFixedSize(true)
                rvheals.adapter=adapter.withLoadStateHeaderAndFooter(
                    header = HealingLoadStateAdapter{adapter.retry()},
                    footer =HealingLoadStateAdapter{adapter.retry()}
                )
                btnretry.setOnClickListener {
                    adapter.retry();
                }
            }

            viewmodel.Healed.observe(viewLifecycleOwner){
                adapter.submitData(viewLifecycleOwner.lifecycle,it)
            }

            adapter.addLoadStateListener {loadstate->
                binding.apply {
                    pgbar.isVisible=loadstate.source.refresh is LoadState.Loading
                    rvheals.isVisible=loadstate.source.refresh is LoadState.NotLoading
                    btnretry.isVisible=loadstate.source.refresh is LoadState.Error
                    tvError.isVisible=loadstate.source.refresh is LoadState.Error
                    //empty view
                    if(loadstate.source.refresh is LoadState.NotLoading &&
                        loadstate.append.endOfPaginationReached &&
                        adapter.itemCount<1){
                        rvheals.isVisible=false
                        tvViewEmpty.isVisible=true
                    }else{
                        tvViewEmpty.isVisible =false
                    }
                }
            }
        }

        override fun onItemClick(heal: Healings) {
            requireContext().showmessages(
                "Delete video",
                "Are you sure you want to do this?",
                {delvid(heal.id,heal.time)}

            )
        }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    private fun delvid(id:Int,img:String){
        viewmodel.deletevidz(id,img)
        adapter.refresh()
        requireView().snackbarz("Video deleted sucessfully","ok",{null})
    }

}