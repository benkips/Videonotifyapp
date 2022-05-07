package com.mabnets.videouploaderapp.fragmentz

import android.content.Context
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.geeksmediapicker.GeeksMediaPicker
import com.geeksmediapicker.GeeksMediaType
import com.mabnets.videouploaderapp.R
import com.mabnets.videouploaderapp.Utils.showmessages
import com.mabnets.videouploaderapp.Utils.snackbarz
import com.mabnets.videouploaderapp.Utils.visible
import com.mabnets.videouploaderapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.exceptions.UploadError
import net.gotev.uploadservice.exceptions.UserCancelledUploadException
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import java.io.File


@AndroidEntryPoint
class Home : Fragment(R.layout.fragment_home) {

    private  var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var path:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentHomeBinding.bind(view)
        binding.pgbar.visible(false)

        binding.btnCapture.setOnClickListener {

            GeeksMediaPicker.with(this)
                .setMediaType(GeeksMediaType.VIDEO)
                .startSingle { mediaStoreData ->
                    binding.lv.visibility=View.VISIBLE
                    path = mediaStoreData.content_uri.toString()
                    binding.vidname.text=mediaStoreData.media_name
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        previewVideo(mediaStoreData.content_uri!!)
                    }
                    //onFilePicked(path)

                }

        }
        binding.send.setOnClickListener {
            val title = binding.title.text.toString()
            val message = binding.msg.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty()&& path.isNotEmpty()) {
                upload(context = requireContext(), lifecycleOwner = viewLifecycleOwner,path,title,message)
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun previewVideo(path: Uri) {
        val mSize = Size(96, 96)
        val ca = CancellationSignal()
        val bitmapThumbnail =requireContext().contentResolver.loadThumbnail(
            path,
            mSize,
            ca
        )
        binding.ivthumb.setImageBitmap(bitmapThumbnail)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun upload(context: Context, lifecycleOwner: LifecycleOwner,filePath: String,title: String,msg: String) {
        MultipartUploadRequest(context, "https://repentanceandholinessinfo.com/mobiadmin/sendfrommobile")
            .setMethod("POST")
            .addParameter(paramName ="title" ,paramValue =title)
            .addParameter(paramName ="message" ,paramValue =msg )
            .addFileToUpload(
                filePath = filePath,
                parameterName = "uploadFile"
            )
            .subscribe(context = context, lifecycleOwner = lifecycleOwner, delegate = object :
                RequestObserverDelegate {
                override fun onProgress(context: Context, uploadInfo: UploadInfo) {
                    // do your thing
                    binding.pgbar.visible(true)
                }

                override fun onSuccess(
                    context: Context,
                    uploadInfo: UploadInfo,
                    serverResponse: ServerResponse
                ) {
                    // do your thing
                    binding.pgbar.visible(false)
                    requireContext().showmessages(
                        "Sucess",
                        "Video was uploaded and notification sent"
                    )
                }

                override fun onError(
                    context: Context,
                    uploadInfo: UploadInfo,
                    exception: Throwable
                ) {
                    when (exception) {
                        is UserCancelledUploadException -> {
                            Log.e("RECEIVER", "Error, user cancelled upload: $uploadInfo")
                            requireContext().showmessages(
                                "Error",
                                "user cancelled upload"
                            )
                        }

                        is UploadError -> {
                            Log.e("RECEIVER", "Error, upload error: ${exception.serverResponse}")
                            requireContext().showmessages(
                                "Error",
                                "upload error"
                            )
                        }

                        else -> {
                            Log.e("RECEIVER", "Error: $uploadInfo", exception)
                            requireContext().showmessages(
                                "Error",
                                "upload error"
                            )

                        }
                    }
                }

                override fun onCompleted(context: Context, uploadInfo: UploadInfo) {
                    binding.pgbar.visible(false)
                    // do your thing
                    requireView().snackbarz("Upload and notification completed","ok",{null})
                }

                override fun onCompletedWhileNotObserving() {
                    binding.pgbar.visible(false)
                    // do your thing
                    Toast.makeText(context, "Upload and notification tasks completed", Toast.LENGTH_SHORT).show()
                }
            })
    }
}